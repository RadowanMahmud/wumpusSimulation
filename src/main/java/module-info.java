module com.example.wumpusworldsimulation {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.wumpusworldsimulation to javafx.fxml;
    exports com.example.wumpusworldsimulation;
    exports com.example.wumpusworldsimulation.Board;
    opens com.example.wumpusworldsimulation.Board to javafx.fxml;
}