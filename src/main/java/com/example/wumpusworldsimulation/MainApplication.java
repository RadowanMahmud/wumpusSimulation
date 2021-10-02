package com.example.wumpusworldsimulation;

import com.example.wumpusworldsimulation.Board.Agent;
import com.example.wumpusworldsimulation.Board.WumpusWorldGenerator;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.*;

public class MainApplication extends Application {

    GridPane gp = new GridPane();
    Circle circle =new Circle();
    String world[][];
//    PropositionalLogicResolution propositionalLogicResolution = new PropositionalLogicResolution();
//    AgentPercept agentPercept = new AgentPercept();
    Agent agent = new Agent();


    @Override
    public void start(Stage stage) throws IOException {

        Random rand = new Random();
        //generating my world
        WumpusWorldGenerator generator= new WumpusWorldGenerator();
        world = generator.getGeneratedBoard();
        Map<String, Integer> list = new HashMap<String, Integer>();
        list.put("", 0);
        list.put("wumpus", 1);
        list.put("stench", 2);
        list.put("pit", 3);
        list.put("breeze", 4);
        list.put("gold", 5);
        list.put("glitter", 6);

        VBox vbox = new VBox();

        gp.setStyle("-fx-padding: 15 15 15 15");
        gp.setVgap(2.5);
        gp.setHgap(2.5);


        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {


                Rectangle rec = new Rectangle();
                rec.setWidth(65);
                rec.setHeight(65);
                rec.setFill(Color.TAN);
                String[] input = world[row][col].split(" ");
                Set<String> in= new HashSet<String>();
                for (int i = 0; i < input.length; i++) {
                    in.add(input[i]);
                }
                String output = "";

                Iterator value = in.iterator();
                while (value.hasNext()) {
                    output = output + "\n" + value.next();
                }

                Text text = new Text(output);
                GridPane.setRowIndex(rec, row);
                GridPane.setColumnIndex(rec, col);
                GridPane.setRowIndex(text, row);
                GridPane.setColumnIndex(text, col);
                GridPane.setMargin(text, new Insets(0,0,0,4));
                text.setStyle("-fx-padding: 10px;\n" +
                        "    -fx-font-size: 14px;-fx-color:aqua");
                text.setVisible(true);

                gp.getChildren().addAll(rec, text);
            }
        }

        BorderPane root = new BorderPane();
        BorderPane topBar = new BorderPane();

        Label label = new Label("Wumpus World Simulation!");
        label.setTextFill(Color.valueOf("white"));

        Button btn = new Button("Simulate");
        btn.setOnAction(e -> {
            changePlayerPosition(0, 0);
            agent.base.printKB();
//
//            startSimulation();
        });

        topBar.setRight(btn);
        topBar.setLeft(label);
        topBar.setStyle("-fx-background-color:darkslategray;-fx-padding: 10px;\n" +
                "    -fx-font-size: 20px;-fx-color:aqua");
        root.setTop(topBar);

        BorderPane footer = new BorderPane();

        Label text = new Label("@Developed By: Radowan, Khalil!");
        label.setTextFill(Color.valueOf("white"));

        footer.setLeft(text);
        footer.setStyle("-fx-padding: 10px;\n" +
                "    -fx-font-size: 10px;-fx-color:aqua");
        root.setBottom(footer);



