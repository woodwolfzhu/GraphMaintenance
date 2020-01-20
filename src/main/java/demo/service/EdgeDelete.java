package demo.service;

import demo.Graph.Graph;
import demo.Graph.NormalGraph;
import demo.GraphOperator.*;

public class EdgeDelete {

    //删除边算法
    Graph EDM(NormalGraph E, NormalGraph SD, NormalGraph derta_E){

        NormalGraph Eu = (NormalGraph) DifferenceOperator.getResult(E,derta_E);
        NormalGraph derta_E_s = (NormalGraph) SubOperator.getResult(derta_E, MulOperator.getResult(Eu, SD));
        NormalGraph SD_star = (NormalGraph) SubOperator.getResult(SD, derta_E_s);
        //3:    省略原文第3行
        NormalGraph derta_P_r = new NormalGraph();
        NormalGraph SD_r = new NormalGraph();


        NormalGraph derta_SD_r = (NormalGraph) SubOperator.getResult(derta_P_r, UnionOperator.getResult(MulOperator.getResult(Eu, SD_star),Eu));
        while (!(derta_SD_r.isGraphNull())){

            derta_SD_r = (NormalGraph) SubOperator.getResult(derta_P_r, UnionOperator.getResult(MulOperator.getResult(Eu, SD_star),Eu));
            SD_r = (NormalGraph) AddOperator.getResult(derta_SD_r, SD_r);
            SD_star = (NormalGraph) SubOperator.getResult(SD_star, derta_SD_r);
            //9:   省略原文第9行
        }

        //11:   省略原文第11行
        NormalGraph derta_P_l = new NormalGraph();
        NormalGraph SD_l = new NormalGraph();
        NormalGraph derta_SD_l = (NormalGraph) SubOperator.getResult(derta_P_l,UnionOperator.getResult(MulOperator.getResult(Eu, SD_star),Eu));
        while (!(derta_SD_l.isGraphNull())){
            derta_SD_l = (NormalGraph) SubOperator.getResult(derta_P_l,UnionOperator.getResult(MulOperator.getResult(Eu, SD_star),Eu));
            SD_l = (NormalGraph) AddOperator.getResult(derta_SD_l, SD_l);
            SD_star = (NormalGraph) SubOperator.getResult(SD_star, derta_SD_l);
            //17:   省略原文第17行
        }

        //19:   省略原文第19行
        NormalGraph derta_P_i = new NormalGraph();

        NormalGraph SD_i = (NormalGraph) IntersectionOperator.getResult(SD_star,derta_P_i);
        SD_star = (NormalGraph) SubOperator.getResult(SD_star, SD_i);
        //22:   省略原文第22行
        Graph SD_ = new NormalGraph();
        SD_star = (NormalGraph) RecalOperator.getResult(Eu, (NormalGraph) SD_, SD_star);      //SD_就是SD- , 由第22行产生

        return SD_star;

    }
}
