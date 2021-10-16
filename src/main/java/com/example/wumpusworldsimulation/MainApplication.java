package com.example.wumpusworldsimulation;

import com.example.wumpusworldsimulation.Board.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
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

public class MainApplication extends Application {

    GridPane gp = new GridPane();
    Circle circle =new Circle();
    String world[][];

    Agent agent = new Agent();
    MyKnowledgeBase base = new MyKnowledgeBase();
    MyAi ai;
    Thread simulate;
    boolean simulation_started= false;
    boolean gameLoop=true;

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
            Image im = new Image("front_face.png",false);
            circle.setFill(new ImagePattern(im));
            circle.setRadius(15);
            GridPane.setRowIndex(circle,agent.getCurrentRow());
            GridPane.setColumnIndex(circle,agent.getCurrentCol());
            agent.setCurrentDirection("down");
            changePlayerPosition(0, 0);
            startAiSimulation();
        });

        topBar.setRight(btn);
        topBar.setLeft(label);
        topBar.setStyle("-fx-background-color:darkslategray;-fx-padding: 10px;\n" +
                "    -fx-font-size: 20px;-fx-color:aqua");
        root.setTop(topBar);

        BorderPane instructions = new BorderPane();
        Label textq = new Label("For Manual Play \nw => Up Move , s => Down Move , a => Left Move , d => Right Move ");
        label.setTextFill(Color.valueOf("black"));
        instructions.setStyle("-fx-padding: 8px;\n" +
                "    -fx-font-size: 15px;");
        instructions.setLeft(textq);

        BorderPane footer = new BorderPane();

        Label text = new Label("@Developed By: Radowan, Khalil, Shahriyar!");
        label.setTextFill(Color.valueOf("white"));

        footer.setLeft(text);
        footer.setStyle("-fx-padding: 10px;\n" +
                "    -fx-font-size: 10px;-fx-color:aqua");

        BorderPane bottom = new BorderPane();
        bottom.setTop(instructions);
        bottom.setBottom(footer);
        root.setBottom(bottom);



        //circle.setFill(Color.valueOf("Red"));
        agent.setCurrentDirection("down");
        Image im = new Image("front_face.png",false);
        circle.setFill(new ImagePattern(im));
        circle.setRadius(15);
        GridPane.setRowIndex(circle,agent.getCurrentRow());
        GridPane.setColumnIndex(circle,agent.getCurrentCol());
        gp.getChildren().add(circle);
        GridPane.setMargin(circle, new Insets(0,0,0,17));
        vbox.getChildren().add(gp);
        root.setLeft(vbox);
        stage.setTitle("Wumpus World!");
        Scene sc =new Scene(root, 706, 855);
                sc.setOnKeyPressed(new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event) {
                        switch (event.getCode()) {
                            case W:  {
                                System.out.println("up");
                                if(agent.getCurrentRow()>0){
                                    if(agent.getCurrentDirection().equals("up")){
                                        changePlayerPosition(agent.getCurrentRow()-1, agent.getCurrentCol());
                                    }else{
                                        Image im = new Image("back.png",false);
                                        circle.setFill(new ImagePattern(im));
                                        circle.setRadius(15);
                                        GridPane.setRowIndex(circle,agent.getCurrentRow());
                                        GridPane.setColumnIndex(circle,agent.getCurrentCol());
                                        agent.setCurrentDirection("up");
                                    }
                                    break;
                                }
                                break;
                            }
                            case S:  {
                                System.out.println("down");
                                if(agent.getCurrentRow()<9){
                                    if(agent.getCurrentDirection().equals("down")){
                                        changePlayerPosition(agent.getCurrentRow()+1, agent.getCurrentCol());
                                    }else{
                                        Image im = new Image("front_face.png",false);
                                        circle.setFill(new ImagePattern(im));
                                        circle.setRadius(15);
                                        GridPane.setRowIndex(circle,agent.getCurrentRow());
                                        GridPane.setColumnIndex(circle,agent.getCurrentCol());
                                        agent.setCurrentDirection("down");
                                    }
                                }
                                break;
                            }
                            case A:  {
                                System.out.println("left");
                                if(agent.getCurrentCol()>0){
                                    if(agent.getCurrentDirection().equals("left")){
                                        changePlayerPosition(agent.getCurrentRow(),agent.getCurrentCol()-1);
                                    }else{
                                        Image im = new Image("left_face.png",false);
                                        circle.setFill(new ImagePattern(im));
                                        circle.setRadius(15);
                                        GridPane.setRowIndex(circle,agent.getCurrentRow());
                                        GridPane.setColumnIndex(circle,agent.getCurrentCol());
                                        agent.setCurrentDirection("left");
                                    }
                                }
                                break;
                            }
                            case D: {
                                System.out.println("right");
                                if(agent.getCurrentCol()<9){
                                    if(agent.getCurrentDirection().equals("right")){
                                        changePlayerPosition(agent.getCurrentRow(), agent.getCurrentCol()+1);
                                    }else{
                                        Image im = new Image("right_face.png",false);
                                        circle.setFill(new ImagePattern(im));
                                        circle.setRadius(15);
                                        GridPane.setRowIndex(circle,agent.getCurrentRow());
                                        GridPane.setColumnIndex(circle,agent.getCurrentCol());
                                        agent.setCurrentDirection("right");
                                    }
                                }
                                break;
                            }
                            case F10: {
                                System.out.println("F10 pressed");
                                removeCover();
                            }
                        }
                    }
                });
        stage.setScene(sc);
        stage.show();
    }

    public void startAiSimulation(){
        simulation_started = true;
        ai = new MyAi(base,agent,10,world);
        simulate = new Thread(()->{
            while(gameLoop){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                // int dir = pickMove();
                String direction= ai.whereSHouldIgo();
                if(direction.equals("up")) changePlayerPosition( agent.getCurrentRow()-1, agent.getCurrentCol());
                else if(direction.equals("right")) changePlayerPosition( agent.getCurrentRow(),agent.getCurrentCol()+1);
                else if(direction.equals("down")) changePlayerPosition(agent.getCurrentRow()+1,agent.getCurrentCol());
                else if(direction.equals("left")) changePlayerPosition( agent.getCurrentRow(),agent.getCurrentCol()-1);
            }
        });
        simulate.start();
    }

    public void changePlayerPosition( int row ,int col)
    {
        agent.setCurrentCol(col);
        agent.setCurrentRow(row);
        agent.base.visited[row][col] = 1;
        GridPane.setRowIndex(circle, row);
        GridPane.setColumnIndex(circle, col);
        // agent.addKnowledgeFromPercept(world[y][x]);
        Rectangle c = (Rectangle) gp.lookup("#cover"+row+col);
        try {
            Platform.runLater(new Runnable(){
                @Override
                public void run() {
                    gp.getChildren().remove(c);
                }
            });
//            if(simulation_started){
//                simulate.join();
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(world[row][col].equals("wumpus")){
                Platform.runLater(new Runnable(){
                    @Override
                    public void run() {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.getButtonTypes().remove(ButtonType.OK);
                        alert.getButtonTypes().add(ButtonType.CANCEL);
                        alert.setTitle("Game Over");
                        alert.setHeaderText("WUMPUS!!!!!!");
                        alert.setContentText(String.format("Wumpus Has Attacked You"));
                        Optional<ButtonType> res = alert.showAndWait();

                        if(res.isPresent()) {
                            if(res.get().equals(ButtonType.CANCEL))
                                System.exit(0);
                        }
                    }
                });
                if(simulation_started){
                    try {
                        simulate.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

        }
        else if(world[row][col].equals("pit")){
            Platform.runLater(new Runnable(){
                @Override
                public void run() {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.getButtonTypes().remove(ButtonType.OK);
                    alert.getButtonTypes().add(ButtonType.CANCEL);
                    alert.setTitle("GameOver");
                    alert.setHeaderText("Pit!!!!!!");
                    alert.setContentText(String.format("You fell in a pit"));
                    Optional<ButtonType> res = alert.showAndWait();

                    if(res.isPresent()) {
                        if(res.get().equals(ButtonType.CANCEL))
                            System.exit(0);
                    }
                }
            });
            if(simulation_started){
                try {
                    simulate.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        else if(world[row][col].equals("gold")){
            Platform.runLater(new Runnable(){
                @Override
                public void run() {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.getButtonTypes().remove(ButtonType.OK);
                    alert.getButtonTypes().add(ButtonType.CANCEL);
                    alert.setTitle("VIctory");
                    alert.setHeaderText("Gold$$$$");
                    alert.setContentText(String.format("Congratulations, You won"));
                    Optional<ButtonType> res = alert.showAndWait();
                    if(res.isPresent()) {
                        if(res.get().equals(ButtonType.CANCEL))
                            System.exit(0);
                    }
                }
            });
            if(simulation_started){
                try {
                    simulate.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void removeCover(){
        for(int i=0;i<10;i++){
            for(int j=0;j<10;j++){
                if(i == 0 && j == 0) continue;
                else{
                    Rectangle c = (Rectangle) gp.lookup("#cover"+i+j);
                    gp.getChildren().remove(c);
                }
            }
        }
    }

    public String getMove(){
        if(agent.getCurrentCol() == 9){
            return "down";
        }else if(agent.getCurrentCol() == 0){
            return "right";
        } else return "right";
    }


    public static void main(String[] args) {
        launch();
    }
}