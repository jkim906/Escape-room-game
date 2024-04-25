package nz.ac.auckland.se206.controllers;

import java.net.URISyntaxException;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import nz.ac.auckland.se206.App;

public class MusicPlayer {

  private MediaPlayer mediaPlayer;
  private static MusicPlayer instance;

  private MusicPlayer() throws URISyntaxException {
    Media song = new Media(App.class.getResource("/sounds/intenseMusic.mp3").toURI().toString());
    mediaPlayer = new MediaPlayer(song);
    mediaPlayer.setVolume(0.25);
  }

  public static MusicPlayer getInstance() throws URISyntaxException {
    if (instance == null) {
      instance = new MusicPlayer();
    }
    return instance;
  }

  // Plays the music
  public void play() {
    if (mediaPlayer != null && mediaPlayer.getStatus() != MediaPlayer.Status.PLAYING) {
      mediaPlayer.setVolume(0.2);
      mediaPlayer.play();
    }
  }

  // Stops the music
  public void stop() {
    if (mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
      mediaPlayer.stop();
    }
  }
}
