package demo.service;

import demo.Graph.Graph;
import demo.Graph.NormalGraph;
import demo.Graph.SDGraph;
import demo.GraphOperator.*;

import java.util.LinkedList;
import java.util.Queue;

public class EdgeInsert {

    Graph getResult(NormalGraph E, SDGraph SD, NormalGraph derta_E) {

        // 取并集，而不是Add
        NormalGraph Eu = (NormalGraph) UnionOperator.getResult(E,derta_E);

        NormalGraph derta_E_s = (NormalGraph) PruneOperator.getResult(MinOperator.getResult(derta_E), SD);  //∆Es
        SDGraph SD_star = (SDGraph) AddOperator.getResult(derta_E_s, SD);                                   //SD*

        // Queue<NormalGraph> Q1 = new LinkedList<NormalGraph>();                                           //创建一个空队列Q
        SDGraph SD_r = new SDGraph();                                                                    //空的SDr
        // 论文中derta_P_r的运算与程序中的MulOperator完全是一个意思
        NormalGraph derta_P_r = (NormalGraph) ForwardOperator.getResult(derta_E_s, Eu,true);

        //当derta_SD_r不为空的时候循环，虽然这里和论文中有些不同，但是不影响最后的结果
        SDGraph derta_SD_r = (SDGraph) PruneOperator.getResult(MinOperator.getResult(derta_P_r), SD_star);
        while (!(derta_SD_r.isGraphNull())) {
            derta_SD_r = (SDGraph) PruneOperator.getResult(MinOperator.getResult(derta_P_r), SD_star);
            SD_star = (SDGraph) AddOperator.getResult(derta_SD_r, SD_star);

            //省略两行贪心算法内容
            //used by the greedy variant only
            //used by the greedy variant only

            SD_r = (SDGraph) AddOperator.getResult(derta_SD_r, SD_r);

            //12:   省略原文第12行
            derta_P_r = (NormalGraph) ForwardOperator.getResult(derta_E_s, Eu,false);
        }

        // Queue<NormalGraph> Q2 = new LinkedList<NormalGraph>();
        SDGraph SD_l = new SDGraph();                                                                    //空的SDl
        NormalGraph derta_P_l = (NormalGraph) BackWardOperator.getResult(Eu, derta_E_s,true);

        SDGraph derta_SD_l = (SDGraph) PruneOperator.getResult(MinOperator.getResult(derta_P_l), SD_star);
        while (!(derta_SD_l.isGraphNull())) {

            derta_SD_l = (SDGraph) PruneOperator.getResult(MinOperator.getResult(derta_P_l), SD_star);
            SD_star = (SDGraph) AddOperator.getResult(derta_SD_l, SD_star);

            //省略两行贪心算法内容
            //used by the greedy variant only
            //used by the greedy variant only

            SD_l = (SDGraph) AddOperator.getResult(derta_SD_l, SD_l);

            //23:    省略原文第23行
            derta_P_l = (NormalGraph) BackWardOperator.getResult(Eu,derta_SD_l,false);
        }
//======================================================================================================================
        // 下面一行的代码需要修改
        // 需要单写一个类来处理。
        NormalGraph derta_P_i = (NormalGraph) MulOperator.getResult(SD_l, SD_r);
        SDGraph derta_SD_i = (SDGraph) PruneOperator.getResult(MinOperator.getResult(derta_P_i), SD_star);

        SD_star = (SDGraph) AddOperator.getResult(derta_SD_i, SD_star);
        return SD_star;
    }
}
