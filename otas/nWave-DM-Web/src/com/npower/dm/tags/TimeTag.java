/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/tags/TimeTag.java,v 1.3 2009/02/23 07:42:11 zhaowx Exp $
 * $Revision: 1.3 $
 * $Date: 2009/02/23 07:42:11 $
 *
 * ===============================================================================================
 * License, Version 1.1
 *
 * Copyright (c) 1994-2009 NPower Network Software Ltd.  All rights reserved.
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

package com.npower.dm.tags;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * @author Zhao wanxiang
 * @version $Revision: 1.3 $ $Date: 2009/02/23 07:42:11 $
 */

public class TimeTag extends TagSupport {

  private static final long serialVersionUID = 1L;

  private long               value;
  
  private boolean    milliSecond; 
  
  private Log                logger    = LogFactory.getLog(getClass());
 

  public long getValue() {
    return value;
  }

  public void setValue(long value) {
    this.value = value;
  }  

  public boolean isMilliSecond() {
    return milliSecond;
  }

  public void setMilliSecond(boolean milliSecond) {
    this.milliSecond = milliSecond;
  }

  public int doStartTag() throws JspException {
    TimeItem timeItem = new TimeItem(this.value,this.milliSecond);
    timeItem.setRequest((HttpServletRequest)this.pageContext.getRequest());

    try {
      pageContext.getOut().write(timeItem.toString());
    } catch (IOException e) {
      logger.debug(e);
      throw new JspException(e);
    }


    return EVAL_BODY_INCLUDE;
  }

}
