package hr.fer.zemris.nos.crypto.gui.signature;

import hr.fer.zemris.nos.crypto.asymetric.RSAPrivateKeyProvider;
import hr.fer.zemris.nos.crypto.asymetric.RSAPublicKeyProvider;
import hr.fer.zemris.nos.crypto.gui.AbstractViewController;
import hr.fer.zemris.nos.crypto.signature.DigitalSignatureChecker;
import hr.fer.zemris.nos.crypto.signature.DigitalSignatureGenerator;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.PrivateKey;
import java.security.PublicKey;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class DigitalSignatureViewController extends AbstractViewController {

    @FXML
    private Button selectInputFileButton;
    @FXML
    private Button viewInputFileButton;
    @FXML
    private Button selectSenderPrivateKeyButton;
    @FXML
    private Button viewSenderPrivateKeyButton;
    @FXML
    private Button selectDigitalSignatureFileButton;
    @FXML
    private Button viewDigitalSignatureFileButton;
    @FXML
    private Button generateButton;
    @FXML
    private Button selectSenderPublicKeyButton;
    @FXML
    private Button viewSenderPublicKeyButton;
    @FXML
    private Button checkButton;

    @FXML
    private TextField inputFileTextField;
    @FXML
    private TextField senderPrivateKeyTextField;
    @FXML
    private TextField digitalSignatureFileTextField;
    @FXML
    private TextField senderPublicKeyTextField;

    @FXML
    public void initialize() {
        generateButton.disableProperty().bind(
            inputFileTextField.textProperty().isEmpty()
                .or(senderPrivateKeyTextField.textProperty().isEmpty())
                .or(digitalSignatureFileTextField.textProperty().isEmpty())
        );

        checkButton.disableProperty().bind(
            senderPublicKeyTextField.textProperty().isEmpty()
        );

        viewInputFileButton.disableProperty().bind(inputFileTextField.textProperty().isEmpty());
        viewSenderPrivateKeyButton.disableProperty()
            .bind(senderPrivateKeyTextField.textProperty().isEmpty());
        viewDigitalSignatureFileButton.disableProperty()
            .bind(digitalSignatureFileTextField.textProperty().isEmpty());
        viewSenderPublicKeyButton.disableProperty()
            .bind(senderPublicKeyTextField.textProperty().isEmpty());
    }

    @FXML
    private void selectInputFile() {
        selectFile("Input File", inputFileTextField);
    }

    @FXML
    private void viewInputFile() throws IOException {
        Desktop.getDesktop().open(new File(inputFileTextField.getText()));
    }

    @FXML
    private void selectSenderPrivateKey() {
        selectFile("Sender Private Key", senderPrivateKeyTextField);
    }

    @FXML
    private void viewSenderPrivateKey() throws IOException {
        Desktop.getDesktop().open(new File(senderPrivateKeyTextField.getText()));
    }

    @FXML
    private void selectDigitalSignatureFile() {
        selectFile("Digital Signature", digitalSignatureFileTextField);
    }

    @FXML
    private void viewDigitalSignatureFile() throws IOException {
        Desktop.getDesktop().open(new File(digitalSignatureFileTextField.getText()));
    }

    @FXML
    private void generateDigitalSignature() throws IOException {
        RSAPrivateKeyProvider privateKeyProviderA =
            new RSAPrivateKeyProvider(senderPrivateKeyTextField.getText());
        PrivateKey privateKey = privateKeyProviderA.getPrivateKey();

        Path inputFile = Paths.get(inputFileTextField.getText());
        Path digitalSignatureFile = Paths.get(digitalSignatureFileTextField.getText());

        DigitalSignatureGenerator digitalSignatureGenerator =
            new DigitalSignatureGenerator(digitalSignatureFile);
        digitalSignatureGenerator.create(inputFile, privateKey);

        showAlertDialog("Generate Digital Signature", "Digital Signature generated!");
    }

    @FXML
    private void selectSenderPublicKey() {
        selectFile("Sender Public Key", senderPublicKeyTextField);
    }

    @FXML
    private void viewSenderPublicKey() throws IOException {
        Desktop.getDesktop().open(new File(senderPublicKeyTextField.getText()));
    }

    @FXML
    private void checkDigitalSignature() throws IOException {
        RSAPublicKeyProvider publicKeyProvider =
            new RSAPublicKeyProvider(senderPublicKeyTextField.getText());
        PublicKey publicKey = publicKeyProvider.getPublicKey();

        Path digitalSignatureFile = Paths.get(digitalSignatureFileTextField.getText());

        DigitalSignatureChecker digitalSignatureChecker =
            new DigitalSignatureChecker(digitalSignatureFile);

        boolean correct = digitalSignatureChecker.check(publicKey);
        showAlertDialog("Check Digital Signature", "Digital Signature valid: " + correct + "!");
    }
}
