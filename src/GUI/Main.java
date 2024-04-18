package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        Application.launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/View/SlideshowView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setTitle("Image viewer");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}