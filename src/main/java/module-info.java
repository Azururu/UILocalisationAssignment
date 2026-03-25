module com.myapp.uilocalisationassignment {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.myapp.uilocalisationassignment to javafx.fxml;
    exports com.myapp.uilocalisationassignment;
}