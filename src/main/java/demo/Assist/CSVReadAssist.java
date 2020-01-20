package demo.Assist;

import com.csvreader.CsvReader;
import demo.Graph.Quintuple;
import demo.GraphEdge.GraphEdge;
import demo.GraphEdge.NormalEdge;
import demo.GraphNode.GraphNode;
import demo.GraphNode.NormalNode;
import demo.GraphWeight.GraphWeight;
import demo.GraphWeight.NormalWeight;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

/*
 * 当我们在 HashMap存入某个对象object时，HashMap应该直接建立了对其的引用，
 * 之后我们如果修改了object，相应的HashMap 中的值也会跟着改变
 * 所以如果想要使用同一个变量object 想HashMap中多次存入时，
 * 应当每存入一次，就重新给object new一个新地址*/
public class CSVReadAssist {


    // key为起点的名字，value 为edgeMap
    private static HashMap<String, HashMap> topGraph;

    public static HashMap read(String filePath) {
        /*初始化，全部以三元组的形式读入Quintuple 中的第一个成员变量中
         读入一条数据，首先获取graph 中对应的map，将新的记录存入，然后更新graph 对应的value
         现在无法处理两个节点之间有多条边的情况，只保留最后一次出现的边
         必须得实现，现在就得实现，不然后面的运算符没法实现
         Weight类设置多个 权值，每有多出来一条边就给一个赋值*/
        try {
            topGraph = new HashMap<String, HashMap>();
            CsvReader csvReader = new CsvReader(filePath);

            while (csvReader.readRecord()) {
                GraphNode startNode = new NormalNode();
                GraphNode endNode = new NormalNode();
                GraphWeight weight = new NormalWeight();
                // 读入起点、终点、权重，权重应该包括名字和value，这里名字先用字母a代替
                startNode.setName(csvReader.get(0));
                endNode.setName(csvReader.get(1));
                String name = "a";
                weight.setName(name);
                weight.setValue(csvReader.get(2));


                // 由上面的信息构建一条边
                Vector weightVector = new Vector();
                weightVector.add(weight);
                GraphEdge edge = new NormalEdge(startNode, endNode, weightVector);
                // 初始化由此产生的的五元组，只有一条边
                Quintuple quintuple = new Quintuple(edge);


                // bottom 是底层的一个图，key为终点，value五元组。bottom保存着以startNode 为起点，其他点为终点的所有边
                HashMap bottom = topGraph.get(startNode.getName());

                // 检查是否已经存在以starNode 为起点的边
                if (bottom == null) {
                    // 暂时没有以startNode为起点的边，那么直接创建、放入新元素就可以
                    bottom = new HashMap<String, Quintuple>();
                    bottom.put(endNode.getName(), quintuple);
                } else {
                    // 已经存在以startNode为起点的边

                    // 接着判断是否有以endNode为终点的边
                    Quintuple tempQuintuple = (Quintuple) bottom.get(endNode.getName());
                    Vector tempWeight;

                    if (tempQuintuple == null) {
                        // 如果没有以endNode为终点的边
                        tempQuintuple = new Quintuple();
                        tempWeight = new Vector();
                        tempWeight.add(weight);
                        bottom.put(endNode.getName(), quintuple);
                    } else {
                        // 如果已经存在以endNode 为终点的边，则需判断这次读入的权重是否已经存在，存在则更新，不存在则新加入
                        tempWeight = tempQuintuple.getEdge1().getWeightVector();
                        String tempWeightName;
                        // flag 用来表示tempWight是否被更新过
//                        boolean flag = false;
//                        for (int i = 0; i < tempWeight.size(); i++) {
//                            tempWeightName = ((GraphWeight) tempWeight.get(i)).getName();
//
//                            // 如果此次读入的权值的名字已经存在，则将原有的元素删掉，存入新读入的值。
//                            if (tempWeightName.equals(name)) {
//                                tempWeight.setElementAt(weight, i);
//                                flag = true;
//                                break;
//                            }
//                        }
//                        // 如果此次读入的权值的名字不存在，则将其加入
//                        if (!flag) {
//                            tempWeight.add(weight);
//                        }
                        tempWeight.add(weight);
                        edge = new NormalEdge(startNode, endNode, tempWeight);
                        quintuple = new Quintuple(edge);
                        bottom.put(endNode.getName(), quintuple);
                    }
                }
                topGraph.put(startNode.getName(), bottom);
//                System.out.println(edge.toString());
            }
//            printAll(topGraph);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return topGraph;
    }

    public static void printAll(HashMap topGraph) {
        // 遍历topGraph
        Iterator iterator = topGraph.entrySet().iterator();
        if (iterator == null) {
            System.out.println("iterator is null");
        }
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            String key = (String) entry.getKey();
            System.out.println(key+":");
            HashMap value = (HashMap) entry.getValue();
            Iterator bottomIterator = value.entrySet().iterator();
            while(bottomIterator.hasNext()){
                Map.Entry entry1 = (Map.Entry) bottomIterator.next();
                String endName = (String) entry1.getKey();
                Quintuple quintuple = (Quintuple) entry1.getValue();
                System.out.println("\t"+quintuple.getEdge1());
            }
        }
    }
}
