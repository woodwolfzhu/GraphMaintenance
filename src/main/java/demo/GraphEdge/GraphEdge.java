package demo.GraphEdge;

import demo.Graph.Graph;
import demo.GraphNode.GraphNode;
import demo.GraphWeight.GraphWeight;

import java.util.Iterator;
import java.util.Vector;

public interface GraphEdge {
    public void setEnd(GraphNode end);
    public void setStart(GraphNode start);
    public void setWeight(Vector weight);
    public Iterator getWeightIterator();
    Vector getWeightVector();
    // 因为涉及到多个权值，目前的解决办法是给不同的权值再另加权值，最后求一个总权值，以此来计算
    double getMinResult(Vector<GraphWeight> weightVector);
    double getMinResult();

    // 在所有权值中，得到最小的一组权值
    Vector getMinWeightGroup(Vector<GraphWeight> weightVector);

    // 在所有权值中，得到最小的一组权值对应的边
    GraphEdge getMinEdge();


}
