package demo;


import demo.Graph.Graph;
import demo.Graph.NormalGraph;
import demo.Graph.Quintuple;
import demo.Graph.SDGraph;

public class Application {


    public static void main(String[] args) {
//        Graph graph=new NormalGraph();
//        ((NormalGraph) graph).readGraphFile("G:\\Users\\Administrator\\GraphMaintenance\\src\\main\\resources\\graph1.csv");

        SDGraph sdGraph = new SDGraph();
        sdGraph.readGraphFile("G:\\Users\\Administrator\\GraphMaintenance\\src\\main\\resources\\graph1.csv");
//        System.out.println(graph.toString());
        System.out.println("what the fuck!");
    }
}
