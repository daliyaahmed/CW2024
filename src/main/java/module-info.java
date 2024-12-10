module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.media;

    opens com.example.demo.controller to javafx.fxml;
    exports com.example.demo.controller;
    opens com.example.demo.actors to javafx.fxml;
    opens com.example.demo.projectiles to javafx.fxml;
    opens com.example.demo.ui to javafx.fxml;
    opens com.example.demo.levels to javafx.fxml;
    opens com.example.demo.levels.banners to javafx.fxml;
    opens com.example.demo.levels.views to javafx.fxml;
    opens com.example.demo.actors.l4enemies to javafx.fxml;
    opens com.example.demo.actors.l3enemies to javafx.fxml;
    opens com.example.demo.physics to javafx.fxml;
    opens com.example.demo.menus to javafx.fxml;
}