        circle.setFill(Color.valueOf("Red"));
        circle.setRadius(12);
        circle.setUserData("Player");
        GridPane.setRowIndex(circle,agent.getCurrentRow());
        GridPane.setColumnIndex(circle,agent.getCurrentCol());
        gp.getChildren().add(circle);
        GridPane.setMargin(circle, new Insets(0,0,0,20));
        vbox.getChildren().add(gp);
        root.setLeft(vbox);
        stage.setTitle("Wumpus World!");
        stage.setScene(new Scene(root, 706, 800));
        stage.show();
    }

    private void startSimulation(){
        new Thread(()->{ //use another thread so long process does not block gui
            changePlayerPosition(agent.getCurrentCol(), agent.getCurrentRow());
            while(true){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                int dir = pickMove();
                if(dir == 0) changePlayerPosition(agent.getCurrentCol(), agent.getCurrentRow()-1);
                else if(dir == 1) changePlayerPosition(agent.getCurrentCol()+1, agent.getCurrentRow());
                else if(dir == 2) changePlayerPosition(agent.getCurrentCol(), agent.getCurrentRow()+1);
                else if(dir == 3) changePlayerPosition(agent.getCurrentCol()-1, agent.getCurrentRow());
                agent.base.printKB();

            }

        }).start();
    }


    private void changePlayerPosition( int x, int y) {
        agent.setCurrentCol(x);
        agent.setCurrentRow(y);
        agent.base.visited[y][x] = true;
        GridPane.setRowIndex(circle, y);
        GridPane.setColumnIndex(circle, x);
        agent.addKnowledgeFromPercept(world[y][x]);

    }


    private int pickMove(){
        int priority = -10;
        int moveDir = 2;

//        if(agentPercept.getCurrCol() == 0 || agentPercept.getCurrCol()== 9) moveDir = 1;
        if(agent.getCurrentRow() == 0 || agent.getCurrentRow()== 9) moveDir = 1;
        if(agent.getCurrentCol() == 0 || agent.getCurrentCol()== 9) moveDir = 2;

        if(world[agent.getCurrentCol()][agent.getCurrentRow()].equals("")) return moveDir;



        if(agent.getCurrentCol()-1>=0){
            int p = getPriority(agent.getCurrentCol()-1, agent.getCurrentRow());
            if(priority < p){
                priority = p;
                moveDir = 3;
            }

        }

        if(agent.getCurrentCol()+1<10){
            int p = getPriority(agent.getCurrentCol()+1, agent.getCurrentRow());
            if(priority < p){
                priority = p;
                moveDir = 1;
            }

        }

        if(agent.getCurrentRow()-1>=0){
            int p = getPriority(agent.getCurrentCol(), agent.getCurrentRow()-1);
            if(priority < p){
                priority = p;
                moveDir = 0;
            }

        }


        if(agent.getCurrentRow()+1<10){
            int p = getPriority(agent.getCurrentCol(), agent.getCurrentRow()+1);
            if(priority < p){
                priority = p;
                moveDir = 2;
            }

        }

        return moveDir;
    }
//
    private int getPriority(int x, int y) {

        //check if visited
        if(agent.base.visited[y][x]) return 1;
//        if(propositionalLogicResolution.getResolutionResult("B"+y+x)) {
//            System.out.println("Breeze at " + x + " " + y);
//            if (propositionalLogicResolution.getResolutionResult("~P" + x + y)) {
//                System.out.println("No Pit at " + x + " " + y);
//                return 10;
//
//            } else {
//                System.out.println("Pit at " + x + " " + y);
//            }
//        }
//        if(propositionalLogicResolution.getResolutionResult("S"+x+y)) {
//            System.out.println("Stench at " + x + " " + y);
//
//            if (propositionalLogicResolution.getResolutionResult("~W" + x + y)) {
//                return 5;
//            } else {
//                System.out.println("WUMPUS at " + x + " " + y);
//                if (propositionalLogicResolution.getResolutionResult("Gl" + x + y))
//                    if (propositionalLogicResolution.getResolutionResult("G" + x + y)) {
//                        System.out.println("GOLD at " + x + " " + y);
//                        return 100;
//                    } else
//                        System.out.println("No GOLD at " + x + " " + y);
//
//            }
//        }
//        if(propositionalLogicResolution.getResolutionResult("Gl"+x+y)) {
//            System.out.println("Glitter at " + x + " " + y);
//
//            if (propositionalLogicResolution.getResolutionResult("G" + x + y)) {
//                System.out.println("GOLD at " + x + " " + y);
//                return 100;
//            } else
//                System.out.println("No GOLD at " + x + " " + y);
//        }




        return 3;
    }

    public static void main(String[] args) {
        launch();
    }
}