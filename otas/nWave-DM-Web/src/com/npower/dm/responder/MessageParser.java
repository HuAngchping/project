package com.npower.dm.responder;

import com.npower.sms.SmsAddress;

public interface MessageParser<E> {

  /**
   * ������Ϣ��
   * @param from
   * @param message
   * @param serviceID
   * @return
   * @throws InvalidateMessageException
   */
  public abstract E parse(SmsAddress from, byte[] message, SmsAddress serviceID)
      throws InvalidateMessageException;

}