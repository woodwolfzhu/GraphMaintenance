package demo.GraphOperator;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.Union;
import demo.Graph.Graph;
import demo.Graph.NormalGraph;
import demo.Graph.SDGraph;
import demo.GraphOperator.AddOperator;
import demo.GraphOperator.MinOperator;
import demo.GraphOperator.PruneOperator;

public class RecalOperator extends Operator {
    public static Graph getResult(NormalGraph Eu, SDGraph SD_, SDGraph SD_star){
        Graph derta_P = UnionOperator.getResult(Eu,MulOperator.getResult(Eu,SD_star));
        // 2
        Graph derta_P_point = new NormalGraph();
        Graph derta_SD = MinOperator.getResult(derta_P_point);
        Graph SD_add = derta_SD;
        while (!derta_SD.isGraphNull()){

            derta_P=MulOperator.getResult(Eu,derta_SD);
            // 7
            derta_P_point = null;
            derta_SD = PruneOperator.getResult(MinOperator.getResult(derta_P_point),SD_add);
            SD_add=AddOperator.getResult(derta_SD,SD_add);
        }
        SD_star = (SDGraph) UnionOperator.getResult(SD_star,SD_add);
        return SD_star;
    }

}
