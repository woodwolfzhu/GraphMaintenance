package demo.GraphOperator.forRecal;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.Union;
import demo.Graph.Graph;
import demo.Graph.NormalGraph;
import demo.GraphOperator.forDelete.UnionOperator;
import demo.GraphOperator.forInsert.*;

public class RecalOperator extends Operator {
    public static Graph getResult(NormalGraph Eu, NormalGraph SD_, NormalGraph SD_star){
        Graph derta_P = UnionOperator.getResult(Eu, MulOperator.getResult(Eu,SD_star));
        // 2
        Graph derta_P_point = RecalUnion.getResult(derta_P,SD_);
        Graph derta_SD = MinOperator.getResult(derta_P_point);
        Graph SD_add = derta_SD;
        while (!derta_SD.isGraphNull()){

            derta_P=MulOperator.getResult(Eu,derta_SD);
            // 7
            derta_P_point = RecalUnion.getResult(derta_P,SD_);
            derta_SD = PruneOperator.getResult(MinOperator.getResult(derta_P_point),SD_add);
            SD_add= AddOperator.getResult(derta_SD,SD_add);
        }
        SD_star = (NormalGraph) UnionOperator.getResult(SD_star,SD_add);
        return SD_star;
    }

}
