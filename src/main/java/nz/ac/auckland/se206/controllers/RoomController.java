package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import java.net.URISyntaxException;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.speech.TextToSpeech;

/** Controller class for the room view. */
public class RoomController {

  @FXML private Rectangle door;
  @FXML private Rectangle window;
  @FXML private Rectangle lamp;
  @FXML private Button btnStart;
  @FXML private Pane room;
  @FXML private Rectangle camera;
  @FXML private ProgressBar progressBar;
  @FXML private Rectangle axe;

  private MediaPlayer mediaPlayer;

  /** Initializes the room view, it is called when the room loads. */
  public void initialize() {
    // Initialization code goes here
    startMessage();
  }

  private void startMessage() {
    Task<Void> task =
        new Task<Void>() {
          @Override
          protected Void call() throws Exception {
            TextToSpeech textToSpeech = new TextToSpeech();
            textToSpeech.speak(
                "Welcome to my virtual escape room!  You have 2 minutes to escape! read the"
                    + " instructions carefully and Good luck!");

            return null;
          }
        };
    new Thread(task).start();
  }

  /**
   * Handles the click event on the axe.
   *
   * @param event the mouse event
   */
  @FXML
  public void btnAxePressed(MouseEvent event) {
    showDialog("Info", "Axe Found", "246810");
    GameState.isAxeFound = true;
  }

  /**
   * Handles the key pressed event.
   *
   * @param event the key event
   */
  @FXML
  public void onKeyPressed(KeyEvent event) {
    System.out.println("key " + event.getCode() + " pressed");
  }

  /**
   * Handles the key released event.
   *
   * @param event the key event
   */
  @FXML
  public void onKeyReleased(KeyEvent event) {
    System.out.println("key " + event.getCode() + " released");
  }

