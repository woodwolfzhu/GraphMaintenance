package demo.GraphEdge;

import demo.GraphNode.GraphNode;
import demo.GraphWeight.GraphWeight;

import java.util.Vector;

public interface GraphEdge {
    public void setEnd(GraphNode end);
    public void setStart(GraphNode start);
    public void setWeight(Vector weight);
    public Vector getWeight();

}
