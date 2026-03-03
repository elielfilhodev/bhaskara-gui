module com.example.bhaskaragui {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.bhaskaragui to javafx.fxml;
    exports com.example.bhaskaragui;
}