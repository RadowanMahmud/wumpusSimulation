package com.example.wumpusworldsimulation;

import com.example.wumpusworldsimulation.Board.Agent;
import com.example.wumpusworldsimulation.Board.LogicClass;
import com.example.wumpusworldsimulation.Board.WumpusWorldGenerator;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.ImagePattern;
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
import java.util.concurrent.ThreadLocalRandom;

public class MainApplication extends Application {

    GridPane gp = new GridPane();
    Circle circle =new Circle();
    String world[][];
//    PropositionalLogicResolution propositionalLogicResolution = new PropositionalLogicResolution();
//    AgentPercept agentPercept = new AgentPercept();
    Agent agent = new Agent();
    LogicClass logic = new LogicClass(agent);

    @Override
    public void start(Stage stage) throws IOException {

        WumpusWorldGenerator generator= new WumpusWorldGenerator();
        world = generator.getGeneratedBoard();

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
                    if(in.size()==1){
                        output = (String) value.next();
                    }
                    else output = output + "\n" + value.next();
                }

                Text text = new Text(output);
                GridPane.setRowIndex(rec, row);
                GridPane.setColumnIndex(rec, col);
                Rectangle recsmall = new Rectangle();
                recsmall.setWidth(55);
                recsmall.setHeight(55);
                if(output.equals("wumpus")){
                    Image monster = new Image("monster.jpg",false);
                    recsmall.setFill(new ImagePattern(monster));
                    GridPane.setRowIndex(recsmall, row);
                    GridPane.setColumnIndex(recsmall, col);
                    GridPane.setMargin(recsmall, new Insets(0,0,0,5));
                    gp.getChildren().addAll( rec,recsmall);
                }
                else if(output.equals("gold")){
                    Image monster = new Image("gold.jpg",false);
                    recsmall.setFill(new ImagePattern(monster));
                    GridPane.setRowIndex(recsmall, row);
                    GridPane.setColumnIndex(recsmall, col);
                    GridPane.setMargin(recsmall, new Insets(0,0,0,5));
                    gp.getChildren().addAll( rec,recsmall);
                }
                else if(output.equals("pit")){
                    Image monster = new Image("pit.png",false);
                    recsmall.setFill(new ImagePattern(monster));
                    GridPane.setRowIndex(recsmall, row);
                    GridPane.setColumnIndex(recsmall, col);
                    GridPane.setMargin(recsmall, new Insets(0,0,0,5));
                    gp.getChildren().addAll( rec,recsmall);
                }
                else{
                    GridPane.setRowIndex(text, row);
                    GridPane.setColumnIndex(text, col);
                    GridPane.setMargin(text, new Insets(0,0,0,4));
                    text.setStyle("-fx-padding: 10px;\n" +
                            "    -fx-font-size: 14px;-fx-color:aqua");
                    text.setVisible(true);
                    gp.getChildren().addAll(rec, text);
                }
                Rectangle coverRec= new Rectangle();
                Image coverImage = new Image("grass.jpg",false);
                coverRec.setHeight(65);
                coverRec.setWidth(65);
                coverRec.setFill(new ImagePattern(coverImage));
                coverRec.setId("cover"+row+col);
                //System.out.println("cover"+row+col);
                GridPane.setRowIndex(coverRec, row);
                GridPane.setColumnIndex(coverRec, col);
                if(row == 0 && col ==0) continue;
                gp.getChildren().add(coverRec);
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
          // startSimulation();
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



