package us.screenshottr.installer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Scene;
import mslinks.ShellLink;

public class Util {
    
    public enum InstallType {
        ENCRYPTED, UNENCRYPTED;
    }
    
    public InstallType installType;

    public Map<Scene, SceneLoader> scenes = new HashMap<>();

    public SceneLoader getSceneLoader(Scene scene) {
        return scenes.get(scene);
    }

    public boolean isFileEmpty(File f) {
        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader(f));
            if (br.readLine() == null) {
                return true;
            }
        }
        catch (FileNotFoundException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {
            try {
                br.close();
            }
            catch (IOException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return false;
    }

    public void pinApplication() {
        File taskbar = new File(System.getenv("APPDATA") + "/Microsoft/Internet Explorer/Quick Launch/User Pinned/TaskBar");
        File startMenu = new File(System.getenv("APPDATA") + "/Microsoft/Internet Explorer/Quick Launch/User Pinned/StartMenu");
        
        File screenshottr = new File(new File(System.getenv("APPDATA")).getParentFile() + "/Local/ScreenShottr/");
        
        try {
            if (installType == InstallType.ENCRYPTED) {
                ShellLink.createLink(screenshottr.getAbsolutePath() + "/ScreenShottrEncrypted.exe", taskbar.getAbsolutePath() + "/ScreenShottrEncrypted.lnk");
                ShellLink.createLink(screenshottr.getAbsolutePath() + "/ScreenShottrEncrypted.exe", startMenu.getAbsolutePath() + "/ScreenShottrEncrypted.lnk");
            }
            else {
                ShellLink.createLink(screenshottr.getAbsolutePath() + "/ScreenShottr.exe", taskbar.getAbsolutePath() + "/ScreenShottr.lnk");
                ShellLink.createLink(screenshottr.getAbsolutePath() + "/ScreenShottr.exe", startMenu.getAbsolutePath() + "/ScreenShottr.lnk");                
            }
        }
        catch (IOException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        }

        /*try {
         InputStream is = this.getClass().getResourceAsStream("resources/pin.vbs");           
            
         Scanner scanner = new Scanner(is);
            
         while(scanner.hasNext()) {
         write(scanner.nextLine());
         }
            
         Runtime.getRuntime().exec("wscript pin.vbs " + program + " " + programName + " false");
         Runtime.getRuntime().exec("wscript pin.vbs " + program + " " + programName + " true");
         }
         catch (IOException ex) {
         Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
         }*/
    }
    
    public void download(String fileURL, String destinationDirectory) throws IOException {
        String downloadedFileName = fileURL.substring(fileURL.lastIndexOf("/") + 1);
        URL url = new URL(fileURL);
        InputStream is = url.openStream();
        FileOutputStream fos = new FileOutputStream(destinationDirectory + "/" + downloadedFileName);
        
        byte[] buffer = new byte[4096];
        int bytesRead = 0;
        
        System.out.print("Downloading " + downloadedFileName);
        
        while ((bytesRead = is.read(buffer)) != -1) {
            System.out.print(".");
            fos.write(buffer, 0, bytesRead);
        }
        
        System.out.println("done!");
        
        fos.close();
        is.close();
    }
}
