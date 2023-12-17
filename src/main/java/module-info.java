module ber.soundboard.berssoundboard {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.media;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;

    requires com.google.gson;

    opens ber.soundboard.berssoundboard to javafx.fxml;
    exports ber.soundboard.berssoundboard;
}