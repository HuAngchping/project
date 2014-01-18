/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/responder/SMSRequest.java,v 1.2 2008/12/25 10:29:25 chenlei Exp $
 * $Revision: 1.2 $
 * $Date: 2008/12/25 10:29:25 $
 *
 * ===============================================================================================
 * License, Version 1.1
 *
 * Copyright (c) 1994-2008 NPower Network Software Ltd.  All rights reserved.
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

import com.npower.sms.SmsAddress;

/**
 * 短信的请求类，利用三个必要条件来构造一个SMSRequest
 * @author chenlei
 * @version $Revision: 1.2 $ $Date: 2008/12/25 10:29:25 $
 */

public class SMSRequest {

  private SmsAddress from;
  
  private byte[] message;
  
  private SmsAddress to;

  /**
   * 
   */
  public SMSRequest(SmsAddress from, byte[] message, SmsAddress to) {
    this.from = from;
    this.message = message;
    this.to = to;
  }
  
  
  public SmsAddress getFrom() {
    return from;
  }

  public void setFrom(SmsAddress from) {
    this.from = from;
  }

  public byte[] getMessage() {
    return message;
  }

  public void setMessage(byte[] message) {
    this.message = message;
  }

  public SmsAddress getTo() {
    return to;
  }

  public void setTo(SmsAddress to) {
    this.to = to;
  }

}
