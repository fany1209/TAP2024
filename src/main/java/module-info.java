module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;



    opens com.example.demo to javafx.fxml;
    opens com.example.demo.componentes to javafx.base;
    exports com.example.demo;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires mysql.connector.j;
    opens com.example.demo.modelos;
}