package demo.GraphOperator;

import com.google.common.collect.Sets;
import demo.Graph.Graph;
import demo.Graph.NormalGraph;
import demo.Graph.Quintuple;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

// 求两个集合的并集
public class UnionOperator extends Operator {

    public static Graph getResult(Graph R, Graph S) {
        HashMap topGraphR = R.getTopGraph();
        Iterator iteratorR = R.getGraphIterator();
        HashMap topGraphS = S.getTopGraph();
        Iterator iteratorS = S.getGraphIterator();
        HashMap resultTopGraph = new HashMap();

        while (iteratorR.hasNext()) {
            Map.Entry entry = (Map.Entry) iteratorR.next();
            HashMap bottomGraphS = (HashMap) topGraphS.get(entry.getKey());
//            topGraphR中有，但是topGraphS中没有，那么将R的元素放入restultTopGraph 中后直接跳过即可
            if(bottomGraphS == null){
                resultTopGraph.put(entry.getKey(),entry.getValue());
                continue;
            }
//          topGraphR中有，但是topGraphS也有，那么将二者的bottomGraph进行合并
            Set<String> SKey = bottomGraphS.keySet();
//            S的顶层HashMap中有此Key
            HashMap<String, Quintuple> bottomGrapgR = (HashMap) entry.getValue();
            Set<String> RKey = bottomGrapgR.keySet();
            Set<String> allKey = Sets.union(RKey, SKey);
            HashMap resultBottomMap = new HashMap();
            for (String key : allKey) {
                if (bottomGrapgR.containsKey(key)) {
                    resultBottomMap.put(key, bottomGrapgR.get(key));
                } else {
                    resultBottomMap.put(key, bottomGraphS.get(key));
                }
            }
            resultTopGraph.put(entry.getKey(),resultBottomMap);
        }

//        topGraphR中没有，但是topGraphS有
        while (iteratorS.hasNext()) {
            Map.Entry entryS = (Map.Entry) iteratorS.next();
            if (!topGraphR.containsKey(entryS.getKey())) {
                resultTopGraph.put(entryS.getKey(), entryS.getValue());
            }
        }

        Graph resultGraph = new NormalGraph(resultTopGraph);
        return resultGraph;
    }
}
