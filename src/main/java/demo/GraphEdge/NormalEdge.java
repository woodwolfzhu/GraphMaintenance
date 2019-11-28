package demo.GraphEdge;

import demo.Graph.Quintuple;
import demo.GraphNode.GraphNode;
import demo.GraphNode.NormalNode;
import demo.GraphWeight.GraphWeight;

import java.util.Vector;

public class NormalEdge implements GraphEdge {

    private GraphNode start;
    private GraphNode end;
    private Vector<Quintuple> weight;

    public NormalEdge() {
        start = new NormalNode();
        end = new NormalNode();
        weight = new Vector<Quintuple>();
    }

    public NormalEdge(GraphNode start, GraphNode end, Vector weight) {
        this.start = start;
        this.end = end;
        this.weight = weight;
    }

    public void setStart(GraphNode start) {
        this.start = start;
    }

    public void setEnd(GraphNode end) {
        this.end = end;
    }

    public void setWeight(Vector weight) {
        this.weight = weight;
    }

    public GraphNode getStart() {
        return start;
    }

    public GraphNode getEnd() {
        return end;
    }

    public Vector<Quintuple> getWeight() {
        return weight;
    }


    @Override
    public String toString() {
        return start.toString()+end.toString()+weight.toString();
    }
}