  /**
   * Displays a dialog box with the given title, header text, and message.
   *
   * @param title the title of the dialog box
   * @param headerText the header text of the dialog box
   * @param message the message content of the dialog box
   */
  private void showDialog(String title, String headerText, String message) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setHeaderText(headerText);
    alert.setContentText(message);
    alert.showAndWait();
  }

  /**
   * Handles the click event on the door.
   *
   * @param event the mouse event
   * @throws IOException if there is an error loading the chat view
   * @throws URISyntaxException
   */
  @FXML
  public void clickDoor(MouseEvent event) throws IOException, URISyntaxException {
    System.out.println("door clicked");
    if (!GameState.isRiddleResolved && GameState.IsCameraClicked) {
      showDialog(
          "Info",
          "Riddle -The answer to the riddle will lead you to the key!",
          "You need to resolve the riddle! The answer of the riddle will be your key out!");
      App.setRoot(SceneManager.AppUi.CHAT);
      return;
    } else if (!GameState.isRiddleResolved && !GameState.IsCameraClicked) {
      showDialog("Info", "Eyes are everywhere", "You need to disable the security camera first!");
      return;
    } else if (GameState.isRiddleResolved && GameState.isKeyFound) {
      showDialog("Info", "You Won!", "Good Job! You are now free!");
      App.setRoot(SceneManager.AppUi.ENDING_WIN);
      MusicPlayer.getInstance().stop();
      playHappyMusic(); // Load the clap sound
      mediaPlayer.play(); // Play the clap sound
      App.setRoot(SceneManager.AppUi.ENDING_WIN);
    } else {
      showDialog("Info", "Door Locked", "You need to find the key first!");
    }
  }

  /**
   * Handles the click event on the lamp.
   *
   * @param event the mouse event
   */
  @FXML
  public void clickLamp(MouseEvent event) {
    System.out.println("Lamp clicked");
    if (GameState.isRiddleResolved && !GameState.isKeyFound) {
      showDialog("Info", "Key Found", "You found a key under the inside the lamp!");
      GameState.isKeyFound = true;
    }
  }

  private void playHappyMusic() throws URISyntaxException {
    try {
      Media clapSound = new Media(App.class.getResource("/sounds/clap.mp3").toURI().toString());
      mediaPlayer = new MediaPlayer(clapSound);
      mediaPlayer.setVolume(0.6);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void playSadMusic() throws URISyntaxException {
    try {
      Media clapSound = new Media(App.class.getResource("/sounds/sad.mp3").toURI().toString());
      mediaPlayer = new MediaPlayer(clapSound);
      mediaPlayer.setVolume(0.6);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Handles the click event on the window.
   *
   * @param event the mouse event
   */
  @FXML
  public void clickCamera(MouseEvent event) {
    App.setRoot(SceneManager.AppUi.PASSCODE);
    if (!GameState.isAxeFound) {
      showDialog(
          "Info",
          "Good Job you found the camera...But wait theres a security lock on it!",
          "the password is written on an axe in the room find it and enter the password to disable"
              + " the camera!");
    }
  }

  // When start button is pressed timer starts and button is removed
  @FXML
  private void btnTimerClicked(MouseEvent event) throws URISyntaxException {
    room.getChildren().remove(btnStart);
    // Show and start the progress bar
    startTimer();
  }

  // submethod to start the timer and show on progress bar(used concurrency)
  private void startTimer() {

    IntegerProperty seconds = new SimpleIntegerProperty();

    seconds.addListener(
        (observable, oldValue, newValue) -> {
          // Update the variable in GameState here
          GameState.time = newValue.intValue();
          System.out.println(GameState.time);
          Task<Void> task =
              new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                  // Text to speech for timer alerting player of time left
                  if (GameState.time == 60) {
                    TextToSpeech textToSpeech = new TextToSpeech();
                    textToSpeech.speak("You have 1 minute left!");
                  } else if (GameState.time == 30) {
                    TextToSpeech textToSpeech = new TextToSpeech();
                    textToSpeech.speak("You have 30 seconds left!");
                  } else if (GameState.time == 10) {
                    TextToSpeech textToSpeech = new TextToSpeech();
                    textToSpeech.speak("You have 10 seconds left!");
                  } else if (GameState.time == 0) {
                    TextToSpeech textToSpeech = new TextToSpeech();
                    textToSpeech.speak("Time is up!");
                  } else if (GameState.time == 3) {
                    TextToSpeech textToSpeech = new TextToSpeech();
                    textToSpeech.speak("3");
                  } else if (GameState.time == 2) {
                    TextToSpeech textToSpeech = new TextToSpeech();
                    textToSpeech.speak("2");
                  } else if (GameState.time == 1) {
                    TextToSpeech textToSpeech = new TextToSpeech();
                    textToSpeech.speak("1");
                  }
                  return null;
                }
              };
          new Thread(task).start();
        });

    progressBar.progressProperty().bind(seconds.divide(120.0)); // 2 minutes

    Timeline timeline =
        new Timeline(
            new KeyFrame(Duration.ZERO, new KeyValue(seconds, 120)),
            new KeyFrame(Duration.seconds(120), new KeyValue(seconds, 0)));
    timeline.setCycleCount(1); // Run the timeline only once

    timeline.setOnFinished(
        e -> {
          progressBar.setVisible(false);
          room.getChildren().remove(progressBar);
          if (!GameState.gameOutcome) {
            GameState.gameOutcome = false;
            App.setRoot(SceneManager.AppUi.ENDING_LOSE);
            try {
              MusicPlayer.getInstance().stop();
              playSadMusic(); // Load the clap sound
              mediaPlayer.play(); // Play the clap sound
            } catch (URISyntaxException e1) {
              // TODO Auto-generated catch block
              e1.printStackTrace();
            }
          } else {
            App.setRoot(SceneManager.AppUi.ENDING_WIN);
            GameState.gameOutcome = true;
            try {
              MusicPlayer.getInstance().stop();
            } catch (URISyntaxException e1) {
              // TODO Auto-generated catch block
              e1.printStackTrace();
            }
          }
        });

    timeline.play();
  }
}
