package com.example.wumpusworldsimulation.Board;

public class DeadLoack {
    private String[][] myBase;
    public boolean freeBoxAvailable = false;

    public void setMyBase(String[][] knowledgebase){
        this.myBase = knowledgebase;
    }

    public boolean isItDeadLock(int row,int col){
        if (isItLegalToUseTheBox(row-1,col)){
            determineItDeadLOck(row-1, col);
            if (freeBoxAvailable) return false;
        }
        if (isItLegalToUseTheBox(row,col+1)){
            determineItDeadLOck(row, col+1);
            if(freeBoxAvailable) return false;
        }
        if (isItLegalToUseTheBox(row+1,col)){
            determineItDeadLOck(row+1, col);
            if(freeBoxAvailable) return false;
        }
        if (isItLegalToUseTheBox(row,col-1)){
            determineItDeadLOck(row, col-1);
            if(freeBoxAvailable) return false;
        }
        return freeBoxAvailable;
    }
    public boolean determineItDeadLOck(int row,int col){

        if(this.myBase[row][col].equals("#") || this.myBase[row][col].equals("glitter")){
            freeBoxAvailable = true;
            return freeBoxAvailable;
        }else if(this.myBase[row][col].equals("~")){
            return false;
        }else if(this.myBase[row][col].equals(" ")){
            this.myBase[row][col]="~";
        }else if(this.myBase[row][col].contains("breeze") || this.myBase[row][col].contains("stench")){
            return false;
        }

        if (isItLegalToUseTheBox(row-1,col)){
            if(freeBoxAvailable){
                return freeBoxAvailable;
            }else {
                determineItDeadLOck(row-1, col);
            }
        }
        if (isItLegalToUseTheBox(row,col+1)){
            if(freeBoxAvailable){
                return freeBoxAvailable;
            }else {
                determineItDeadLOck(row, col+1);
            }
        }
        if (isItLegalToUseTheBox(row+1,col)){
            if(freeBoxAvailable){
                return freeBoxAvailable;
            }else {
                determineItDeadLOck(row+1, col);
            }
        }
        if (isItLegalToUseTheBox(row,col-1)){
            if(freeBoxAvailable){
                return freeBoxAvailable;
            }else {
                determineItDeadLOck(row, col-1);
            }
        }
        return freeBoxAvailable;
    }

    public boolean isItLegalToUseTheBox(int row , int col){
        if(row<0 || row>9 || col<0 || col>9){
            return false;
        }
        return true;
    }


}
