package com.example.wumpusworldsimulation.Board;

import java.util.ArrayList;

public class DeadLoack {

    MyKnowledgeBase knowledgeBase;
    Agent agent;
    public boolean freeBoxAvailable = false;

    public boolean determineDeadlock(MyKnowledgeBase knowledge,Agent agent){
        this.knowledgeBase = knowledge;
        this.agent = agent;
        String[][] myBase= new String[10][10];
        for (int i=0;i<10;i++){
            for(int j=0;j<10;j++){
                if(knowledgeBase.base[i][j].contains("breeze") || knowledgeBase.base[i][j].contains("stench")){
                    myBase[i][j]="~";
                }else{
                    myBase[i][j]=" ";
                }
            }
        }

        for (int i=0;i<10;i++){
            for(int j=0;j<10;j++){
                if(knowledgeBase.cost[i][j]==0){
                    return isitDeadlock(i,j,myBase);
                }
            }
        }
        return true;
    }

    public boolean isitDeadlock(int row,int col, String[][] myBase){
        ArrayList<Data> list = new ArrayList<Data>();
        list.add(new Data(agent.getCurrentRow(), agent.getCurrentCol()));
        while(list.size()>0) {
            Data position = list.get(0);
            list.remove(0);
            myBase[position.row][position.col] = "~";

            if (position.row == row && position.col == col)
                return false;

            if (isItLegalToUseTheBox(position.row+1,position.col) && myBase[position.row+1][position.col].equals(" "))
            {
                list.add(new Data(position.row+1,position.col));
            }
            if (isItLegalToUseTheBox(position.row,position.col+1) && myBase[position.row][position.col+1].equals(" "))
            {
                list.add(new Data(position.row,position.col+1));
            }
            if (this.isItLegalToUseTheBox(position.row,position.col-1)&& myBase[position.row][position.col-1].equals(" "))
            {
                list.add(new Data(position.row,position.col-1));
            }
            if (this.isItLegalToUseTheBox(position.row-1,position.col)&& myBase[position.row-1][position.col].equals(" "))
            {
                list.add(new Data(position.row-1,position.col));
            }
        }
        return true;
    }

    public boolean isItLegalToUseTheBox(int row,int col){
        if(row<0 || row>9 || col<0 || col>9){
            return false;
        }
        return true;
    }


}
class Data{
    int row;
    int col;
    public Data(int row, int col){
        this.row = row;
        this.col = col;
    }
}
