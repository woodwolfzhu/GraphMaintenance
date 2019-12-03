package demo.GraphOperator;

import demo.Graph.Graph;
import demo.Graph.NormalGraph;
import demo.Graph.Quintuple;
import demo.GraphEdge.GraphEdge;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MinOperator extends Operator {

    public static Graph getResult(Graph graph){
        // 新的topGraph,对应NormalGraph中的topGraph
        HashMap<String,HashMap> topGraph = new HashMap();

        // 获得graph 的Iterator
        Iterator graphIterator = graph.getGraphIterator();

        while(graphIterator.hasNext()){
            Map.Entry entry = (Map.Entry) graphIterator.next();
            String startName = (String) entry.getKey();
            // 获得同样以某个节点为起点的点的Map，形式为<String,Quintuple>(<终点名,对应的五元组>
            HashMap bottomMap = (HashMap) entry.getValue();
            Iterator bottomIterator = bottomMap.entrySet().iterator();
            Quintuple quintupleTrans = null;
            String endName=null;
            while(bottomIterator.hasNext()){
                Map.Entry bottomEntry= (Map.Entry) bottomIterator.next();
                endName = (String) bottomEntry.getKey();
                Quintuple quintuple = (Quintuple) bottomEntry.getValue();
                quintupleTrans =  quintuple.getMinQuituple();
                bottomMap.put(endName,quintupleTrans);
            }
            topGraph.put(startName,bottomMap);
        }
        return new NormalGraph(topGraph);
    }

}
