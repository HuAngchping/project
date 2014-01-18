/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/tags/VolumeTag.java,v 1.4 2008/08/14 08:11:24 zhao Exp $
 * $Revision: 1.4 $
 * $Date: 2008/08/14 08:11:24 $
 *
 * ===============================================================================================
 * License, Version 1.1
 *
 * Copyright (c) 1994-2007 NPower Network Software Ltd.  All rights reserved.
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
import java.text.NumberFormat;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * JSP tag to convert size from bytes into human readable form: KB, MB, GB or TB
 * depending on how large the value in bytes is.
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.4 $ $Date: 2008/08/14 08:11:24 $
 */
public class VolumeTag extends TagSupport {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  

  public static final double KB        = 1024;

  public static final double MB        = KB * 1024;

  public static final double GB        = MB * 1024;

  public static final double TB        = GB * 1024;

  private Log                logger    = LogFactory.getLog(getClass());

  private long               value;

  private int                fractions = 0;
  
  private String             unit = "";

  public void setValue(long value) {
    this.value = value;
  }

  public int getFractions() {
    return fractions;
  }

  public void setFractions(int fractions) {
    this.fractions = fractions;
  }

  public int doStartTag() throws JspException {
    double doubleResult;
    String suffix;

    if (value < KB) {
      doubleResult = value;
      suffix = "" + this.getUnit();
    } else if (value >= KB && value < MB) {
      doubleResult = round(value / KB);
      suffix = "K" + this.getUnit();
    } else if (value >= MB && value < GB) {
      doubleResult = round(value / MB);
      suffix = "M" + this.getUnit();
    } else if (value >= GB && value < TB) {
      doubleResult = round(value / GB);
      suffix = "G" + this.getUnit();
    } else {
      doubleResult = round(value / TB);
      suffix = "T" + this.getUnit();
    }

    try {
      NumberFormat nf = NumberFormat.getInstance();
      nf.setMinimumFractionDigits(fractions);
      pageContext.getOut().write(nf.format(doubleResult) + suffix);
    } catch (IOException e) {
      logger.debug(e);
      throw new JspException(e);
    }

    return EVAL_BODY_INCLUDE;
  }

  private double round(double value) {
    return Math.round(value * Math.pow(10, fractions)) / Math.pow(10, fractions);
  }

  /**
   * @return the unit
   */
  public String getUnit() {
    return (unit != null)?unit:"";
  }

  /**
   * @param unit the unit to set
   */
  public void setUnit(String unit) {
    this.unit = unit;
  }
}
