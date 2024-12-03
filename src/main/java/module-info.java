module org.example.cis3270projectjavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;

    exports edu.gsu.gui;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens org.example.cis3270projectjavafx to javafx.fxml;
    exports org.example.cis3270projectjavafx;
    exports edu.gsu.db to javafx.graphics;
}