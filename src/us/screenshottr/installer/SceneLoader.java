package us.screenshottr.installer;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class SceneLoader {
    
    private final int id;
    private Parent root;
    private Scene scene;
    Util util = new Util();
    
    SceneLoader(int id) {
        this.id = id;
        
        try {
            this.root = FXMLLoader.load(getClass().getResource("resources/" + id + ".fxml"));
            this.scene = new Scene(this.root);
        }
        catch (IOException ex) {
            Logger.getLogger(SceneLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    SceneLoader(String name) {
        this.id = 0;
        
        try {
            this.root = FXMLLoader.load(getClass().getResource("resources/" + name + ".fxml"));
            this.scene = new Scene(this.root);
        }
        catch (IOException ex) {
            Logger.getLogger(SceneLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public int getId() {
        return this.id;
    }
    
    public Scene getScene() {
        return scene;
    }
    
    public Parent getParent() {
        return this.root;
    }
}
