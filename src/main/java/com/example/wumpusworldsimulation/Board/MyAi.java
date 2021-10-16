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
        setBasicMap();
        this.knowledgeBase = knowledgeBase;
        this.agent = agent;
        this.size = size;
        this.realWorld = world;
    }

    public void setBasicMap(){
        myMoves.put("up",0);
        myMoves.put("right",0);
        myMoves.put("down",0);
        myMoves.put("left",0);
    }

    public void GoldDetection(){

    }

    public String whereSHouldIgo(){
        myAvailableMoves();
        mySafeMoves();
        String finalMove = finalMove();
        if(finalMove.equals("up")){
            updateBase(agent.getCurrentRow()-1, agent.getCurrentCol());
        }else if(finalMove.equals("right")){
            updateBase(agent.getCurrentRow(), agent.getCurrentCol()+1);
        }else if(finalMove.equals("down")){
            updateBase(agent.getCurrentRow()+1, agent.getCurrentCol());
        }else if(finalMove.equals("left")){
            updateBase(agent.getCurrentRow(), agent.getCurrentCol()-1);
        }
        setBasicMap();
        return finalMove;
    }
    public void updateBase(int row,int col){
        this.knowledgeBase.cost[row][col]++;
        this.knowledgeBase.base[row][col] = this.realWorld[row][col];
        System.out.println(row +" "+ col);
        System.out.println(knowledgeBase.base[row][col]);
        System.out.println(knowledgeBase.cost[row][col]);
    }

    public String finalMove(){
        int mincost=99999;
        String move="right";
        for(Map.Entry<String,Integer> dir: myMoves.entrySet()){
            if(dir.getValue()>-1){
                if(dir.getValue()<mincost){
                    mincost = dir.getValue();
                    move = dir.getKey();
                }
            }
        }
        return move;
    }
    public boolean canImakeThisMove(int row,int col){
        if(isItLegalToUseTheBox(row,col+1)){
            if(!this.knowledgeBase.base[row][col+1].equals("#") && !this.knowledgeBase.base[row][col+1].contains("breeze")
                    && !this.knowledgeBase.base[row][col+1].contains("stench")){
                return true;
            }
        }
        if(isItLegalToUseTheBox(row+1,col)){
            if(!this.knowledgeBase.base[row+1][col].equals("#") && !this.knowledgeBase.base[row+1][col].contains("breeze")
                    && !this.knowledgeBase.base[row+1][col].contains("stench")){
                return true;
            }
        }
        if(isItLegalToUseTheBox(row-1,col)){
            if(!this.knowledgeBase.base[row-1][col].equals("#") && !this.knowledgeBase.base[row-1][col].contains("breeze")
                    && !this.knowledgeBase.base[row-1][col].contains("stench")){
                return true;
            }
        }
        if(isItLegalToUseTheBox(row,col-1)){
            if(!this.knowledgeBase.base[row][col-1].equals("#") && !this.knowledgeBase.base[row][col-1].contains("breeze")
                    && !this.knowledgeBase.base[row][col-1].contains("stench")){
                return true;
            }
        }
        return false;
    }

    public boolean isItLegalToUseTheBox(int row , int col){
        if(row<0 || row>size-1 || col<0 || col>size-1){
            return false;
        }
        return true;
    }

    public void mySafeMoves(){
        for(Map.Entry<String,Integer> dir: myMoves.entrySet()){
            if(dir.getValue()!= -1){
                if(dir.getKey().equals("up")){
                    if(canImakeThisMove(agent.getCurrentRow()-1, agent.getCurrentCol())){
                        dir.setValue(this.knowledgeBase.cost[agent.getCurrentRow()-1][agent.getCurrentCol()]);
                    }else{
                        dir.setValue(-1);
                    }
                }
                else if(dir.getKey().equals("right")){
                    if(canImakeThisMove(agent.getCurrentRow(), agent.getCurrentCol()+1)){
                        dir.setValue(this.knowledgeBase.cost[agent.getCurrentRow()][agent.getCurrentCol()+1]);
                    }else{
                        dir.setValue(-1);
                    }
                }
                else if(dir.getKey().equals("down")){
                    if(canImakeThisMove(agent.getCurrentRow()+1,agent.getCurrentCol() )){
                        dir.setValue(this.knowledgeBase.cost[agent.getCurrentRow()+1][agent.getCurrentCol()]);
                    }else{
                        dir.setValue(-1);
                    }
                }
                else if(dir.getKey().equals("left")){
                    if(canImakeThisMove(agent.getCurrentRow(), agent.getCurrentCol()-1)){
                        dir.setValue(this.knowledgeBase.cost[agent.getCurrentRow()][agent.getCurrentCol()-1]);
                    }else{
                        dir.setValue(-1);
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
