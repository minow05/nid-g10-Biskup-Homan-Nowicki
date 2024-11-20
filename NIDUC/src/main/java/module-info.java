module com.example.niduc {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.niduc to javafx.fxml;
    exports com.niduc;
    exports com.niduc.votingalgorithms;
    opens com.niduc.votingalgorithms to javafx.fxml;
    exports com.niduc.errormodels;
    opens com.niduc.errormodels to javafx.fxml;
    exports com.niduc.sensors;
    opens com.niduc.sensors to javafx.fxml;
}