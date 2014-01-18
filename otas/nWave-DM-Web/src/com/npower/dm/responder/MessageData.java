package com.npower.dm.responder;

public interface MessageData {

  /**
   * @return Returns the rawMessage.
   */
  public abstract byte[] getRawMessage();

  /**
   * @param rawMessage The rawMessage to set.
   */
  public abstract void setRawMessage(byte[] rawMessage);

}