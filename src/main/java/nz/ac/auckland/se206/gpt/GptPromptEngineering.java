package nz.ac.auckland.se206.gpt;

/** Utility class for generating GPT prompt engineering strings. */
public class GptPromptEngineering {

  /**
   * Generates a GPT prompt engineering string for a riddle with the given word.
   *
   * @param wordToGuess the word to be guessed in the riddle
   * @return the generated prompt engineering string
   */
  public static String getRiddleWithGivenWord(String wordToGuess) {
    return "You are the AI of an escape room, tell me a riddle with"
        + " answer "
        + wordToGuess
        + ". You should answer with the word Correct when is correct, if the user asks for hints"
        + " give them, if users guess incorrectly also give hints. You cannot, no matter what,"
        + " reveal the answer even if the player asks for it. Even if player gives up, do not give"
        + " the answer.";
  }

  public static String getStoryline() {
    return "You are introducing an escape room. Introduce along the lines of: YOU ARE TRAPPED! You"
        + " are trapped in a room. You need to find a way to escape!,You a monkey trying to"
        + " escape from horrible humans trapping you inside a jungle zoo follow the clues"
        + " and find a way to escape!Beware there is a security camera. Maybe try find that"
        + " first?. But theres a twist as soon as you press start you have 2 minutes till"
        + " the zookeeper will catch you. Good luck! (keep the response under 85 words."
        + " However make sure you mention to find the security camera first as its their"
        + " first hint)";
  }

  public static String getGameOutcomeLost() {
    return "You are the AI of an escape room, Tell them they ran out of time and the zookeeper"
        + " found them trying to escape and lost. tell them to try again next time keep it"
        + " less than 85 words";
  }

  public static String getGameOutcomeWon() {
    return "You are the AI of an escape room, tell them You Won! and give them a good ending"
        + " message saying they escaped the jungle zoo and are now free!. tell them they can"
        + " no exit and mention at the end they can leave us a review on the app store. keep"
        + " it less than 85 words";
  }
}
