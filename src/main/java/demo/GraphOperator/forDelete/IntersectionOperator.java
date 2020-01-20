package demo.GraphOperator.forDelete;

import com.google.common.collect.Sets;
import demo.Graph.Graph;
import demo.Graph.NormalGraph;
import demo.Graph.Quintuple;
import demo.GraphOperator.forInsert.Operator;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

// 求两个集合的交集

public class IntersectionOperator extends Operator {

    public static Graph getResult(Graph R, Graph S){
        HashMap topGraphR = R.getTopGraph();
        Iterator iteratorR = R.getGraphIterator();
        HashMap topGraphS = S.getTopGraph();
        HashMap resultTopGraph = new HashMap();


        while (iteratorR.hasNext()) {
            Map.Entry entry = (Map.Entry) iteratorR.next();
            HashMap bottomGraphS = (HashMap) topGraphS.get(entry.getKey());
//            topGraphR中有，但是topGraphS中没有，那么直接跳过
            if(bottomGraphS == null){
                continue;
            }
//          topGraphR中有，但是topGraphS也有，那么将二者的bottomGraph中相同的元素放入resultBottomGraph中，
            // 最后在放入resultTopGraph中

            Set<String> SKey = bottomGraphS.keySet();
//            S的顶层HashMap中有此Key
            HashMap<String, Quintuple> bottomGrapgR = (HashMap) entry.getValue();
            Set<String> RKey = bottomGrapgR.keySet();
            Set<String> intersectionKey = Sets.intersection(RKey, SKey);
            HashMap resultBottomMap = new HashMap();
            for (String key : intersectionKey) {
                resultBottomMap.put(key,bottomGrapgR.get(key));
            }
            resultTopGraph.put(entry.getKey(),resultBottomMap);
        }
        Graph resultGraph = new NormalGraph(resultTopGraph);
        return resultGraph;
    }
}
