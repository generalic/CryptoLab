package hr.fer.zemris.nos.crypto.gui.envelope;

import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by generalic on 07/05/17.
 * crypto
 */
public class DigitalEnvelopeView extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Two ways to do it:
        // If you pass a resource path that doesn't start with a / to Class.getResource(), the
        // class loader looks for the resource in the package of the class.
        // Not at the root. Your code should be
        //URL url = getClass().getResource("/digital_signature_view.fxml"); // or
        URL url = getClass().getClassLoader().getResource("digital_envelope_view.fxml");

        FXMLLoader loader = new FXMLLoader(url);
        loader.setClassLoader(getClass().getClassLoader());
        Parent root = loader.load();

        DigitalEnvelopeViewController controller = loader.getController();
        controller.setMainStage(primaryStage);

        primaryStage.setTitle("Digital Envelope");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
