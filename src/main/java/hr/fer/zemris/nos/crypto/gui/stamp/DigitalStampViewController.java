package hr.fer.zemris.nos.crypto.gui.stamp;

import hr.fer.zemris.nos.crypto.asymetric.RSAPrivateKeyProvider;
import hr.fer.zemris.nos.crypto.asymetric.RSAPublicKeyProvider;
import hr.fer.zemris.nos.crypto.envelope.DigitalEnvelopeGenerator;
import hr.fer.zemris.nos.crypto.envelope.DigitalEnvelopeOpener;
import hr.fer.zemris.nos.crypto.gui.AbstractViewController;
import hr.fer.zemris.nos.crypto.signature.DigitalSignatureChecker;
import hr.fer.zemris.nos.crypto.signature.DigitalSignatureGenerator;
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

public class DigitalStampViewController extends AbstractViewController {

    @FXML
    private TextField inputFileTextField;
    @FXML
    private TextField receiverPublicKeyTextField;
    @FXML
    private TextField digitalEnvelopeFileTextField;
    @FXML
    private TextField senderPrivateKeyTextField;
    @FXML
    private TextField digitalSignatureFileTextField;
    @FXML
    private TextField receiverPrivateKeyTextField;
    @FXML
    private TextField outputFileTextField;
    @FXML
    private TextField senderPublicKeyTextField;

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
    private Button selectReceiverPrivateKeyButton;
    @FXML
    private Button viewReceiverPrivateKeyButton;
    @FXML
    private Button selectOutputFileButton;
    @FXML
    private Button viewOutputFileButton;
    @FXML
    private Button selectSenderPublicKeyButton;
    @FXML
    private Button viewSenderPublicKeyButton;
    @FXML
    private Button openButton;

    @FXML
    public void initialize() {
        generateButton.disableProperty().bind(
            inputFileTextField.textProperty().isEmpty()
                .or(receiverPublicKeyTextField.textProperty().isEmpty())
                .or(senderPrivateKeyTextField.textProperty().isEmpty())
                .or(digitalEnvelopeFileTextField.textProperty().isEmpty())
                .or(digitalSignatureFileTextField.textProperty().isEmpty())
        );

        openButton.disableProperty().bind(
            senderPublicKeyTextField.textProperty().isEmpty()
                .or(receiverPrivateKeyTextField.textProperty().isEmpty())
                .or(senderPrivateKeyTextField.textProperty().isEmpty())
                .or(outputFileTextField.textProperty().isEmpty())
        );

        viewInputFileButton.disableProperty().bind(inputFileTextField.textProperty().isEmpty());
        viewReceiverPublicKeyButton.disableProperty()
            .bind(receiverPublicKeyTextField.textProperty().isEmpty());
        viewDigitalEnvelopeFileButton.disableProperty()
            .bind(digitalEnvelopeFileTextField.textProperty().isEmpty());
        viewSenderPrivateKeyButton.disableProperty()
            .bind(senderPrivateKeyTextField.textProperty().isEmpty());
        viewDigitalSignatureFileButton.disableProperty()
            .bind(digitalSignatureFileTextField.textProperty().isEmpty());
        viewReceiverPrivateKeyButton.disableProperty()
            .bind(receiverPrivateKeyTextField.textProperty().isEmpty());
        viewOutputFileButton.disableProperty().bind(outputFileTextField.textProperty().isEmpty());
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
    private void generateDigitalStamp() throws IOException {
        RSAPublicKeyProvider publicKeyProviderB =
            new RSAPublicKeyProvider(receiverPublicKeyTextField.getText());
        PublicKey publicKeyB = publicKeyProviderB.getPublicKey();

        RSAPrivateKeyProvider privateKeyProviderA =
            new RSAPrivateKeyProvider(senderPrivateKeyTextField.getText());
        PrivateKey privateKeyA = privateKeyProviderA.getPrivateKey();

        Path inputFile = Paths.get(inputFileTextField.getText());
        Path digitalEnvelopeFile = Paths.get(digitalEnvelopeFileTextField.getText());
        Path digitalSignatureFile = Paths.get(digitalSignatureFileTextField.getText());

        // generate digital envelope
        DigitalEnvelopeGenerator digitalEnvelopeGenerator =
            new DigitalEnvelopeGenerator(digitalEnvelopeFile);
        digitalEnvelopeGenerator.create(inputFile, publicKeyB);

        // sign digital envelope with digital signature = digital stamp
        DigitalSignatureGenerator digitalSignatureGenerator =
            new DigitalSignatureGenerator(digitalSignatureFile);
        digitalSignatureGenerator.create(digitalEnvelopeFile, privateKeyA);

        showAlertDialog("Generate Digital Stamp", "Digital Stamp generated!");
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
    private void selectSenderPublicKey() {
        selectFile("Sender Public Key", senderPublicKeyTextField);
    }

    @FXML
    private void viewSenderPublicKey() throws IOException {
        Desktop.getDesktop().open(new File(senderPublicKeyTextField.getText()));
    }

    @FXML
    private void openDigitalStamp() throws IOException {
        RSAPublicKeyProvider publicKeyProviderA =
            new RSAPublicKeyProvider(senderPublicKeyTextField.getText());
        PublicKey publicKeyA = publicKeyProviderA.getPublicKey();

        RSAPrivateKeyProvider privateKeyProviderB =
            new RSAPrivateKeyProvider(receiverPrivateKeyTextField.getText());
        PrivateKey privateKeyB = privateKeyProviderB.getPrivateKey();

        Path outputFile = Paths.get(outputFileTextField.getText());
        Path digitalSignatureFile = Paths.get(digitalSignatureFileTextField.getText());

        DigitalSignatureChecker digitalSignatureChecker =
            new DigitalSignatureChecker(digitalSignatureFile);

        // check digital stamp
        boolean correct = digitalSignatureChecker.check(publicKeyA);

        if (correct) {
            Path digitalEnvelopeFile = Paths.get(digitalSignatureChecker.getFileName());

            // open digital stamp
            DigitalEnvelopeOpener digitalEnvelopeOpener =
                new DigitalEnvelopeOpener(digitalEnvelopeFile);

            String data = digitalEnvelopeOpener.open(privateKeyB);
            Files.write(outputFile, data.getBytes(StandardCharsets.UTF_8));

            //viewOutputFileButton.setDisable(false);
        }
        showAlertDialog("Open Digital Stamp", "Digital Stamp valid: " + correct + "!");
    }
}
