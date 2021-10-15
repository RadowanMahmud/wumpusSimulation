package com.example.wumpusworldsimulation.Board;

import java.util.ArrayList;
import java.util.Arrays;

public class Agent {
    private int currentRow= 0;
    private int currentCol= 0;
    private String currentDirection = "down";
    public static KnowledgeBase base = null;
    public Agent(){
        base = new KnowledgeBase();
        base.prepareBase();
    }

    public void addKnowledgeFromPercept(String perceptSpace){

        ArrayList<String> percept = new ArrayList<String>(Arrays.asList(perceptSpace));
        extractKnowledge(percept, "B", "P");
        extractKnowledge(percept, "Gl", "G");
        extractKnowledge(percept, "S", "W");
    }
    private void extractKnowledge(ArrayList<String> percept, String type1, String type2){
        if(percept.contains(type1))
        {
            base.addSentenceToKnowledgeBase(mergeChar(type1, currentCol, currentRow));
            base.addSentenceToKnowledgeBase(
                    "~"+mergeChar(type1, currentCol, currentRow) + getEnvironmentAssumptions(type2)
            );
        }
        else {
            base.addSentenceToKnowledgeBase(mergeChar("~"+type1, currentCol, currentRow));

            if(currentCol -1>=0)
                base.addSentenceToKnowledgeBase(mergeChar("~"+type2, currentCol -1, currentRow));
            if(currentCol +1<10)
                base.addSentenceToKnowledgeBase(mergeChar("~"+type2, currentCol +1, currentRow));
            if(currentRow -1>=0)
                base.addSentenceToKnowledgeBase(mergeChar("~"+type2, currentCol, currentRow -1));
            if(currentRow +1<10)
                base.addSentenceToKnowledgeBase(mergeChar("~"+type2, currentCol, currentRow +1));
        }
    }

    public String mergeChar(String str,int x,int y){
        return str + x+y;
    }

    private String getEnvironmentAssumptions(String type){
        return  ((currentCol -1>=0)? "|"+mergeChar(type, currentCol -1, currentRow):"")
                + ((currentRow -1>=0)? "|"+mergeChar(type, currentCol, currentRow -1):"")
                + ((currentCol +1<10)? "|"+mergeChar(type, currentCol +1, currentRow):"")
                + ((currentRow +1<10)? "|"+mergeChar(type, currentCol, currentRow +1):"");
    }


    public int getCurrentCol() {
        return currentCol;
    }

    public void setCurrentCol(int currentCol) {
        this.currentCol = currentCol;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public String getCurrentDirection() {
        return currentDirection;
    }

    public void setCurrentDirection(String currentDirection) {
        this.currentDirection = currentDirection;
    }
}
