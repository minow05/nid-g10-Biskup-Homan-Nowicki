module com.example.niduc {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.niduc to javafx.fxml;
    exports com.niduc;
}