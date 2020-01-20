package demo.GraphOperator.forDelete;

import demo.Graph.Graph;
import demo.Graph.NormalGraph;
import demo.Graph.Quintuple;
import demo.GraphEdge.GraphEdge;
import demo.GraphEdge.NormalEdge;
import demo.GraphOperator.forInsert.Operator;
import demo.GraphWeight.GraphWeight;
import demo.GraphWeight.NormalWeight;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

public class DelBackwardOperator extends Operator {
    public static Graph getResult(Graph R, Graph S, Graph SD_star,boolean flag){
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
            Iterator bottomIteratorR = bottomMapR.entrySet().iterator();
            String endName = null;
            HashMap<String, Quintuple> bottomResult = new HashMap<String, Quintuple>();

            while (bottomIteratorR.hasNext()) {
                Map.Entry bottomEntryR = (Map.Entry) bottomIteratorR.next();
                endName = (String) bottomEntryR.getKey();

                // 用R的终点为起点，到S中拿元素
                HashMap bottomMapS = topGraphS.get(endName);
                if (bottomMapS == null) {
                    System.out.println("第二个参数给出的图中元素不完全，R中有元素未得到计算");
                    continue;
                }

                // 遍历bottomMapS，对R中的一条边进行拓展
                Iterator quintupleEntryS = bottomMapS.entrySet().iterator();
                while (quintupleEntryS.hasNext()) {
                    Map.Entry entryS = (Map.Entry) quintupleEntryS.next();
                    String endName2 = (String) entryS.getKey();
                    Quintuple quintupleS = (Quintuple) entryS.getValue();

                    // 判断这个endName2 与startName 是同一个点,是得话就直接跳过
                    if (endName2.equals(endName)) {
                        continue;
                    } else {
                        Quintuple quintupleR = (Quintuple) bottomEntryR.getValue();

                        // 需要将两条边的权值合到一起
                        // 因为存在含有多个相同权值的情况（只有值不同），这里直接取其最小的一组
                        Vector weightR = quintupleR.getEdge1().getMinWeightGroup();
                        Vector weightS = quintupleS.getEdge1().getMinWeightGroup();
                        Iterator iteratorR = weightR.iterator();
                        Iterator iteratorS = weightS.iterator();
                        HashMap<String, GraphWeight> resultWeightHashMap = new HashMap<String, GraphWeight>();

                        // 先将R这一条边的权值放入resultWeightHashMap中,之后再对resultWeightHashMap中的元素
                        // 进行更新.
                        // 这样做,就是以R中的权值为主导,R中有的权值才会被保存和更新
                        while(iteratorR.hasNext()){
                            GraphWeight wR= (GraphWeight) iteratorR.next();
                            resultWeightHashMap.put(wR.getName(),wR);
                        }

                        while (iteratorS.hasNext()){
                            GraphWeight wS = (GraphWeight) iteratorS.next();
                            if(resultWeightHashMap.get(wS.getName()) != null){
                                double value = resultWeightHashMap.get(wS.getName()).getValue()+wS.getValue();
//                                wS.setValue(value);
                                resultWeightHashMap.put(wS.getName(),new NormalWeight(wS.getName(),value));
                            }else{
                                System.out.println("第二个参数给出的图中元素不完全，R中有元素未得到计算");
                                continue;
                            }
                        }

                        // 图E 中被删去的边在SD 中一定有
                        HashMap sd_starTopGraph = SD_star.getTopGraph();
                        Quintuple sd_starBottom = (Quintuple) ((HashMap) sd_starTopGraph.get(startName)).get(endName2);
                        if(sd_starBottom == null){
                            continue;
                        }
                        Vector sd_starWeight = sd_starBottom.getEdge1().getMinWeightGroup();
                        int i;
                        HashMap<String,Double> sd_starWeightMap = new HashMap();
                        for (i=0;i<sd_starWeight.size();i++){
                            String key = ((GraphWeight) sd_starWeight.get(i)).getName();
                            double value = ((GraphWeight) sd_starWeight.get(i)).getValue();
                            sd_starWeightMap.put(key,value);
                        }
                        // 将HashMap 转化为Vector
                        Iterator iterator1 = resultWeightHashMap.entrySet().iterator();
                        Vector<GraphWeight> resultWeightVector = new Vector<GraphWeight>();
                        while(iterator1.hasNext()){
                            Map.Entry entry = (Map.Entry) iterator1.next();
                            double sd_value = (double) sd_starWeightMap.get(entry.getKey());
                            NormalWeight resultWeight = (NormalWeight) entry.getValue();
                            if(sd_value == resultWeight.getValue()){
                                resultWeightVector.add((GraphWeight) entry.getValue());
                            }

                        }
                        if(resultWeightVector.size() == 0){
                            continue;
                        }
                        GraphEdge edge = new NormalEdge(quintupleR.getEdge1().getStart(),
                                quintupleS.getEdge1().getEnd(),resultWeightVector);

                        Quintuple resultQuintuple = new Quintuple();
                        resultQuintuple.setEdge1(edge);

                        if(flag) {
                            resultQuintuple.setEdge2(quintupleS.getEdge1());
                        }

                        bottomResult.put(quintupleS.getEdge1().getEnd().getName(),resultQuintuple);

                    }
                }
            }
            if(!bottomResult.isEmpty()) {
                resultR.put(startName, bottomResult);
            }
        }
        return new NormalGraph(resultR);
    }
}
