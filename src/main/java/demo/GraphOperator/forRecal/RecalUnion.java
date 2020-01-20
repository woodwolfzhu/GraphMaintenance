package demo.GraphOperator.forRecal;

import demo.Graph.Graph;
import demo.Graph.NormalGraph;
import demo.Graph.Quintuple;
import demo.GraphEdge.NormalEdge;
import demo.GraphWeight.NormalWeight;
import sun.awt.SunHints;

import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

public class RecalUnion {
    public static Graph getResult(Graph R,Graph S){
        HashMap result = R.getTopGraph();
        HashMap STop = S.getTopGraph();
        Iterator RIterator = R.getGraphIterator();
        while (RIterator.hasNext()){
            Map.Entry REntry = (Map.Entry) RIterator.next();
            HashMap RBottom = (HashMap) REntry.getValue();
            String startName = (String) REntry.getKey();
            HashMap SBottom = (HashMap) STop.get(startName);
            if (SBottom == null){
                continue;
            }else{
                Iterator RBottom_iter = RBottom.entrySet().iterator();
                HashMap resultBottom = new HashMap();
                while (RBottom_iter.hasNext()){
                    Map.Entry RBottomEntry = (Map.Entry) RBottom_iter.next();
                    String endName = (String) RBottomEntry.getKey();
                    Quintuple SQuintuple = (Quintuple) SBottom.get(endName);
                    if(SQuintuple == null){
                        continue;
                    }else {
                        resultBottom.put(endName,(Quintuple)RBottomEntry.getValue());
//                        Vector RWeight = ((Quintuple)RBottomEntry.getValue()).getEdge1().getMinWeightGroup();
//                        Vector SWeight = SQuintuple.getEdge1().getMinWeightGroup();
//                        int i;
//                        for (i=0;i<RWeight.size();i++){
//                            double rw = ((NormalWeight)RWeight.get(i)).getValue();
//                            double sw = ((NormalWeight)SWeight.get(i)).getValue();
//                            if(rw != sw){
//                                RWeight.remove(i);
//                            }else{
//                                continue;
//                            }
//                        }
//                        NormalEdge edge = (NormalEdge) SQuintuple.getEdge1();
//                        edge.setWeight(RWeight);
//                        SQuintuple.setEdge1(edge);
//                        resultBottom.put(endName,SQuintuple);
                    }
                }
                if (!resultBottom.isEmpty() ) {
                    result.put(startName, resultBottom);
                }
            }
        }
        return new NormalGraph(result);
    }
}
