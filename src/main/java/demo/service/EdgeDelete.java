package demo.service;

import demo.Graph.Graph;
import demo.Graph.NormalGraph;
import demo.GraphOperator.forDelete.*;
import demo.GraphOperator.forInsert.*;
import demo.GraphOperator.forRecal.RecalOperator;

public class EdgeDelete {

    //删除边算法
    public static Graph getResult(NormalGraph E, NormalGraph SD, NormalGraph derta_E) {

        NormalGraph Eu = (NormalGraph) DifferenceOperator.getResult(E, derta_E);
        NormalGraph derta_E_s = (NormalGraph) SubOperator.getResult(derta_E, MulOperator.getResult(Eu, SD));
        NormalGraph SD_star = (NormalGraph) SubOperator.getResult(SD, derta_E_s);
        NormalGraph derta_P_r = (NormalGraph) DelForwardOperator.getResult(derta_E_s,E,SD_star,true);
        NormalGraph SD_r = new NormalGraph();


        NormalGraph derta_SD_r = (NormalGraph) SubOperator.getResult(derta_P_r, UnionOperator.getResult(MulOperator.getResult(Eu, SD_star), Eu));
        while (!(derta_SD_r.isGraphNull())) {

            derta_SD_r = (NormalGraph) SubOperator.getResult(derta_P_r, UnionOperator.getResult(MulOperator.getResult(Eu, SD_star), Eu));
            SD_r = (NormalGraph) UnionOperator.getResult(derta_SD_r, SD_r);
            SD_star = (NormalGraph) SubOperator.getResult(SD_star, derta_SD_r);

            derta_P_r = (NormalGraph) DelForwardOperator.getResult(derta_SD_r,E,SD_star,false);
        }

        NormalGraph derta_P_l = (NormalGraph) DelBackwardOperator.getResult(E,derta_E_s,SD_star,true);
        NormalGraph SD_l = new NormalGraph();
        NormalGraph derta_SD_l = (NormalGraph) SubOperator.getResult(derta_P_l, UnionOperator.getResult(MulOperator.getResult(Eu, SD_star), Eu));
        while (!(derta_SD_l.isGraphNull())) {
            derta_SD_l = (NormalGraph) SubOperator.getResult(derta_P_l, UnionOperator.getResult(MulOperator.getResult(Eu, SD_star), Eu));
            SD_l = (NormalGraph) UnionOperator.getResult(derta_SD_l, SD_l);
            SD_star = (NormalGraph) SubOperator.getResult(SD_star, derta_SD_l);

            derta_P_l = (NormalGraph) DelBackwardOperator.getResult(E,derta_SD_l,SD_star,false);
        }

        //19:   省略原文第19行
        NormalGraph derta_P_i = (NormalGraph) ConnectOperator.getResult(SD_l,SD_r);

        NormalGraph SD_i = (NormalGraph) IntersectionOperator.getResult(SD_star, derta_P_i);
        SD_star = (NormalGraph) SubOperator.getResult(SD_star, SD_i);

        NormalGraph temp1 = (NormalGraph) UnionOperator.getResult(derta_E_s,SD_i);
        NormalGraph temp2 = (NormalGraph) UnionOperator.getResult(SD_r,SD_l);

        NormalGraph SD_ = (NormalGraph) UnionOperator.getResult(temp1,temp2);
        SD_star = (NormalGraph) RecalOperator.getResult(Eu, (NormalGraph) SD_, SD_star);      //SD_就是SD- , 由第22行产生

        return SD_star;

    }
}
