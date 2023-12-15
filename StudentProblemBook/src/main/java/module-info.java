module com.example.studentproblembook {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.example.studentproblembook to javafx.fxml;
    exports com.example.studentproblembook;
}