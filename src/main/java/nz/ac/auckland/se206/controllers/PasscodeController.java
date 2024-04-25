package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import java.net.URISyntaxException;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.gpt.openai.ApiProxyException;

public class PasscodeController {
  @FXML private TextField passcode;
  @FXML private ProgressBar progressBar3;

  private MusicPlayer musicPlayer;

  /** Initializes the chat view, loading the passcode and timer. */
  @FXML
  private void initialize() {
    // Initialize the music player
    try {
      musicPlayer = MusicPlayer.getInstance();
    } catch (URISyntaxException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    // Start playing music
    musicPlayer.play();
    // Initialization code goes here
    Timeline progressBarUpdater =
        new Timeline(
            new KeyFrame(
                Duration.seconds(1),
                event -> {
                  int timerValue = GameState.time;
                  double progress = (timerValue) / 120.0; // Assuming max time is 120 seconds
                  progressBar3.setProgress(progress);
                }));
    progressBarUpdater.setCycleCount(Timeline.INDEFINITE);
    progressBarUpdater.play();
  }

  // when enter is pressed it checks if the passcode is correct
  @FXML
  private void enterPressed(ActionEvent event) throws ApiProxyException, IOException {
    String enteredPasscode = passcode.getText();
    String expectedPasscode = "246810";

    if (enteredPasscode.equalsIgnoreCase(expectedPasscode)) {
      showDialog(
          "Result",
          "Access Granted",
          "The password is correct! Why not try the door to see if it is open?");
      GameState.IsCameraClicked = true;
    } else {
      showDialog("Result", "Access Denied", "The password is incorrect please try again!");
      return;
    }
  }

  // submethod for showing dialog/alert
  private void showDialog(String title, String headerText, String message) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setHeaderText(headerText);
    alert.setContentText(message);
    alert.showAndWait();
  }

  /**
   * Navigates back to the previous view.
   *
   * @param event the action event triggered by the go back button
   * @throws ApiProxyException if there is an error communicating with the API proxy
   * @throws IOException if there is an I/O error
   */
  @FXML
  private void onGoback(ActionEvent event) throws ApiProxyException, IOException {
    App.setRoot(SceneManager.AppUi.ROOM);
  }
}