        //circle.setFill(Color.valueOf("Red"));
        Image im = new Image("wumpus.png",false);
        circle.setFill(new ImagePattern(im));
        circle.setRadius(20);
        GridPane.setRowIndex(circle,agent.getCurrentRow());
        GridPane.setColumnIndex(circle,agent.getCurrentCol());
        gp.getChildren().add(circle);
        GridPane.setMargin(circle, new Insets(0,0,0,10));
        vbox.getChildren().add(gp);
        root.setLeft(vbox);
        stage.setTitle("Wumpus World!");
        Scene sc =new Scene(root, 706, 800);
                sc.setOnKeyPressed(new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event) {
                        switch (event.getCode()) {
                            case W:  {
                                System.out.println("up");
                                if(agent.getCurrentRow()>0){
                                    //code for image rotation
//                                    ImageView iv = new ImageView(im);
//                                    iv.setRotate(270);
//                                    SnapshotParameters params = new SnapshotParameters();
//                                    params.setFill(Color.TRANSPARENT);
//                                    Image rotatedImage = iv.snapshot(params, null);
//                                    circle.setFill(new ImagePattern(rotatedImage));
//                                    GridPane.setRowIndex(circle,agent.getCurrentRow());
//                                    GridPane.setColumnIndex(circle,agent.getCurrentCol());
                                    System.out.println("Up");
                                    if(agent.getCurrentRow()>0){
                                        changePlayerPosition(agent.getCurrentCol(),agent.getCurrentRow()-1);
                                    }
                                    break;
                                }
                                break;
                            }
                            case S:  {
                                System.out.println("down");
                                if(agent.getCurrentRow()<9){
                                    changePlayerPosition(agent.getCurrentCol(),agent.getCurrentRow()+1);
                                }
                                break;
                            }
                            case A:  {
                                System.out.println("left");
                                if(agent.getCurrentCol()>0){
                                    changePlayerPosition(agent.getCurrentCol()-1,agent.getCurrentRow());
                                }
                                break;
                            }
                            case D: {
                                System.out.println("right");
                                if(agent.getCurrentCol()<9){
                                    changePlayerPosition(agent.getCurrentCol()+1,agent.getCurrentRow());
                                }
                                break;
                            }
                        }
                    }
                });
        stage.setScene(sc);
        stage.show();
    }

//    private void startSimulation(){
//        new Thread(()->{ //use another thread so long process does not block gui
//            changePlayerPosition(agent.getCurrentCol(), agent.getCurrentRow());
//            while(true){
//                try {
//                    Thread.sleep(500);
//                } catch (InterruptedException ex) {
//                    ex.printStackTrace();
//                }
//                int dir = pickMove();
//                if(dir == 0) changePlayerPosition(agent.getCurrentCol(), agent.getCurrentRow()-1);
//                else if(dir == 1) changePlayerPosition(agent.getCurrentCol()+1, agent.getCurrentRow());
//                else if(dir == 2) changePlayerPosition(agent.getCurrentCol(), agent.getCurrentRow()+1);
//                else if(dir == 3) changePlayerPosition(agent.getCurrentCol()-1, agent.getCurrentRow());
//                agent.base.printKB();
//
//            }
//
//        }).start();
//    }
//

    public void changePlayerPosition( int x, int y) {
        agent.setCurrentCol(x);
        agent.setCurrentRow(y);
        agent.base.visited[y][x] = 1;
        GridPane.setRowIndex(circle, y);
        GridPane.setColumnIndex(circle, x);
        // agent.addKnowledgeFromPercept(world[y][x]);
        Rectangle c = (Rectangle) gp.lookup("#cover"+y+x);
        System.out.println(c);
        gp.getChildren().remove(c);
        if(world[y][x].equals("wumpus")){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.getButtonTypes().remove(ButtonType.OK);
            alert.getButtonTypes().add(ButtonType.CANCEL);
            alert.setTitle("Wumpus");
            alert.setContentText(String.format("Wumpus Has Attacked You"));
            Optional<ButtonType> res = alert.showAndWait();

            if(res.isPresent()) {
                if(res.get().equals(ButtonType.CANCEL))
                    System.exit(0);
            }
        }
        else if(world[y][x].equals("pit")){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.getButtonTypes().remove(ButtonType.OK);
            alert.getButtonTypes().add(ButtonType.CANCEL);
            alert.setTitle("Pit");
            alert.setContentText(String.format("You fell in a pit"));
            Optional<ButtonType> res = alert.showAndWait();

            if(res.isPresent()) {
                if(res.get().equals(ButtonType.CANCEL))
                    System.exit(0);
            }
        }
        else if(world[y][x].equals("gold")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.getButtonTypes().remove(ButtonType.OK);
            alert.getButtonTypes().add(ButtonType.CANCEL);
            alert.setTitle("GOLD$$$$$$$$$$$$$$$");
            alert.setContentText(String.format("Congratulations, You won"));
            Optional<ButtonType> res = alert.showAndWait();
            if(res.isPresent()) {
                if(res.get().equals(ButtonType.CANCEL))
                    System.exit(0);
            }
        }
    }


