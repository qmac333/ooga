package ooga.model.hand;

public class InvalidCardSelectionException extends Exception{

  public InvalidCardSelectionException(String errorMessage) {
    super(errorMessage);
  }
}
