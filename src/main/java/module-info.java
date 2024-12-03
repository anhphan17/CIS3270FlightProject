module org.example.cis3270projectjavafx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens org.example.cis3270projectjavafx to javafx.fxml;
    exports org.example.cis3270projectjavafx;
}