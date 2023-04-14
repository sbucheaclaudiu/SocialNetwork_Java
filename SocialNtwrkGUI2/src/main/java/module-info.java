module com.example.socialntwrkgui {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.example.socialntwrkgui to javafx.fxml;
    exports com.example.socialntwrkgui;
    exports com.example.socialntwrkgui.controller;
    opens com.example.socialntwrkgui.controller to javafx.fxml;
    exports com.example.socialntwrkgui.domain;
}