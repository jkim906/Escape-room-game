package nz.ac.auckland.se206.controllers;

import java.util.HashMap;
import javafx.scene.Parent;

public class SceneManager {

  public enum AppUi {
    ROOM,
    CHAT,
    PASSCODE,
    ENTRY,
    ENDING_WIN,
    ENDING_LOSE,
  }

  // A hashmap to store all the scenes
  private static HashMap<AppUi, Parent> sceneMap = new HashMap<AppUi, Parent>();

  // Adds a scene to the hashmap
  public static void addAppUi(AppUi name, Parent root) {
    sceneMap.put(name, root);
  }

  // Returns the scene associated with the input name
  public static Parent getUi(AppUi ui) {
    return sceneMap.get(ui);
  }
}
