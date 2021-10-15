package com.example.wumpusworldsimulation.Board;

import java.util.HashMap;
import java.util.Map;

public class MyAi {
    public MyKnowledgeBase knowledgeBase;
    public Agent agent;
    public int size;
    public Map<String,Integer> myMoves=new HashMap<String,Integer>();
    String realWorld[][];
    public MyAi(MyKnowledgeBase knowledgeBase,Agent agent, int size, String[][] world){
        myMoves.put("up",0);
        myMoves.put("right",0);
        myMoves.put("down",0);
        myMoves.put("left",0);
        this.knowledgeBase = knowledgeBase;
        this.agent = agent;
        this.size = size;
        this.realWorld = world;
    }

    public String whereSHouldIgo(){
        myAvailableMoves();
        return "right";
    }

    public boolean canImakeThisMove(int row,int col){
        return true;
    }

    public void mySafeMoves(){
        for(Map.Entry<String,Integer> dir: myMoves.entrySet()){
            if(dir.getValue()!= -1){
                if(dir.getKey().equals("up")){
                    if(canImakeThisMove(agent.getCurrentRow()-1, agent.getCurrentCol())){

                    }
                }
                else if(dir.getKey().equals("right")){
                    if(canImakeThisMove(agent.getCurrentRow(), agent.getCurrentCol()+1)){

                    }
                }
                else if(dir.getKey().equals("down")){
                    if(canImakeThisMove(agent.getCurrentRow()+1,agent.getCurrentCol() )){

                    }
                }
                else if(dir.getKey().equals("left")){
                    if(canImakeThisMove(agent.getCurrentRow(), agent.getCurrentCol()-1)){

                    }
                }
            }
        }
    }
    public void myAvailableMoves(){
        if(agent.getCurrentRow() == 0){
            myMoves.put("up",-1);
        }
        if(agent.getCurrentRow() == size-1){
            myMoves.put("down",-1);
        }
        if(agent.getCurrentCol() == 0){
            myMoves.put("left", -1);
        }
        if(agent.getCurrentCol() == size-1){
            myMoves.put("right",-1);
        }
    }
}
