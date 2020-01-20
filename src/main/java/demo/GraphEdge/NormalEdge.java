package demo.GraphEdge;

import com.sun.javafx.scene.traversal.WeightedClosestCorner;
import demo.Graph.Graph;
import demo.Graph.Quintuple;
import demo.GraphNode.GraphNode;
import demo.GraphNode.NormalNode;
import demo.GraphWeight.GraphWeight;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

public class NormalEdge implements GraphEdge, Serializable {

    private GraphNode start;
    private GraphNode end;
    private Vector<GraphWeight> weight;

    /*
    * 因为有多个权值，但是在进行最短距离计算时，又只能使用一个，所以必须有个办法
    * 拿到自己所需的权值，
    * 目前的方案是：
    * 将所有权值加在一起，获得总权值
    * */


    // 获得权值中最小的一组
    public Vector getMinWeightGroup(Vector weightVector) {
        Iterator iterator=weightVector.iterator();
        HashMap<String,GraphWeight> weightHashMap = new HashMap<String, GraphWeight>();
        double result=0;

        // 在所有权值中，得到最小的一组权值
        while (iterator.hasNext()) {
            GraphWeight weightTemp = (GraphWeight) iterator.next();
            String name = weightTemp.getName();
            Double value = weightTemp.getValue();
            if (weightHashMap.get(name) == null) {
                weightHashMap.put(weightTemp.getName(), weightTemp);
            } else {
                // 将weight 中元素更新为value较小的一个
                if(value < weightHashMap.get(name).getValue()){
                    weightHashMap.put(name,weightTemp);
                }
            }
        }

        // 之前得到的结果是HashMap，现在转化为Vector
        Iterator iterator1 = weightHashMap.entrySet().iterator();
        Vector<GraphWeight> weightResult = new Vector<GraphWeight>();
        while(iterator1.hasNext()){
            Map.Entry entry = (Map.Entry) iterator1.next();
            weightResult.add((GraphWeight) entry.getValue());
        }

        return weightResult;
    }

    public Vector getMinWeightGroup() {
        return getMinWeightGroup(weight);
    }


    public NormalEdge() {
        start = new NormalNode();
        end = new NormalNode();
        weight = new Vector<GraphWeight>();
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

    public Iterator getWeightIterator() {
        return weight.iterator();
    }

    public Vector getWeightVector() {
        return weight;
    }



    // 获得权值的综合值，目前为简单的和
    public double getMinResult(Vector<GraphWeight> weightVector) {
        Vector minWeightGroup = getMinWeightGroup(weightVector);
        Iterator iterator = minWeightGroup.iterator();
        double result=0;
        while(iterator.hasNext()){
            GraphWeight graphWeight = (GraphWeight) iterator.next();
            result = result+graphWeight.getValue();
        }
        return result;
    }

    // 获得权值的综合值，目前为简单的和
    public double getMinResult() {
        Iterator iterator = weight.iterator();
        double result=0;
        while(iterator.hasNext()){
            GraphWeight graphWeight = (GraphWeight) iterator.next();
            result = result+graphWeight.getValue();
        }
        return result;
    }

    public GraphEdge getMinEdge() {
        Vector minWeightGroup = getMinWeightGroup(weight);
        return new NormalEdge(start,end,minWeightGroup);
    }

    @Override
    public String toString() {
        return start.toString()+end.toString()+weight.toString();
    }
}
