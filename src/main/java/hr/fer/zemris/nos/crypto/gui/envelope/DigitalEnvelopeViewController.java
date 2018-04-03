package hr.fer.zemris.nos.crypto.gui.envelope;

import hr.fer.zemris.nos.crypto.asymetric.RSAPrivateKeyProvider;
import hr.fer.zemris.nos.crypto.asymetric.RSAPublicKeyProvider;
import hr.fer.zemris.nos.crypto.envelope.DigitalEnvelopeGenerator;
import hr.fer.zemris.nos.crypto.envelope.DigitalEnvelopeOpener;
import hr.fer.zemris.nos.crypto.gui.AbstractViewController;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.PrivateKey;
import java.security.PublicKey;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class DigitalEnvelopeViewController extends AbstractViewController {

    @FXML
    private Button selectInputFileButton;
    @FXML
    private Button viewInputFileButton;
    @FXML
    private Button selectReceiverPublicKeyButton;
    @FXML
    private Button viewReceiverPublicKeyButton;
    @FXML
    private Button selectDigitalEnvelopeFileButton;
    @FXML
    private Button viewDigitalEnvelopeFileButton;
    @FXML
    private Button generateButton;
    @FXML
    private Button selectReceiverPrivateKeyButton;
    @FXML
    private Button viewReceiverPrivateKeyButton;
    @FXML
    private Button selectOutputFileButton;
    @FXML
    private Button viewOutputFileButton;
    @FXML
    private Button readButton;

    @FXML
    private TextField inputFileTextField;
    @FXML
    private TextField receiverPublicKeyTextField;
    @FXML
    private TextField digitalEnvelopeFileTextField;
    @FXML
    private TextField receiverPrivateKeyTextField;
    @FXML
    private TextField outputFileTextField;

    @FXML
    public void initialize() {
        generateButton.disableProperty().bind(
            inputFileTextField.textProperty().isEmpty()
                .or(receiverPublicKeyTextField.textProperty().isEmpty())
                .or(digitalEnvelopeFileTextField.textProperty().isEmpty())
        );

        readButton.disableProperty().bind(
            receiverPrivateKeyTextField.textProperty().isEmpty()
                .or(outputFileTextField.textProperty().isEmpty())
        );

        viewInputFileButton.disableProperty().bind(inputFileTextField.textProperty().isEmpty());
        viewReceiverPublicKeyButton.disableProperty()
            .bind(receiverPublicKeyTextField.textProperty().isEmpty());
        viewDigitalEnvelopeFileButton.disableProperty()
            .bind(digitalEnvelopeFileTextField.textProperty().isEmpty());
        viewReceiverPrivateKeyButton.disableProperty()
            .bind(receiverPrivateKeyTextField.textProperty().isEmpty());
        viewOutputFileButton.disableProperty().bind(outputFileTextField.textProperty().isEmpty());
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
    private void selectReceiverPublicKey() {
        selectFile("Receiver Public Key", receiverPublicKeyTextField);
    }

    @FXML
    private void viewReceiverPublicKey() throws IOException {
        Desktop.getDesktop().open(new File(receiverPublicKeyTextField.getText()));
    }

    @FXML
    private void selectDigitalEnvelopeFile() {
        selectFile("Digital Envelope", digitalEnvelopeFileTextField);
    }

    @FXML
    private void viewDigitalEnvelopeFile() throws IOException {
        Desktop.getDesktop().open(new File(digitalEnvelopeFileTextField.getText()));
    }

    @FXML
    private void generateDigitalEnvelope() throws IOException {
        RSAPublicKeyProvider publicKeyProviderA =
            new RSAPublicKeyProvider(receiverPublicKeyTextField.getText());

        PublicKey publicKey = publicKeyProviderA.getPublicKey();

        Path inputFile = Paths.get(inputFileTextField.getText());
        Path digitalEnvelopeFile = Paths.get(digitalEnvelopeFileTextField.getText());

        DigitalEnvelopeGenerator digitalEnvelopeGenerator =
            new DigitalEnvelopeGenerator(digitalEnvelopeFile);
        digitalEnvelopeGenerator.create(inputFile, publicKey);

        showAlertDialog("Generate Digital Envelope", "Digital Envelope generated!");
    }

    @FXML
    private void selectReceiverPrivateKey() {
        selectFile("Sender Private Key", receiverPrivateKeyTextField);
    }

    @FXML
    private void viewReceiverPrivateKey() throws IOException {
        Desktop.getDesktop().open(new File(receiverPrivateKeyTextField.getText()));
    }

    @FXML
    private void selectOutputFile() {
        selectFile("Output File", outputFileTextField);
    }

    @FXML
    private void viewOutputFileButton() throws IOException {
        Desktop.getDesktop().open(new File(outputFileTextField.getText()));
    }

    @FXML
    private void openDigitalEnvelope() throws IOException {
        RSAPrivateKeyProvider privateKeyProvider =
            new RSAPrivateKeyProvider(receiverPrivateKeyTextField.getText());
        PrivateKey privateKey = privateKeyProvider.getPrivateKey();

        Path outputFile = Paths.get(outputFileTextField.getText());
        Path digitalEnvelopeFile = Paths.get(digitalEnvelopeFileTextField.getText());

        DigitalEnvelopeOpener digitalEnvelopeOpener =
            new DigitalEnvelopeOpener(digitalEnvelopeFile);

        String data = digitalEnvelopeOpener.open(privateKey);
        Files.write(outputFile, data.getBytes(StandardCharsets.UTF_8));

        showAlertDialog("Open Digital Envelope", "Digital Envelope data extracted!");
    }
}
