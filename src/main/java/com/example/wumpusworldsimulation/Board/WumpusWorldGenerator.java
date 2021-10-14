package com.example.wumpusworldsimulation.Board;

import java.util.Random;

public class WumpusWorldGenerator {
    private String[][] board = new String[10][10];
    private Random raandom = new Random();

    public String[][] getGeneratedBoard(){
        prepareBoard();
        setGoldInBoard();
        placeWumpusOnBoard(2);
        int pitNumber = raandom.nextInt(5)+5;
        for(int i=0;i<pitNumber;i++){
            placePitsOnBoard();
        }
        print();
        return board;
    }
    public void prepareBoard(){
        for(int i=0;i<10;i++){
            for(int j=0;j<10;j++){
                board[i][j]=" ";
            }
        }
    }

    public void setGoldInBoard(){
        int ranx = raandom.nextInt(9)+1;
        int rany = raandom.nextInt(9)+1;
        board[ranx][rany] = "gold";
        setEnvironment(ranx,rany,"glitter");
    }

    public void placeWumpusOnBoard(int numberOfWumpusMonster){
        for(int i=0;i<numberOfWumpusMonster;i++){
            int ranx = raandom.nextInt(9)+1;
            int rany = raandom.nextInt(9)+1;
            if(board[ranx][rany].equals(" ")){
                board[ranx][rany]="wumpus";
                setEnvironment(ranx,rany,"stench");
            }else{
                i--;
            }
        }
    }

    public void placePitsOnBoard(){
        int ranx = raandom.nextInt(9)+1;
        int rany = raandom.nextInt(9+1);
        if(checkBlock(ranx,rany)){
            board[ranx][rany]="pit";
            setEnvironment(ranx,rany,"breeze");
        }
    }

    public boolean checkBlock(int i,int j){
        if(board[i][j].equals("wumpus")) return false;
        else if(board[i][j].equals("gold")) return false;
        else if(board[i][j].equals("pit")) return false;
        else return true;
    }

    public void setEnvironment(int x,int y,String env){
        if(x-1>=0 && board[x-1][y]!="pit" && board[x-1][y]!="wumpus" && board[x-1][y]!="gold"){
            placeEnvValue(x-1,y,env);
        }
        if(x+1<10 && board[x+1][y]!="pit" && board[x+1][y]!="wumpus" && board[x+1][y]!="gold"){
            placeEnvValue(x+1,y,env);
        }
        if(y-1>=0 && board[x][y-1]!="pit" && board[x][y-1]!="wumpus" && board[x][y-1]!="gold"){
            placeEnvValue(x,y-1,env);
        }
        if(y+1<10 && board[x][y+1]!="pit" && board[x][y+1]!="wumpus" && board[x][y+1]!="gold"){
            placeEnvValue(x,y+1,env);
        }
    }
    public void placeEnvValue(int x,int y,String env){
        if(board[x][y].equals(" ")){
            board[x][y]=env;
        }else{
            if(board[x][y].equals(env)) return;
            board[x][y]=board[x][y]+" "+env;
        }
    }

    public void print(){
        for(int i=0;i<10;i++){
            for(int j=0;j<10;j++){
                System.out.print(j+board[i][j]+"----");
            }
            System.out.println();
        }
    }
}
