package demo.GraphOperator.forInsert;

import demo.Graph.Graph;
import demo.Graph.NormalGraph;
import demo.Graph.Quintuple;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SubOperator extends Operator {

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

            // 如果S中有R中不存在的元素，则直接跳过即可
            if (bottomMapR == null || bottomMapR.isEmpty()) {
                continue;
            }

            Iterator bottomIteratorS = bottomMapS.entrySet().iterator();
            String endName = null;


            while (bottomIteratorS.hasNext()) {
                Map.Entry bottomEntryS = (Map.Entry) bottomIteratorS.next();
                endName = (String) bottomEntryS.getKey();

                // 当比较的权值发生变化时，修改这里
                //======================================================================
                //  如果S中的元素在R中不存在，则直接跳过
                // 如果S中的元素在R中也存在，则比较其权值的大小，如果相等则删掉；
                // 这里又涉及到了多权值的问题，暂时使用综合权值进行比较
                if (bottomMapR.get(endName) == null) {
                    continue;
                }else{
                    Quintuple quintupleR = (Quintuple) bottomEntryS.getValue();
                    Quintuple quintupleS = (Quintuple) bottomMapS.get(endName);
                    double weightR, weightS;
                    weightR = quintupleR.getEdge1().getMinResult();
                    weightS = quintupleS.getEdge1().getMinResult();
                    // 如果权值相等则该元素从R中移除
                    if(weightR == weightS){
                        bottomMapR.remove(endName);
                    }
                }
            }
            if (!bottomMapR.isEmpty()) {
                resultR.put(startName, bottomMapR);
            }
            //==============================================================================

        }
        return new NormalGraph(resultR);
    }
}
