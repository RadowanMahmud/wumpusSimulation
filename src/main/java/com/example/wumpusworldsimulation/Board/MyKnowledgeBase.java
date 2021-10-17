package com.example.wumpusworldsimulation.Board;

public class MyKnowledgeBase {
    public String[][] base = new String[10][10];
    public int[][] cost = new int[10][10];
    public double[][] probabilty = new double[10][10];

    public MyKnowledgeBase(){
        for(int i=0;i<10;i++){
            for(int j=0;j<10;j++){
                base[i][j]="#";
                cost[i][j]=0;
                probabilty[i][j]=0.0;
            }
        }
        cost[0][0]=1;
        base[0][0] = " ";
    }
}
