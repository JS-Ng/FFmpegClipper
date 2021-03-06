package ffmpeg.core.scanner;

import ffmpeg.core.JobResult;
import ffmpeg.core.scanner.errors.DuplicateDependencyException;
import ffmpeg.core.scanner.errors.MainDefinitionException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.BiFunction;

/**
 * AS indicated in main method
 * maintains a multiway tree, which is maintained for dependency management
 * This pool currently supports serial implementation, TODO: add parallel run, each time we have a new client, resolve the synchronization issue
 * */
public class FunctionPool {
    /**
     * the name of the tree root, default to _root_, unnecessary to change
     * anything of it, when using this functional pool, no method
     * */
    private static String ROOT_NAME = "_root_";
    Node root = new Node(ROOT_NAME);
    Object instance;
    Map<String, Node> nodeInfo = new HashMap<>(); // actual node inside the pool
    Map<String, List<Node>> dependCache = new HashMap<>(); // cache when inserting a node
    public static class Node implements Iterable <Node>{
        Object cached_result = null;
        volatile boolean isCached = false;
        boolean shouldCache = false;
        String name;
        Method method;
        List<Node> children = new LinkedList<>();
        public Node() {}
        public Node(String name) {
            this();
            this.name = name;
        }
        public Node(String name, Method method) {
            this(name);
            this.method = method;
        }
        public boolean isLeaf() {
            return this.children.size() == 0;
        }
        @Override
        public boolean equals(Object obj) {
            Node other = (Node) obj;
            return other.name.equals(this.name);
        }

        @Override
        public Iterator<Node> iterator() {
            return this.children.iterator();
        }
    }

    public FunctionPool(Object instance) {
        nodeInfo.put("_root_", new Node());
        this.instance = instance;
    }
    public FunctionPool insert(Method method) {
        return this.insert(method, false);
    }
    public FunctionPool insert(Method method, boolean shouldCache) {
        return this.insert(method, ROOT_NAME, shouldCache);
    }

    public FunctionPool insert(Method method, String dependsOn, boolean shouldCache) {
        if (dependsOn.equals(method.getName())) {
            throw new DuplicateDependencyException("Dependency name and method name should not be defined same: "
                                                    + method.getName() + "at " +
                                                    method.getDeclaringClass().getName());
        }
        Node insertTarget = new Node(method.getName(), method);
        insertTarget.shouldCache = shouldCache;
        if (nodeInfo.containsKey(insertTarget.name)) {
            throw new DuplicateDependencyException("Cannot use overload with @main");
        }
        // register the node information
        nodeInfo.put(insertTarget.name, insertTarget);
        if (nodeInfo.containsKey(dependsOn)) { // directly insert to the tree
            Node curDepends = nodeInfo.get(dependsOn);
            curDepends.children.add(insertTarget);
        }
        else {
            // cache it if it doesn't contain a parent for now
            List<Node> curCacheList = dependCache.getOrDefault(dependsOn, new ArrayList<>());
            curCacheList.add(insertTarget);
            dependCache.put(dependsOn, curCacheList);
        }
        // swipe if it's a parent of someone else
        swipeCache(insertTarget);

        return this;
    }

    public FunctionPool setInstance(Object instance) {
        this.instance = instance;
        return this;
    }
    // swipe the cache according to the target information, trying to put it to where it belongs
    void swipeCache(Node target) {
        if (dependCache.containsKey(target.name)) {
            target.children.addAll(dependCache.get(target.name));
            dependCache.remove(target.name);
        }
    }
    /**
     * This method will get the expected result according to the bifunction passed into it, and return a list of them
     * processFunc: function to deal with method reflection and return the job result
     * */
    public <T> List<JobResult> getResult(BiFunction<Method, Object, Object> processFunc) throws IllegalAccessException, InvocationTargetException {
        ensureSafety(); // cache should be empty when getResult() is called
        List<JobResult> result = new ArrayList<>();
        Stack<Node> runtimeStack = new Stack<>();
        Stack<Object> andThenStack = new Stack<>();
        // the first layer is the most special
        for (Node child : root) {
           if (child.isLeaf()) {
               JobResult rs = new JobResult();
               if (child.isCached) rs.set(child.cached_result);
               else {
                   if (child.shouldCache) {
                       synchronized (this) {
                           child.cached_result = processFunc.apply(child.method, instance);
                           child.isCached = true;
                       }
                   }
               }
               result.add(rs);
           }

           else {
               Object computeResult;
               if (!child.isCached) {
                   if (child.shouldCache) {
                       synchronized (this) {
                           child.cached_result = processFunc.apply(child.method, instance);
                           child.isCached = true; // if not cached, must cache it immediately
                           computeResult = child.cached_result;
                       }
                   }

                   else {
                       computeResult = processFunc.apply(child.method, instance);
                   }
               }

               else {
                   computeResult = processFunc.apply(child.method, instance);
               }
               for (Node subChild : child) {
                   andThenStack.push(computeResult);
                   runtimeStack.push(subChild);
                   proceedWithRuntimeStack(runtimeStack, andThenStack, result);
               }
           }
        }

        return result;
    }

    private void proceedWithRuntimeStack(Stack<Node> runtimeStack, Stack<Object> varStack, List<JobResult> result) throws InvocationTargetException, IllegalAccessException {
        while (!runtimeStack.isEmpty()) {
            Node cur_node = runtimeStack.pop();
            Object input = varStack.pop();
            if (!cur_node.name.equals(ROOT_NAME) &&
                    cur_node.isLeaf()) {
                if (cur_node.isCached) {result.add(new JobResult(cur_node.cached_result));}
                else {
                    if (cur_node.shouldCache) {
                        Object cachedVal;
                        synchronized (this) {
                            cachedVal = cur_node.method.invoke(instance, input);
                            cur_node.cached_result = cachedVal; // get the true input
                            cur_node.isCached = true;
                        }
                        result.add(new JobResult(cachedVal)); // one job finished when reaching the bottom

                    }
                }

            }
            else if (cur_node.isCached) {
                Object inheritResult = cur_node.cached_result;
                for (Node child : cur_node) {
                    runtimeStack.push(child); // return the cached result to the children, making program faster
                    varStack.push(inheritResult);
                }
            }

            else {
                Object inheritResult = cur_node.method.invoke(this.instance, input);
                if (cur_node.shouldCache) {
                    synchronized (this) {
                        cur_node.cached_result = inheritResult;
                        cur_node.isCached = true;
                    }
                }

                for (Node child: cur_node) {
                    varStack.push(inheritResult);
                    runtimeStack.push(child); // compute the raw result if this node is not cached
                }
            }

        }
    }
    /**
     * aggregator: function to map the final results together
     * */
    public <K, V, R> JobResult reduce(BiFunction<Method, Object, JobResult> processFunc, BiFunction<K, V, R> aggregator) {
        // TODO: get aggregate result
        return null;
    }
    // ensure cache has been cleared
    public void ensureSafety() {
        if (!this.dependCache.isEmpty()) {
            for (String k : dependCache.keySet()) {
                throw new MainDefinitionException(String.format("Dependency method with name: %s doesn't exist", k));
            }
        }
    }
    public void freeCache() {
        // force clear the cache
        this.dependCache = new HashMap<>();
        System.gc(); // to reduce possible full gc after init the framework
    }
}
