package com.example.wumpusworldsimulation.Board;

public class MyKnowledgeBase {
    public String[][] knowledgeBase = new String[10][10];

    public MyKnowledgeBase(){
        for(int i=0;i<10;i++){
            for(int j=0;j<10;j++){
                knowledgeBase[i][j]=" ";
            }
        }
    }
}
