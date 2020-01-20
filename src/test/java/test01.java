import demo.Graph.Graph;
import demo.Graph.NormalGraph;

import demo.GraphOperator.forDelete.UnionOperator;
import demo.GraphOperator.forInsert.*;
import demo.service.EdgeDelete;
import demo.service.EdgeInsert;
import org.junit.Before;
import org.junit.Test;

public class test01 {

    NormalGraph testR;
    NormalGraph sdGraph;
    NormalGraph testS;

    @Before
    public void init(){
        testR = new NormalGraph();
        testR.readGraphFile("G:\\Users\\Administrator\\GraphMaintenance\\src\\main\\resources\\test3.csv");

        testS = new NormalGraph();
        testS.readGraphFile("G:\\Users\\Administrator\\GraphMaintenance\\src\\main\\resources\\test4.csv");
    }

    @Test
    public void pruneTest(){
        NormalGraph S = (NormalGraph) PruneOperator.getResult(sdGraph, testR);
        System.out.println(S.toString());
    }

    @Test
    public void minTest(){
        NormalGraph result = (NormalGraph) MinOperator.getResult(testR);
        System.out.println(result.toString());
    }

    @Test
    public void addTest(){
        NormalGraph result = (NormalGraph) AddOperator.getResult(testR, testS);
        System.out.println(result);
    }

    @Test
    public void subTest(){
        Graph result = SubOperator.getResult(testR, testS);
        System.out.println(result.toString());
    }

    @Test
    public void mulTest(){
        Graph result = MulOperator.getResult(testR, testS);
        System.out.println(result);

    }

    @Test
    public void unionTest(){
        Graph result = UnionOperator.getResult(testR, testS);
        System.out.println(result);

    }

    @Test
    public void edgeInsertTest(){
        NormalGraph normalGraph=new NormalGraph();
        normalGraph.readGraphFile("G:\\Users\\Administrator\\GraphMaintenance\\src\\main\\resources\\graph1.csv");
        NormalGraph sdGraph = new NormalGraph();
        sdGraph.readGraphFile("G:\\Users\\Administrator\\GraphMaintenance\\src\\main\\resources\\graph2.csv");
        NormalGraph derta = new NormalGraph();
        derta.readGraphFile("G:\\Users\\Administrator\\GraphMaintenance\\src\\main\\resources\\derta.csv");

        Graph result = EdgeInsert.getResult(normalGraph, sdGraph, derta);
        System.out.println(result);
    }

    @Test
    public void edgeDeleteTest(){
        NormalGraph normalGraph=new NormalGraph();
        normalGraph.readGraphFile("G:\\Users\\Administrator\\GraphMaintenance\\src\\main\\resources\\delete_E.csv");
        NormalGraph sdGraph = new NormalGraph();
        sdGraph.readGraphFile("G:\\Users\\Administrator\\GraphMaintenance\\src\\main\\resources\\graph2.csv");
        NormalGraph derta = new NormalGraph();
        derta.readGraphFile("G:\\Users\\Administrator\\GraphMaintenance\\src\\main\\resources\\derta.csv");

        Graph result = EdgeInsert.getResult(normalGraph, sdGraph, derta);
        System.out.println(result);
        System.out.println("==============================================================");

        Graph result1 = EdgeDelete.getResult(normalGraph, (NormalGraph) result, derta);
        System.out.println(result1);
    }
}
