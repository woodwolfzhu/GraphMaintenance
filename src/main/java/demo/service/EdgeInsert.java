package demo.service;

import demo.Graph.Graph;
import demo.Graph.NormalGraph;
import demo.GraphOperator.forDelete.UnionOperator;
import demo.GraphOperator.forInsert.*;

public class EdgeInsert {

     public static Graph getResult(NormalGraph E, NormalGraph SD, NormalGraph derta_E) {

        // 取并集，而不是Add
        NormalGraph Eu = (NormalGraph) UnionOperator.getResult(E,derta_E);

        NormalGraph derta_E_s = (NormalGraph) PruneOperator.getResult(MinOperator.getResult(derta_E), SD);  //∆Es
        NormalGraph SD_star = (NormalGraph) AddOperator.getResult(derta_E_s, SD);                                   //SD*

        // Queue<NormalGraph> Q1 = new LinkedList<NormalGraph>();                                           //创建一个空队列Q
        NormalGraph SD_r = new NormalGraph();                                                                    //空的SDr
        // 论文中derta_P_r的运算与程序中的MulOperator完全是一个意思
        NormalGraph derta_P_r = (NormalGraph) ForwardOperator.getResult(derta_E_s, Eu,true);

        //当derta_SD_r不为空的时候循环，虽然这里和论文中有些不同，但是不影响最后的结果
        NormalGraph derta_SD_r = (NormalGraph) PruneOperator.getResult(MinOperator.getResult(derta_P_r), SD_star);
        while (!(derta_SD_r.isGraphNull())) {
            derta_SD_r = (NormalGraph) PruneOperator.getResult(MinOperator.getResult(derta_P_r), SD_star);
            SD_star = (NormalGraph) AddOperator.getResult(derta_SD_r, SD_star);

            //省略两行贪心算法内容
            //used by the greedy variant only
            //used by the greedy variant only

            SD_r = (NormalGraph) AddOperator.getResult(derta_SD_r, SD_r);

            //12:   省略原文第12行
            derta_P_r = (NormalGraph) ForwardOperator.getResult(derta_SD_r, Eu,false);
        }

        // Queue<NormalGraph> Q2 = new LinkedList<NormalGraph>();
        NormalGraph SD_l = new NormalGraph();                                                                    //空的SDl
        NormalGraph derta_P_l = (NormalGraph) BackWardOperator.getResult(Eu, derta_E_s,true);

        NormalGraph derta_SD_l = (NormalGraph) PruneOperator.getResult(MinOperator.getResult(derta_P_l), SD_star);
        while (!(derta_SD_l.isGraphNull())) {

            derta_SD_l = (NormalGraph) PruneOperator.getResult(MinOperator.getResult(derta_P_l), SD_star);
            SD_star = (NormalGraph) AddOperator.getResult(derta_SD_l, SD_star);

            //省略两行贪心算法内容
            //used by the greedy variant only
            //used by the greedy variant only

            SD_l = (NormalGraph) AddOperator.getResult(derta_SD_l, SD_l);

            //23:    省略原文第23行
            derta_P_l = (NormalGraph) BackWardOperator.getResult(Eu,derta_SD_l,false);
        }
//======================================================================================================================
        // 下面一行的代码需要修改
        // 需要单写一个类来处理。

        NormalGraph derta_P_i = (NormalGraph) ConnectOperator.getResult(SD_l, SD_r);
        NormalGraph derta_SD_i = (NormalGraph) PruneOperator.getResult(MinOperator.getResult(derta_P_i), SD_star);

        SD_star = (NormalGraph) AddOperator.getResult(derta_SD_i, SD_star);
        return SD_star;
    }
}
