package util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class LoadFXMLFile {
    public static void loadOnTheCurrentPane(String filename, AnchorPane context) throws IOException {
        URL resource = LoadFXMLFile.class.getResource("../view/" + filename + ".fxml");
        Parent load = FXMLLoader.load(resource);
        context.getChildren().clear();
        context.getChildren().add(load);
        //new FadeIn(context).play();
    }

    public static void loadOn(String fileName) throws IOException {
        Parent load = FXMLLoader.load(LoadFXMLFile.class.getResource("../view/" + fileName + ".fxml"));
        Scene scene = new Scene(load);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public static void load(String filename, AnchorPane context) throws IOException {
        URL resource = LoadFXMLFile.class.getResource("../view/" + filename + ".fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) context.getScene().getWindow();
        window.setScene(new Scene(load));
    }
}
