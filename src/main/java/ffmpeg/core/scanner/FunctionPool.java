package ffmpeg.core.scanner;

import ffmpeg.core.JobResult;
import ffmpeg.core.scanner.errors.DuplicateDependencyException;
import ffmpeg.core.scanner.errors.MainDefinitionException;

import java.lang.reflect.Method;
import java.util.*;
import java.util.function.BiFunction;

/**
 * AS indicated in main method
 * maintains a multiway tree, which is maintained for dependency management
 * This pool currently supports serial implementation, TODO: add parallel run
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
    public static class Node {
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
        @Override
        public boolean equals(Object obj) {
            Node other = (Node) obj;
            return other.name.equals(this.name);
        }
    }
    public FunctionPool(){
        nodeInfo.put("_root_", new Node());
    }
    public FunctionPool(Object instance) {
        this();
        this.instance = instance;
    }

    public FunctionPool insert(Method method) {
        return this.insert(method, ROOT_NAME);
    }

    public FunctionPool insert(Method method, String dependsOn) {
        if (dependsOn.equals(method.getName())) {
            throw new DuplicateDependencyException("Dependency name and method name should not be defined same: "
                                                    + method.getName() + "at " +
                                                    method.getDeclaringClass().getName());
        }
        Node insertTarget = new Node(method.getName(), method);
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
    public <T> List<JobResult> getResult(BiFunction<Method, Object, JobResult> processFunc) {
        // TODO: implement the result list return according to node
        ensureSafety(); // cache should be empty when getResult() is called
        List<JobResult> result = new ArrayList<>();
        Stack<Node> runtimeStack = new Stack<>();
        runtimeStack.push(root);
        while (!runtimeStack.isEmpty()) {
            Node cur_node = runtimeStack.pop();

        }
        return null;
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
