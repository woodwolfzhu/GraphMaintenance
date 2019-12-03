package demo.GraphOperator;

import demo.Graph.Graph;
import demo.Graph.NormalGraph;
import demo.Graph.Quintuple;
import demo.GraphEdge.GraphEdge;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class PruneOperator extends Operator {

    public static Graph getResult(Graph R, Graph S) {
        // 新的topGraph,对应NormalGraph中的topGraph
        HashMap<String, HashMap> resultR = new HashMap();
        HashMap<String, HashMap> topGraphS = S.getTopGraph();

        // 获得graphR 的Iterator
        Iterator graphIteratorR = R.getGraphIterator();

        while (graphIteratorR.hasNext()) {
            Map.Entry entryR = (Map.Entry) graphIteratorR.next();
            String startName = (String) entryR.getKey();

            // 获得同样以某个节点为起点的点的Map，形式为<String,Quintuple>(<终点名,对应的五元组>
            HashMap bottomMapR = (HashMap) entryR.getValue();
            HashMap bottomMapS = topGraphS.get(startName);

            Iterator bottomIteratorR = bottomMapR.entrySet().iterator();
            String endName = null;

            while (bottomIteratorR.hasNext()) {
                Map.Entry bottomEntryR = (Map.Entry) bottomIteratorR.next();
                endName = (String) bottomEntryR.getKey();
                Quintuple quintupleR = (Quintuple) bottomEntryR.getValue();
                Quintuple quintupleS = (Quintuple) bottomMapS.get(endName);
                double weightR, weightS;
                weightR = quintupleR.getEdge1().getMinResult();
                weightS = quintupleS.getEdge1().getMinResult();

                // 如果R中的边的权值小于S的，则保留下来，否则删掉
                if (weightR < weightS) {
                } else {
                    bottomMapR.remove(endName);
                }
            }
            resultR.put(startName, bottomMapR);
        }
        return new NormalGraph(resultR);
    }
}