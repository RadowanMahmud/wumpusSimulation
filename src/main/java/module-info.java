module com.example.wumpusworldsimulation {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.wumpusworldsimulation to javafx.fxml;
    exports com.example.wumpusworldsimulation;
}