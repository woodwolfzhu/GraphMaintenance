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

//求两个集合的差集
public class DifferenceOperator extends Operator {

    public static Graph getResult(Graph R, Graph S){
        // R为较大的图，S为较小的图

        HashMap topGraphR = R.getTopGraph();
        Iterator iteratorR = R.getGraphIterator();
        HashMap topGraphS = S.getTopGraph();
        HashMap resultTopGraph = new HashMap();

        while (iteratorR.hasNext()) {
            Map.Entry entry = (Map.Entry) iteratorR.next();
            HashMap bottomGraphS = (HashMap) topGraphS.get(entry.getKey());
//            topGraphR中有，但是topGraphS中没有，那么将该元素放入resultTopGraph中

            if(bottomGraphS == null){
                resultTopGraph.put(entry.getKey(),entry.getValue());
                continue;
            }

//          topGraphR中有，但是topGraphS也有，那么比较二者的bottomGraph，
//          取出bottomGraphR中有，但bottomGraphS中没有的元素

            Set<String> SKey = bottomGraphS.keySet();
//            S的顶层HashMap中有此Key
            HashMap<String, Quintuple> bottomGrapgR = (HashMap) entry.getValue();
            Set<String> RKey = bottomGrapgR.keySet();
            Set<String> intersectionKey = Sets.difference(RKey, SKey);
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
