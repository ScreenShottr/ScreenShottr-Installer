package us.screenshottr.installer;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
    
    public static Stage stage;
    public static Util util = new Util();
    
    @Override
    public void start(Stage st) throws Exception {
        stage = st;       
        
        stage.getIcons().add(new Image(getClass().getResourceAsStream("resources/camera.png")));
        
        SceneLoader mainScene = new SceneLoader(0);
        util.scenes.put(mainScene.getScene(), mainScene);
        
        stage.setScene(mainScene.getParent().getScene());
        stage.setResizable(false);
        stage.setTitle("ScreenShottr Installer");
        stage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
