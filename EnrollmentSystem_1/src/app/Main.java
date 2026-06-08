package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage window) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/views/EnrollmentView.fxml"));


        Scene scene = new Scene(root, 780, 600);


        window.setTitle("Student Course Enrollment System");


        window.setScene(scene);
        window.setResizable(false);
        window.show();
    }

     
    public static void main(String[] args) {
        launch(args);
    }
}