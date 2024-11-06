module com.example.niduc {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.niduc to javafx.fxml;
    exports com.niduc;
}