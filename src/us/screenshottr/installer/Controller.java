package us.screenshottr.installer;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import us.screenshottr.installer.Util.InstallType;

public class Controller implements Initializable {

    @FXML
    RadioButton encrypted;

    @FXML
    RadioButton unencrypted;

    @FXML
    TextArea console;

    @FXML
    private void onCancelEvent() {
        System.exit(0);
    }

    @FXML
    private void onNextEvent() {
        Scene s = Main.stage.getScene();
        SceneLoader sceneLoader = Main.util.getSceneLoader(s);
        int newSceneId = sceneLoader.getId() + 1;

        SceneLoader scene = new SceneLoader(newSceneId);
        Main.util.scenes.put(scene.getScene(), scene);
        Main.stage.setScene(scene.getScene());
    }

    @FXML
    private void onBackEvent() {
        Scene s = Main.stage.getScene();
        SceneLoader sceneLoader = Main.util.getSceneLoader(s);
        int newSceneId = sceneLoader.getId() - 1;
        
        SceneLoader scene = new SceneLoader(newSceneId);
        Main.util.scenes.put(scene.getScene(), scene);
        Main.stage.setScene(scene.getScene());
    }

    @FXML
    private void openTerms() {
        try {
            Desktop.getDesktop().browse(new URI("https://screenshottr.us/#TOU"));
        }
        catch (IOException | URISyntaxException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void onUnencryptedClick() {
        if (encrypted.isSelected()) {
            encrypted.setSelected(false);
        }
    }

    @FXML
    private void onEncryptedClick() {
        if (unencrypted.isSelected()) {
            unencrypted.setSelected(false);
        }
    }

    @FXML
    private void install() {
        if (encrypted.isSelected()) {
            Main.util.installType = InstallType.ENCRYPTED;
        }
        else if (unencrypted.isSelected()) {
            Main.util.installType = InstallType.UNENCRYPTED;
        }

        System.out.println("Install Type: " + Main.util.installType);

        SceneLoader installScene = new SceneLoader("install");
        Main.util.scenes.put(installScene.getScene(), installScene);
        Main.stage.setScene(installScene.getScene());

        String download;
        File install = new File(new File(System.getenv("APPDATA")).getParentFile() + "/Local/ScreenShottr/");

        if (Main.util.installType == InstallType.ENCRYPTED) {
            download = "https://screenshottr.us/downloads/ScreenShottrEncrypted.exe";
        }
        else {
            download = "https://screenshottr.us/downloads/ScreenShottr.exe";
        }

        if (!install.exists()) {
            install.mkdir();
        }

        try {
            //console.appendText("Downloading ScreenShottr.");
            Main.util.download(download, install.getAbsolutePath());
            Main.util.pinApplication();
        }
        catch (IOException ex) {

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
}
