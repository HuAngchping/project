/**
  * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/tags/XMLPrettyOutputTag.java,v 1.1 2008/02/21 04:48:26 zhao Exp $
  * $Revision: 1.1 $
  * $Date: 2008/02/21 04:48:26 $
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
package com.npower.dm.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.taglib.TagUtils;
import org.dom4j.DocumentException;

import com.npower.dm.util.XMLPrettyFormatter;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2008/02/21 04:48:26 $
 */
public class XMLPrettyOutputTag extends TagSupport {
  
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private static Log                log    = LogFactory.getLog(XMLPrettyOutputTag.class);

  private String content = null;

  /**
   * 
   */
  public XMLPrettyOutputTag() {
    super();
  }

  /**
   * @return Returns the content.
   */
  public String getContent() {
    return content;
  }

  /**
   * @param content The content to set.
   */
  public void setContent(String content) {
    this.content = content;
  }

  public int doStartTag() throws JspException {
    try {
        if (StringUtils.isNotEmpty(this.getContent())) {
           try {
               XMLPrettyFormatter formatter = new XMLPrettyFormatter(this.getContent());
               TagUtils.getInstance().write(pageContext, TagUtils.getInstance().filter(formatter.format()));
               //pageContext.getOut().write(formatter.format());
           } catch (DocumentException e) {
             log.debug("XMLPrettyOutputTag got a invalidate XML", e);
             //pageContext.getOut().write(this.getContent());
             TagUtils.getInstance().write(pageContext, this.getContent());
           }
        }
    } catch (IOException e) {
      log.error("Error in XMLPrettyOutputTag", e);
      throw new JspException(e);
    }

    return EVAL_BODY_INCLUDE;
  }

}
