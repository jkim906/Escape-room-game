package nz.ac.auckland.se206;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import nz.ac.auckland.se206.controllers.SceneManager;
import nz.ac.auckland.se206.controllers.SceneManager.AppUi;

/**
 * This is the entry point of the JavaFX application, while you can change this class, it should
 * remain as the class that runs the JavaFX application.
 */
public class App extends Application {

  private static Scene scene;

  public static void main(final String[] args) {
    launch();
  }

  public static void setRoot(AppUi newUi) {
    scene.setRoot(SceneManager.getUi(newUi));
  }

  /**
   * Returns the node associated to the input file. The method expects that the file is located in
   * "src/main/resources/fxml".
   *
   * @param fxml The name of the FXML file (without extension).
   * @return The node of the input file.
   * @throws IOException If the file is not found.
   */
  private static Parent loadFxml(final String fxml) throws IOException {
    try {
      return new FXMLLoader(App.class.getResource("/fxml/" + fxml + ".fxml")).load();
    } catch (IOException e) {
      e.printStackTrace();
      throw e;
    }
  }

  public static void restart() {
    // Restart the application by launching a new instance of App
    App.launch(App.class);
  }

  /**
   * This method is invoked when the application starts. It loads and shows the "Canvas" scene.
   *
   * @param stage The primary stage of the application.
   * @throws IOException If "src/main/resources/fxml/canvas.fxml" is not found.
   */
  @Override
  public void start(final Stage stage) throws IOException {
    SceneManager.addAppUi(SceneManager.AppUi.ROOM, loadFxml("room"));
    SceneManager.addAppUi(SceneManager.AppUi.PASSCODE, loadFxml("password"));
    SceneManager.addAppUi(SceneManager.AppUi.ENTRY, loadFxml("entry"));
    SceneManager.addAppUi(SceneManager.AppUi.CHAT, loadFxml("chat"));
    SceneManager.addAppUi(SceneManager.AppUi.ENDING_WIN, loadFxml("endingWin"));
    SceneManager.addAppUi(SceneManager.AppUi.ENDING_LOSE, loadFxml("endingLoss"));
    scene = new Scene(SceneManager.getUi(AppUi.ENTRY), 600, 470);
    stage.setScene(scene);
    stage.show();
    SceneManager.getUi(AppUi.ROOM).requestFocus();
  }
}
