package ffmpeg.core.scanner;

import ffmpeg.job.JobResult;

import java.lang.reflect.Method;
import java.util.*;
import java.util.function.BiFunction;

/**
 * AS indicated in main method
 * maintains a multiway tree, which is maintained for dependency management
 * */
class FunctionPool {
    private static String ROOT_NAME = "_root_";
    Node root = new Node(ROOT_NAME);
    Map<String, Node> nodeInfo = new HashMap<>();
    Object instance;
    Map<String, List<Node>> dependCache = new HashMap<>();
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
    private void swipeCache(Node target) {
        if (dependCache.containsKey(target.name)) {
            target.children.addAll(dependCache.get(target.name));
            dependCache.remove(target.name);
        }
    }
    // force clear the whole cache
    public void forceClear() {
        dependCache.clear();
    }
    public List<JobResult> getResult() {
        // TODO: implement the result list return according to node
        ensureSafety();
        return null;
    }

    public <K, V, R> JobResult reduce(BiFunction<K, V, R> func) {
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
        this.dependCache = new HashMap<>();
        System.gc(); // to reduce possible full gc after init the framework
    }
}
