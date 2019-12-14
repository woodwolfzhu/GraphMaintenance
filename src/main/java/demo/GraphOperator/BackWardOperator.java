package demo.GraphOperator;

import demo.Graph.Graph;
import demo.Graph.NormalGraph;
import demo.Graph.Quintuple;
import demo.GraphEdge.GraphEdge;
import demo.GraphEdge.NormalEdge;
import demo.GraphWeight.GraphWeight;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

public class BackWardOperator extends Operator {

    public static Graph getResult(Graph R, Graph S,boolean flag) {
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
                    if (endName.equals(endName)) {
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

                        while(iteratorR.hasNext()){
                            GraphWeight wR= (GraphWeight) iteratorR.next();
                            resultWeightHashMap.put(wR.getName(),wR);
                        }

                        while (iteratorS.hasNext()){
                            GraphWeight wS = (GraphWeight) iteratorS.next();
                            if(resultWeightHashMap.get(wS.getName()) != null){
                                double value = resultWeightHashMap.get(wS.getName()).getValue()+wS.getValue();
                                wS.setValue(value);
                                resultWeightHashMap.put(wS.getName(),wS);
                            }else{
                                System.out.println("第二个参数给出的图中元素不完全，R中有元素未得到计算");
                                continue;
                            }
                        }

                        // 将HashMap 转化为Vector
                        Iterator iterator1 = resultWeightHashMap.entrySet().iterator();
                        Vector<GraphWeight> resultWeightVector = new Vector<GraphWeight>();
                        while(iterator1.hasNext()){
                            Map.Entry entry = (Map.Entry) iterator1.next();
                            resultWeightVector.add((GraphWeight) entry.getValue());
                        }

                        GraphEdge edge = new NormalEdge(quintupleR.getEdge1().getStart(),
                                quintupleS.getEdge1().getEnd(),resultWeightVector);

                        // 以后者（S）的终点为key，传入新的bottom
                        // 这里有两个选择
                        // 选择1：直接将更新后的元素放入原bottomR 中，但是这样可能会导致本次放入的元素，再后面有被再次更新，
                        //      原有的元素也可能直接被更新。
                        // 选择2：放入一个新的bottomResult中
                        // 需要在论文中再看看，究竟是什么意思。
                        // 应该为 选择第2种方式
                        Quintuple resultQuintuple = new Quintuple();
                        resultQuintuple.setEdge1(edge);
                        if(flag) {
                            resultQuintuple.setEdge2(quintupleS.getEdge1());
                        }
                        bottomResult.put(quintupleS.getEdge1().getEnd().getName(),resultQuintuple);

                    }
                }
            }
            resultR.put(startName, bottomResult);
        }
        return new NormalGraph(resultR);
    }
}