//    private int pickMove(){
//        int priority = -10;
//        int moveDir = ThreadLocalRandom.current().nextInt(4);
//
//        //0 for back,1 right,2 front , 3 left
//        if(agent.getCurrentRow() == 0 || agent.getCurrentRow()== 9) {
//            if(agent.getCurrentCol() == 0) {
//                moveDir=1;
//            }else moveDir=3;
//        }
//        if(agent.getCurrentCol() == 0 || agent.getCurrentCol()== 9) {
//            if(agent.getCurrentRow() == 0){
//                moveDir = 2;
//            }else moveDir = 0;
//        }
////this is my understanding so far
////this is my understanding so far
//
//        if(agent.getCurrentCol()-1>=0){
//            int p = getPriority(agent.getCurrentCol()-1, agent.getCurrentRow());
//            if(priority < p){
//                priority = p;
//                moveDir = 3;
//            }
//
//        }
//
//        if(agent.getCurrentCol()+1<10){
//            int p = getPriority(agent.getCurrentCol()+1, agent.getCurrentRow());
//            if(priority < p){
//                priority = p;
//                moveDir = 1;
//            }
//
//        }
//
//        if(agent.getCurrentRow()-1>=0){
//            int p = getPriority(agent.getCurrentCol(), agent.getCurrentRow()-1);
//            if(priority < p){
//                priority = p;
//                moveDir = 0;
//            }
//
//        }
//
//
//        if(agent.getCurrentRow()+1<10){
//            int p = getPriority(agent.getCurrentCol(), agent.getCurrentRow()+1);
//            if(priority < p){
//                priority = p;
//                moveDir = 2;
//            }
//
//        }
//
//        return moveDir;
//    }
////
//    private int getPriority(int x, int y) {
//
//        int p= agent.base.visited[y][x]==1 ? 1 : 2 ;
//        //check if visited
//        if(logic.getResolutionResult("B"+y+x)) {
//            System.out.println("Breeze at " + x + " " + y);
//            if (logic.getResolutionResult("~P" + x + y)) {
//                System.out.println("No Pit at " + x + " " + y);
//                return 10;
//
//            } else {
//                System.out.println("Pit at " + x + " " + y);
//            }
//        }
//        if(logic.getResolutionResult("S"+x+y)) {
//            System.out.println("Stench at " + x + " " + y);
//
//            if (logic.getResolutionResult("~W" + x + y)) {
//                return 5;
//            } else {
//                System.out.println("WUMPUS at " + x + " " + y);
//                if (logic.getResolutionResult("Gl" + x + y))
//                    if (logic.getResolutionResult("G" + x + y)) {
//                        System.out.println("GOLD at " + x + " " + y);
//                        return 100;
//                    } else
//                        System.out.println("No GOLD at " + x + " " + y);
//
//            }
//        }
//        if(logic.getResolutionResult("Gl"+x+y)) {
//            System.out.println("Glitter at " + x + " " + y);
//
//            if (logic.getResolutionResult("G" + x + y)) {
//                System.out.println("GOLD at " + x + " " + y);
//                return 100;
//            } else
//                System.out.println("No GOLD at " + x + " " + y);
//        }
//
//
//
//
//        return 3;
//    }

    public static void main(String[] args) {
        launch();
    }
}