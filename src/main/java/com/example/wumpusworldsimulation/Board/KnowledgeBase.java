package com.example.wumpusworldsimulation.Board;

import java.util.ArrayList;
import java.util.List;

public class KnowledgeBase {
    public boolean[][] visited = new boolean[10][10];
    private static List<String> knowledbaseSentence = new ArrayList<>();

    public void prepareBase(){
        for(int i=0;i<10;i++){
            for(int j=0;j<10;j++){
                visited[i][j]=false;
            }
        }
        visited[0][0]= true;
    }
    public void printKB(){
        for(String str: knowledbaseSentence)
            System.out.println(str);

        System.out.println("---------------------------------");
    }
    public void addSentenceToKnowledgeBase(String sentence){
        if(!knowledbaseSentence.contains(sentence))
            knowledbaseSentence.add(sentence);
    }
}
