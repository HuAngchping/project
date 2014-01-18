/**
  * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/responder/MessageParserImpl.java,v 1.1 2008/10/22 00:15:05 zhao Exp $
  * $Revision: 1.1 $
  * $Date: 2008/10/22 00:15:05 $
  *
  * ===============================================================================================
  * License, Version 1.1
  *
  * Copyright (c) 1994-2006 NPower Network Software Ltd.  All rights reserved.
  *
  * This SOURCE CODE FILE, which has been provided by NPower as part
  * of a NPower product for use ONLY by licensed users of the product,
  * includes CONFIDENTIAL and PROPRIETARY information of NPower.
  *
  * USE OF THIS SOFTWARE IS GOVERNED BY THE TERMS AND CONDITIONS
  * OF THE LICENSE STATEMENT AND LIMITED WARRANTY FURNISHED WITH
  * THE PRODUCT.
  *
  * IN PARTICULAR, YOU WILL INDEMNIFY AND HOLD NPower, ITS RELATED
  * COMPANIES AND ITS SUPPLIERS, HARMLESS FROM AND AGAINST ANY CLAIMS
  * OR LIABILITIES ARISING OUT OF THE USE, REPRODUCTION, OR DISTRIBUTION
  * OF YOUR PROGRAMS, INCLUDING ANY CLAIMS OR LIABILITIES ARISING OUT OF
  * OR RESULTING FROM THE USE, MODIFICATION, OR DISTRIBUTION OF PROGRAMS
  * OR FILES CREATED FROM, BASED ON, AND/OR DERIVED FROM THIS SOURCE
  * CODE FILE.
  * ===============================================================================================
  */
package com.npower.dm.responder;

import java.io.UnsupportedEncodingException;

import org.apache.commons.lang.StringUtils;

import com.npower.sms.SmsAddress;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2008/10/22 00:15:05 $
 */
public class MessageParserImpl implements MessageParser<SimpleMessageData> {

  /**
   * 
   */
  public MessageParserImpl() {
    super();
  }
  
  /* (non-Javadoc)
   * @see com.npower.sms.responder.MessageParser#parse(com.npower.sms.SmsAddress, byte[], com.npower.sms.SmsAddress)
   */
  public SimpleMessageData parse(SmsAddress from, byte[] message, SmsAddress serviceID) throws InvalidateMessageException {
    try {
        String text = new String(message, "UTF-8");
        if (StringUtils.isEmpty(text)) {
           throw new InvalidateMessageException("sms is empty!");
        }
        text = text.replace('¡¡', ' ');
        text = text.trim();
        
        String[] cols = StringUtils.split(text, ' ');
        if (cols.length == 3) {
          return new SimpleMessageData(cols[0], cols[1], cols[2], message);
        } else if (cols.length == 2) {
          return new SimpleMessageData(null, cols[0], cols[1], message);
        } else {
          throw new InvalidateMessageException("sms message is invalidate");
        }
    } catch (UnsupportedEncodingException e) {
      throw new InvalidateMessageException("error to detect model from sms: " + message, e);
    }
  }

}
