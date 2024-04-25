package nz.ac.auckland.se206.controllers;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.gpt.ChatMessage;
import nz.ac.auckland.se206.gpt.GptPromptEngineering;
import nz.ac.auckland.se206.gpt.openai.ApiProxyException;
import nz.ac.auckland.se206.gpt.openai.ChatCompletionRequest;
import nz.ac.auckland.se206.gpt.openai.ChatCompletionResult;
import nz.ac.auckland.se206.gpt.openai.ChatCompletionResult.Choice;

public class EntryController {

  @FXML private Button btnGoRoom;
  @FXML private TextArea chatTextArea2;
  private ChatCompletionRequest chatCompletionRequest;

  // intialize will be called when the entry view loads
  public void initialize() {
    chatCompletionRequest =
        new ChatCompletionRequest().setN(1).setTemperature(0.2).setTopP(0.5).setMaxTokens(100);
    Task<Void> task =
        new Task<Void>() {
          @Override
          protected Void call() throws Exception {
            ChatMessage responseMsg =
                runGpt(new ChatMessage("user", GptPromptEngineering.getStoryline()));

            Platform.runLater(
                () -> {
                  appendChatMessage(responseMsg); // Append the response message
                });
            return null;
          }
        };
    new Thread(task).start();
  }

  @FXML
  public void btnGoToRoom() {
    App.setRoot(SceneManager.AppUi.ROOM);
  }

  private ChatMessage runGpt(ChatMessage msg) throws ApiProxyException {
    chatCompletionRequest.addMessage(msg);
    try {
      ChatCompletionResult chatCompletionResult = chatCompletionRequest.execute();
      Choice result = chatCompletionResult.getChoices().iterator().next();
      return result.getChatMessage();
    } catch (ApiProxyException e) {
      // To handle exception appropriately
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Appends a chat message to the chat text area.
   *
   * @param msg the chat message to append
   */
  private void appendChatMessage(ChatMessage msg) {
    chatTextArea2.appendText("\n" + msg.getRole() + ": " + msg.getContent() + "\n\n");
  }
}
