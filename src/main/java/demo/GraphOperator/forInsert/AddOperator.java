package demo.GraphOperator.forInsert;

import demo.Graph.Graph;
import demo.Graph.NormalGraph;
import demo.Graph.Quintuple;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class AddOperator extends Operator {

    public static Graph getResult(Graph R, Graph S) {
        // 新的topGraph,对应NormalGraph中的topGraph
        HashMap<String, HashMap> resultR = R.getTopGraph();

        // 获得graphS 的Iterator
        Iterator graphIteratorS = S.getGraphIterator();

        while (graphIteratorS.hasNext()) {
            Map.Entry entry = (Map.Entry) graphIteratorS.next();
            String startName = (String) entry.getKey();

            // 获得同样以某个节点为起点的点的Map，形式为<String,Quintuple>(<终点名,对应的五元组>
            HashMap bottomMapS = (HashMap) entry.getValue();
            HashMap bottomMapR = resultR.get(startName);

            // 如果S中有R中不存在的元素，则将其放入S中，起点不存在
            if (bottomMapR == null) {
                resultR.put(startName, bottomMapS);
                continue;
            }

            Iterator bottomIteratorS = bottomMapS.entrySet().iterator();
            String endName = null;

            while (bottomIteratorS.hasNext()) {
                Map.Entry bottomEntryS = (Map.Entry) bottomIteratorS.next();
                endName = (String) bottomEntryS.getKey();

                // 如果S中有R中不存在的元素，则将其放入S中，终点不存在
                if (bottomMapR.get(endName) == null) {
                    Quintuple quintupleR = (Quintuple) bottomEntryS.getValue();
                    bottomMapR.put(endName, quintupleR);
                }
            }
            resultR.put(startName, bottomMapR);
        }
        return new NormalGraph(resultR);
    }
}
