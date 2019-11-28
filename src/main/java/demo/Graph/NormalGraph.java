package demo.Graph;

import java.util.HashMap;
import demo.Assist.CSVReadAssist;

/*
* 因为算法中设计大量的遍历、比较动作，所以不同的数据结构可能会对时间早成很大的影响
* 后期的优化可以从数据结构下手
* 还是需要明确需求，根据需求做出对应的结构
* 如果两个节点之间有多条边，那么可以进行合并，使Edge 类 中有多个Weight 成员变量
* 或者Weight 里有多个成员变量
* */
public class NormalGraph implements Graph {
    // key为起点的名字，value 为edgeMap
    private HashMap<String,HashMap> topGraph;

    /*bottomGraph 中key 为终点的名字，如果两个节点直接不需要保留多个节点的话，
    key可以改为终点的名字，这里bottom只为说明方便，实际并无作用。*/
    private HashMap<String,Quintuple> bottomGraph;



    public NormalGraph() {
        bottomGraph =new HashMap<String, Quintuple>();
        topGraph = new HashMap<String, HashMap>();
    }

    public NormalGraph(HashMap<String, HashMap> topGraph, HashMap<String, Quintuple> bottomGraph) {
        this.topGraph = topGraph;
        this.bottomGraph = bottomGraph;
    }

    public HashMap<String, Quintuple> getBottomGraph() {
        return bottomGraph;
    }

    public HashMap<String, HashMap> getTopGraph() {
        return topGraph;
    }

    public void add(String key, HashMap value) {
        topGraph.put(key,value);
    }

    public void delete(String key) {
        topGraph.remove(key);
    }

    public int getSize() {
        return topGraph.size();
    }


    @Override
    public String toString() {

        return "输出完毕";
    }

    public void readGraphFile(String filePath){
        topGraph=CSVReadAssist.read(filePath);
    }
}
