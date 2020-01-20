package demo.Graph;

import demo.GraphEdge.GraphEdge;
import demo.GraphEdge.NormalEdge;

import java.io.Serializable;

/*
 * 这里Quintuple 有两个GraphEdge变量
 * 本质上是为了模拟五元组的集合，三元组也能模拟
 * */
public class Quintuple implements Serializable {
    private GraphEdge edge1;
    private GraphEdge edge2;

    public Quintuple() {
        edge1 = new NormalEdge();
        edge2 = new NormalEdge();
    }

    public Quintuple(GraphEdge edge1) {
        this.edge1 = edge1;
        this.edge2 = new NormalEdge();
    }

    public Quintuple(GraphEdge edge1, GraphEdge edge2) {
        this.edge1 = edge1;
        this.edge2 = edge2;
    }

    public void setEdge1(GraphEdge edge1) {
        this.edge1 = edge1;
    }

    public void setEdge2(GraphEdge edge2) {
        this.edge2 = edge2;
    }

    public GraphEdge getEdge1() {
        return edge1;
    }

    public GraphEdge getEdge2() {
        return edge2;
    }
    
    // 获得权值最小的一组边对应的五元组
    public Quintuple getMinQuituple(){
        GraphEdge edge1 = this.edge1.getMinEdge();
        if(edge2!= null) {
            GraphEdge edge2 = this.edge2.getMinEdge();
        }
        return new Quintuple(edge1,edge2);
    }

    @Override
    public String toString() {
        return "{"+edge1.toString() + '\n' + edge2.toString()+"}";
    }
}
