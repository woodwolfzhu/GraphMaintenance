package demo;


import demo.Assist.CSVReadAssist;
import demo.Graph.Graph;
import demo.Graph.NormalGraph;
import demo.GraphOperator.forInsert.MinOperator;

public class Application {

    public static void main(String[] args) {
//        Graph graph=new NormalGraph();
//        ((NormalGraph) graph).readGraphFile("G:\\Users\\Administrator\\GraphMaintenance\\src\\main\\resources\\graph1.csv");

        NormalGraph sdGraph = new NormalGraph();
        sdGraph.readGraphFile("G:\\Users\\Administrator\\GraphMaintenance\\src\\main\\resources\\graph1.csv");
//        System.out.println(graph.toString());
        System.out.println("what the fuck!");

        Graph graph =  MinOperator.getResult(sdGraph);
        CSVReadAssist.printAll(graph.getTopGraph());
    }
}
