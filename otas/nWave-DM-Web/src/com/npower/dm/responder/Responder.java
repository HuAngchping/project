/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/responder/Responder.java,v 1.1 2008/10/22 00:15:05 zhao Exp $
 * $Revision: 1.1 $
 * $Date: 2008/10/22 00:15:05 $
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

import java.util.List;

import com.npower.sms.SmsAddress;
import com.npower.sms.SmsException;
import com.npower.sms.SmsMessage;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2008/10/22 00:15:05 $
 */
public interface Responder {
  
  /**
   * 根据终端提交的消息信息, 返回应答的结果消息.
   * @param from
   *        消息的来源地址
   * @param message
   *        消息内容
   * @param serviceID
   *        消息的目标地址
   * @return
   * @throws SmsException
   */
  public abstract List<SmsMessage> response(SmsAddress from, byte[] message, SmsAddress serviceID) throws SmsException;
  
}
