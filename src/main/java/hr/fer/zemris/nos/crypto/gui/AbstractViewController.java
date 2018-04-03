package hr.fer.zemris.nos.crypto.gui;

import java.io.File;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Created by generalic on 08/05/17.
 * crypto
 */
public abstract class AbstractViewController {

    private Stage mainStage;

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    protected void showAlertDialog(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    protected void selectFile(String title, TextField textField) {
        FileChooser fileChooser = new FileChooser();

        String userDirectoryString = System.getProperty("user.dir");
        File userDirectory = new File(userDirectoryString);
        fileChooser.setInitialDirectory(userDirectory);

        fileChooser.setTitle("Select " + title + " File");
        fileChooser.getExtensionFilters().addAll(
            //new FileChooser.ExtensionFilter("Text Files", "*.txt"),
            new FileChooser.ExtensionFilter("All Files", "*.*"));
        File selectedFile = fileChooser.showOpenDialog(mainStage);
        if (selectedFile != null) {
            textField.setText(selectedFile.getAbsolutePath());
        }
    }
}
