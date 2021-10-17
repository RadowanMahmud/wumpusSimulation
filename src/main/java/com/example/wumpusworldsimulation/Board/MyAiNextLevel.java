package com.example.wumpusworldsimulation.Board;

import java.util.HashMap;
import java.util.Map;

public class MyAiNextLevel {
    public MyKnowledgeBase knowledgeBase;
    public Agent agent;
    public int size;
    public Map<String,Integer> myMoves=new HashMap<String,Integer>();
    public DeadLoack deadLoack = new DeadLoack();
    String realWorld[][];

    public MyAiNextLevel(MyKnowledgeBase knowledgeBase,Agent agent, int size, String[][] world){
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


    public String whereSHouldIgo(){
        if(this.realWorld[agent.getCurrentRow()][agent.getCurrentCol()].contains("glitter")){
            return glitterFound();
        }
        else return normalMove();
    }
    public String glitterFound(){
        if(isItLegalToUseTheBox(agent.getCurrentRow()-1, agent.getCurrentCol())){
            if(isItLegalToUseTheBox(agent.getCurrentRow()-1, agent.getCurrentCol()+1)){
                if(this.knowledgeBase.base[agent.getCurrentRow()-1][agent.getCurrentCol()+1].contains("glitter")){
                    return "right";
                }
            }
            if(isItLegalToUseTheBox(agent.getCurrentRow()-1, agent.getCurrentCol()-1)) {
                if(this.knowledgeBase.base[agent.getCurrentRow()-1][agent.getCurrentCol()-1].contains("glitter")){
                    return "left";
                }
            }
        }
        if(isItLegalToUseTheBox(agent.getCurrentRow(), agent.getCurrentCol()+1)){
            if(isItLegalToUseTheBox(agent.getCurrentRow()+1, agent.getCurrentCol()+1)){
                if(this.knowledgeBase.base[agent.getCurrentRow()+1][agent.getCurrentCol()+1].contains("glitter")){
                    return "down";
                }
            }
            if(isItLegalToUseTheBox(agent.getCurrentRow()-1, agent.getCurrentCol()+1)) {
                if(this.knowledgeBase.base[agent.getCurrentRow()-1][agent.getCurrentCol()+1].contains("glitter")){
                    return "up";
                }
            }
        }
        if(isItLegalToUseTheBox(agent.getCurrentRow()+1, agent.getCurrentCol())){
            if(isItLegalToUseTheBox(agent.getCurrentRow()+1, agent.getCurrentCol()+1)){
                if(this.knowledgeBase.base[agent.getCurrentRow()+1][agent.getCurrentCol()+1].contains("glitter")){
                    return "right";
                }
            }
            if(isItLegalToUseTheBox(agent.getCurrentRow()+1, agent.getCurrentCol()-1)) {
                if(this.knowledgeBase.base[agent.getCurrentRow()+1][agent.getCurrentCol()-1].contains("glitter")){
                    return "left";
                }
            }
        }
        if(isItLegalToUseTheBox(agent.getCurrentRow(), agent.getCurrentCol()-1)){
            if(isItLegalToUseTheBox(agent.getCurrentRow()+1, agent.getCurrentCol()-1)){
                if(this.knowledgeBase.base[agent.getCurrentRow()+1][agent.getCurrentCol()-1].contains("glitter")){
                    return "down";
                }
            }
            if(isItLegalToUseTheBox(agent.getCurrentRow()-1, agent.getCurrentCol()-1)) {
                if(this.knowledgeBase.base[agent.getCurrentRow()-1][agent.getCurrentCol()-1].contains("glitter")){
                    return "up";
                }
            }
        }
        return normalMove();
    }

    public String normalMove(){
        myAvailableMoves();
        mySafeMoves();
        String finalMove = finalMove();
        if(finalMove.equals("up")){
            updateBase(agent.getCurrentRow()-1, agent.getCurrentCol());
            updateCostUponFInalMove(agent.getCurrentRow()-1, agent.getCurrentCol());
        }else if(finalMove.equals("right")){
            updateBase(agent.getCurrentRow(), agent.getCurrentCol()+1);
            updateCostUponFInalMove(agent.getCurrentRow(), agent.getCurrentCol()+1);
        }else if(finalMove.equals("down")){
            updateBase(agent.getCurrentRow()+1, agent.getCurrentCol());
            updateCostUponFInalMove(agent.getCurrentRow()+1, agent.getCurrentCol());
        }else if(finalMove.equals("left")){
            updateBase(agent.getCurrentRow(), agent.getCurrentCol()-1);
            updateCostUponFInalMove(agent.getCurrentRow(), agent.getCurrentCol()-1);
        }
        setBasicMap();
        return finalMove;
    }

    public void updateCostUponFInalMove(int row,int col){
        if(isItLegalToUseTheBox(row-1,col)){
            if(this.knowledgeBase.base[row][col].contains("breeze") || this.knowledgeBase.base[row][col].contains("stench")){
                if(this.knowledgeBase.base[row-1][col].equals("#")){
                    this.knowledgeBase.cost[row-1][col] += 2;
                }
            }
        }
        if(isItLegalToUseTheBox(row,col+1)){
            if(this.knowledgeBase.base[row][col].contains("breeze") || this.knowledgeBase.base[row][col].contains("stench")){
                if(this.knowledgeBase.base[row][col+1].equals("#")){
                    this.knowledgeBase.cost[row][col+1] += 2;
                }
            }
        }
        if(isItLegalToUseTheBox(row+1,col)){
            if(this.knowledgeBase.base[row][col].contains("breeze") || this.knowledgeBase.base[row][col].contains("stench")){
                if(this.knowledgeBase.base[row+1][col].equals("#")){
                    this.knowledgeBase.cost[row+1][col] += 2;
                }
            }
        }
        if (isItLegalToUseTheBox(row, col-1)){
            if(this.knowledgeBase.base[row][col].contains("breeze") || this.knowledgeBase.base[row][col].contains("stench")){
                if(this.knowledgeBase.base[row][col-1].equals("#")){
                    this.knowledgeBase.cost[row][col-1] += 2;
                }
            }
        }
    }

    public void updateBase(int row,int col){
        this.knowledgeBase.cost[row][col]+=3;
        this.knowledgeBase.base[row][col] = this.realWorld[row][col];
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
                    dir.setValue(this.knowledgeBase.cost[agent.getCurrentRow()-1][agent.getCurrentCol()]);
                }
                else if(dir.getKey().equals("right")){
                    dir.setValue(this.knowledgeBase.cost[agent.getCurrentRow()][agent.getCurrentCol()+1]);
                }
                else if(dir.getKey().equals("down")){
                    dir.setValue(this.knowledgeBase.cost[agent.getCurrentRow()+1][agent.getCurrentCol()]);
                }
                else if(dir.getKey().equals("left")){
                    dir.setValue(this.knowledgeBase.cost[agent.getCurrentRow()][agent.getCurrentCol()-1]);
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
