package demo.GraphOperator;

import demo.Graph.Graph;
import demo.Graph.SDGraph;

import java.util.HashMap;

public class minOperator implements Operator{
    HashMap graph;
    public minOperator(HashMap graph) {
        this.graph=graph;
    }

    public Graph getResult() {
        return null;
    }
}
