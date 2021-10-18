package com.example.wumpusworldsimulation.Board;

import java.util.HashMap;
import java.util.Map;

public class MyAi {
    public MyKnowledgeBase knowledgeBase;
    public Agent agent;
    public int size;
    public Map<String,Integer> myMoves=new HashMap<String,Integer>();
    public DeadLoack deadLoack = new DeadLoack();
    public static int numberOfArrow=1;
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


    public String whereSHouldIgo(){
//        if(this.realWorld[agent.getCurrentRow()][agent.getCurrentCol()].contains("glitter")){
//            return glitterFound();
//        }
        if(this.realWorld[agent.getCurrentRow()][agent.getCurrentCol()].contains("stench") && numberOfArrow==0){
            return wumpusAround();
        }
//        if(deadLoack.determineDeadlock(this.knowledgeBase,this.agent)){
//            System.out.println("Its a dead lock");
//            return dead();
//        }
        else return normalMove();
    }
    public String dead(){
        int[] move= new int[]{0, 0, 0, 0};
        if(isItLegalToUseTheBox(agent.getCurrentRow()-1, agent.getCurrentCol()) && knowledgeBase.base[agent.getCurrentRow()-1][agent.getCurrentCol()].equals("#")){
            move[0]=1;
        }
        if(isItLegalToUseTheBox(agent.getCurrentRow(), agent.getCurrentCol()+1) && knowledgeBase.base[agent.getCurrentRow()][agent.getCurrentCol()+1].equals("#")){
            move[1]=1;
        }
        if(isItLegalToUseTheBox(agent.getCurrentRow()+1, agent.getCurrentCol()) && knowledgeBase.base[agent.getCurrentRow()+1][agent.getCurrentCol()].equals("#")){
            move[2]=1;
        }
        if(isItLegalToUseTheBox(agent.getCurrentRow(), agent.getCurrentCol()-1) && knowledgeBase.base[agent.getCurrentRow()][agent.getCurrentCol()-1].equals("#")){
            move[3]=1;
        }
//        for(int i=0;i<4;i++){
//            if(move[i]==1){
//
//            }
//        }
        int mincost  = 9999;
        String movedir = "";
        for(int i=0;i<4;i++){
            if(move[i]>-1){
                if(move[i] <mincost){
                    mincost = move[i];
                    if(i==0) movedir="up";
                    else if(i==1) movedir="right";
                    else if(i==2) movedir="down";
                    else if(i==3) movedir="up";
                }
            }
        }
        return movedir;
    }
    public void chnageWorld(int row , int col){
        if(knowledgeBase.base[row][col].equals("stench")){
            knowledgeBase.base[row][col] = " ";
        }
        else if(knowledgeBase.base[row][col].contains("stench")){
            knowledgeBase.base[row][col] = knowledgeBase.base[row][col].replace("stench","");
        }
    }
    public void setBaseonArow(int row,int col){
        knowledgeBase.base[row][col]=" ";
        if(isItLegalToUseTheBox(row-1,col)){
            chnageWorld(row-1,col);
        }
        if(isItLegalToUseTheBox(row+1,col)){
            chnageWorld(row+1,col);

        }
        if(isItLegalToUseTheBox(row,col+1)){
            chnageWorld(row,col+1);

        }
        if(isItLegalToUseTheBox(row,col-1)){
            chnageWorld(row,col-1);

        }
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
    public String wumpusAround(){
        if(isItLegalToUseTheBox(agent.getCurrentRow()-1, agent.getCurrentCol())){
            if(isItLegalToUseTheBox(agent.getCurrentRow()-1, agent.getCurrentCol()+1)){
                if(this.knowledgeBase.base[agent.getCurrentRow()-1][agent.getCurrentCol()+1].contains("stench")){
                    return "wumpus on #right";
                }
            }
            if(isItLegalToUseTheBox(agent.getCurrentRow()-1, agent.getCurrentCol()-1)) {
                if(this.knowledgeBase.base[agent.getCurrentRow()-1][agent.getCurrentCol()-1].contains("stench")){
                    return "wumpus on #left";
                }
            }
        }
        if(isItLegalToUseTheBox(agent.getCurrentRow(), agent.getCurrentCol()+1)){
            if(isItLegalToUseTheBox(agent.getCurrentRow()+1, agent.getCurrentCol()+1)){
                if(this.knowledgeBase.base[agent.getCurrentRow()+1][agent.getCurrentCol()+1].contains("stench")){
                    return "wumpus on #down";
                }
            }
            if(isItLegalToUseTheBox(agent.getCurrentRow()-1, agent.getCurrentCol()+1)) {
                if(this.knowledgeBase.base[agent.getCurrentRow()-1][agent.getCurrentCol()+1].contains("stench")){
                    return "wumpus #up";
                }
            }
        }
        if(isItLegalToUseTheBox(agent.getCurrentRow()+1, agent.getCurrentCol())){
            if(isItLegalToUseTheBox(agent.getCurrentRow()+1, agent.getCurrentCol()+1)){
                if(this.knowledgeBase.base[agent.getCurrentRow()+1][agent.getCurrentCol()+1].contains("stench")){
                    return "wumpus on #right";
                }
            }
            if(isItLegalToUseTheBox(agent.getCurrentRow()+1, agent.getCurrentCol()-1)) {
                if(this.knowledgeBase.base[agent.getCurrentRow()+1][agent.getCurrentCol()-1].contains("stench")){
                    return "wumpus on #left";
                }
            }
        }
        if(isItLegalToUseTheBox(agent.getCurrentRow(), agent.getCurrentCol()-1)){
            if(isItLegalToUseTheBox(agent.getCurrentRow()+1, agent.getCurrentCol()-1)){
                if(this.knowledgeBase.base[agent.getCurrentRow()+1][agent.getCurrentCol()-1].contains("stench")){
                    return "wumpus on #down";
                }
            }
            if(isItLegalToUseTheBox(agent.getCurrentRow()-1, agent.getCurrentCol()-1)) {
                if(this.knowledgeBase.base[agent.getCurrentRow()-1][agent.getCurrentCol()-1].contains("stench")){
                    return "wumpus on #up";
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
