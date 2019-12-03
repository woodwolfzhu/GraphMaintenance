package demo.GraphNode;

public class NormalNode implements GraphNode {

    // 这里暂时认为 name 为节点的唯一性标识
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name+" ";
    }
}
