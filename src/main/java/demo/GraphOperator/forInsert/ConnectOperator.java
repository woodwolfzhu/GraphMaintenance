package demo.GraphOperator.forInsert;

import demo.Graph.Graph;
import demo.Graph.NormalGraph;
import demo.Graph.Quintuple;
import demo.GraphEdge.GraphEdge;
import demo.GraphEdge.NormalEdge;
import demo.GraphWeight.GraphWeight;
import demo.GraphWeight.NormalWeight;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

public class ConnectOperator {
    /*
以R中五元组中第二条边的起点
之后以a 为索引到S中拿到五元组的HashMap
此时需要遍历这个HashMap，对每个五元组进行比较，找到符合要求的进行连接
获得新的Edge、Quintuple、HashMap、Graph
最后将Graph 作为结果返回，*/
    public static Graph getResult(Graph R, Graph S) {
        // 新的topGraph,对应NormalGraph中的topGraph
        HashMap<String, HashMap> resultR = new HashMap();
        HashMap<String, HashMap> topGraphS = S.getTopGraph();

        // 获得graphR 的Iterator
        Iterator graphIteratorR = R.getGraphIterator();

        while (graphIteratorR.hasNext()) {
            Map.Entry entryR = (Map.Entry) graphIteratorR.next();
            // String startName = (String) entryR.getKey();

            // 获得同样以某个节点为起点的点的Map，形式为<String,Quintuple>(<终点名,对应的五元组>
            HashMap bottomMapR = (HashMap) entryR.getValue();
            Iterator bottomIteratorR = bottomMapR.entrySet().iterator();
            // String endName = null;
            HashMap<String, Quintuple> bottomResult = new HashMap<String, Quintuple>();

            while (bottomIteratorR.hasNext()) {
                Map.Entry bottomEntryR = (Map.Entry) bottomIteratorR.next();
                Quintuple quintupleR = (Quintuple) bottomEntryR.getValue();

                // 取R中五元组中的第二条边的起点为起点
                String startName = quintupleR.getEdge2().getStart().getName();
                // 取R中五元组的第一条边的终点为终点
                String endNameR = quintupleR.getEdge1().getEnd().getName();

                // 以刚才得到的起点为key从S中拿数据
                HashMap bottomMapS = topGraphS.get(startName);

                // 其实这行代码可以没有
                if (bottomMapS == null) {
                    System.out.println("第二个参数给出的图中元素不完全，R中有元素未得到计算");
                    continue;
                }

                // 遍历bottomMapS，符合连接要求的边进行扩展
                // 要求：看论文把。。。不好描述
                Iterator quintupleEntryS = bottomMapS.entrySet().iterator();
                while (quintupleEntryS.hasNext()) {
                    Map.Entry entryS = (Map.Entry) quintupleEntryS.next();
                    String endNameS = (String) entryS.getKey();
                    Quintuple quintupleS = (Quintuple) entryS.getValue();

                    // 判断这个endName2 与R的起点相同的话就直接跳过
                    if (endNameS.equals(quintupleR.getEdge1().getStart().getName())) {
                        continue;
                    } else {
                        // 可进行连接的前提条件
                        if(!quintupleS.getEdge2().getEnd().getName().equals(endNameR)){
                            continue;
                        }

                        // 需要将两条边的权值合到一起
                        // 因为存在含有多个相同权值的情况（只有值不同），这里直接取其最小的一组
                        Vector weightR = quintupleR.getEdge1().getMinWeightGroup();
                        Vector weightS = quintupleS.getEdge1().getMinWeightGroup();
                        Iterator iteratorR = weightR.iterator();
                        Iterator iteratorS = weightS.iterator();

                        // 这里需要减去S（被减去值在R和S中是一样的）中的一个权值
                        Vector weightS2 = quintupleS.getEdge2().getMinWeightGroup();
                        Iterator iteratorS2 = weightS2.iterator();
                        HashMap<String, GraphWeight> resultWeightHashMap = new HashMap<String, GraphWeight>();

                        while(iteratorR.hasNext()){
                            GraphWeight wR= (GraphWeight) iteratorR.next();
                            resultWeightHashMap.put(wR.getName(),wR);
                        }

                        while (iteratorS.hasNext()){
                            GraphWeight wS = (GraphWeight) iteratorS.next();
                            GraphWeight wS2 = (GraphWeight)iteratorS2.next();
                            if(resultWeightHashMap.get(wS.getName()) != null){
                                double value = resultWeightHashMap.get(wS.getName()).getValue()+wS.getValue();
                                value = value- wS2.getValue();
//                                wS.setValue(value);
                                resultWeightHashMap.put(wS.getName(),new NormalWeight(wS.getName(),value));
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

                        GraphEdge edge = new NormalEdge(quintupleR.getEdge1().getStart(),quintupleS.getEdge1().getEnd(),resultWeightVector);

                        // 以后者（S）的终点为key，传入新的bottom
                        // 这里有两个选择
                        // 选择1：直接将更新后的元素放入原bottomR 中，但是这样可能会导致本次放入的元素，再后面有被再次更新，
                        //      原有的元素也可能直接被更新。
                        // 选择2：放入一个新的bottomResult中，这样会将bottomR中原有的元素丢失
                        // 需要在论文中再看看，究竟是什么意思。
                        Quintuple resultQuintuple = new Quintuple();
                        resultQuintuple.setEdge2(quintupleS.getEdge1());
                        resultQuintuple.setEdge1(edge);
                        bottomResult.put(endNameS,resultQuintuple);
                    }
                }
            }
            resultR.put((String) entryR.getKey(), bottomResult);
        }

        return new NormalGraph(resultR);
    }
}

