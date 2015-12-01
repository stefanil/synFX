package org.devel.synfx.beads;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by stefan.illgen on 01.12.2015.
 */
public class Application extends javafx.application.Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(FXMLLoader.load(Application.class.getResource("SynFX.fxml")));
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setWidth(800);
        primaryStage.setHeight(600);
    }
}
