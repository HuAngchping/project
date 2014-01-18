/**
 * $Header: /home/master/OTAS-DM-Help/src/com/npower/servlet/I18nHttpServletRequestWrapper.java,v 1.3 2008/07/25 10:47:36 hcp Exp $
 * $Revision: 1.3 $
 * $Date: 2008/07/25 10:47:36 $
 *
 * ===============================================================================================
 * License, Version 1.1
 *
 * Copyright (c) 1994-2003 IEI Technology (China) Ltd.  All rights reserved.
 *
 * This SOURCE CODE FILE, which has been provided by IEI as part
 * of a IEI product for use ONLY by licensed users of the product,
 * includes CONFIDENTIAL and PROPRIETARY information of IEI.
 *
 * USE OF THIS SOFTWARE IS GOVERNED BY THE TERMS AND CONDITIONS
 * OF THE LICENSE STATEMENT AND LIMITED WARRANTY FURNISHED WITH
 * THE PRODUCT.
 *
 * IN PARTICULAR, YOU WILL INDEMNIFY AND HOLD IEI, ITS RELATED
 * COMPANIES AND ITS SUPPLIERS, HARMLESS FROM AND AGAINST ANY CLAIMS
 * OR LIABILITIES ARISING OUT OF THE USE, REPRODUCTION, OR DISTRIBUTION
 * OF YOUR PROGRAMS, INCLUDING ANY CLAIMS OR LIABILITIES ARISING OUT OF
 * OR RESULTING FROM THE USE, MODIFICATION, OR DISTRIBUTION OF PROGRAMS
 * OR FILES CREATED FROM, BASED ON, AND/OR DERIVED FROM THIS SOURCE
 * CODE FILE.
 * ===============================================================================================
 */

package com.npower.servlet;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

// import com.npower.im.config.Global;
/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author $Author: hcp $
 * @version $Revision: 1.3 $
 */

public class I18nHttpServletRequestWrapper extends HttpServletRequestWrapper {

  private static Log          log     = LogFactory.getLog(I18nHttpServletRequestWrapper.class);

  private Map<String, Object> hashMap = new HashMap<String, Object>();

  private String              charset = "iso-8859-1";

  public I18nHttpServletRequestWrapper(HttpServletRequest request, String charset) {
    super(request);
    this.charset = charset;
    initParameterMap(request);
  }

  @SuppressWarnings("unchecked")
  private void initParameterMap(HttpServletRequest request) {
    if (request == null) {
      return;
    }
    Map map = request.getParameterMap();
    Set names = map.keySet();
    for (Iterator i = names.iterator(); i.hasNext();) {
      String name = (String) i.next();
      this.hashMap.put(name, map.get(name));
    }
  }

  public String getParameter(String name) {
    String[] values = this.getParameterValues(name);
    if (values != null && values.length > 0) {
      return values[0];
    } else {
      return null;
    }
  }

  public String[] getParameterValues(String name) {
    String[] values = (String[]) this.hashMap.get(name);
    if (values == null) {
      return null;
    }
    String[] result = new String[values.length];
    for (int i = 0; values != null && i < values.length; i++) {
      result[i] = convertCharset(values[i]);
    }
    return result;
  }

  private String convertCharset(String str) {
    if (str == null) {
      return null;
    }
    try {
      byte[] bs = str.getBytes("iso-8859-1");
      String result = new String(bs, this.charset);
      return result;
    } catch (UnsupportedEncodingException ex) {
      log.fatal("Failure in convertCharset for charset: " + this.charset, ex);
    }
    return null;
  }

}
