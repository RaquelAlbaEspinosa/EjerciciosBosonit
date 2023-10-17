module com.example.lotrfx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.lotrfx to javafx.fxml;
    exports com.example.lotrfx;

    exports com.example.lotrfx.views;
}