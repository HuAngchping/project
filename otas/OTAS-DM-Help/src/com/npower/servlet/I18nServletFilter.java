/**
 * $Header: /home/master/OTAS-DM-Help/src/com/npower/servlet/I18nServletFilter.java,v 1.2 2008/07/25 10:47:36 hcp Exp $
 * $Revision: 1.2 $
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

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * 在web.xml中增加如下内容 <filter> <filter-name>i18nservletfilter</filter-name>
 * <filter-class>com.npower.servlet.I18nServletFilter</filter-class>
 * <init-param> <param-name>charset</param-name> <param-value>UTF-8</param-value>
 * </init-param> </filter> <filter-mapping> <filter-name>i18nservletfilter</filter-name>
 * <url-pattern>/*</url-pattern> </filter-mapping>
 * 
 * 其中charset参数用于定义客户端提交数据的字符集, 即浏览器提交数据时的字符集, 如果字符集为null或"", 不进行字符集转换.
 * 
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author $Author: hcp $
 * @version $Revision: 1.2 $
 */

public class I18nServletFilter extends HttpServlet implements Filter {

  private Log               log              = LogFactory.getLog(I18nServletFilter.class);

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private FilterConfig      filterConfig;

  private String            charset          = "UTF-8";

  // Handle the passed-in FilterConfig
  public void init(FilterConfig filterConfig) throws ServletException {
    this.filterConfig = filterConfig;
    this.charset = filterConfig.getInitParameter("charset");
    if (this.charset == null || this.charset.trim().length() == 0) {
      this.charset = null;
    }
  }

  // Process the request/response pair
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) {
    try {
      // multipart/form-data
      request.setCharacterEncoding(this.charset);
      filterChain.doFilter(request, response);
      /*
       * if (this.charset != null) { I18nHttpServletRequestWrapper
       * requestWrapper = new I18nHttpServletRequestWrapper((HttpServletRequest)
       * request, charset); filterChain.doFilter(requestWrapper, response); }
       * else { filterChain.doFilter(request, response); }
       */
    } catch (ServletException sx) {
      filterConfig.getServletContext().log(sx.getMessage());
      log.error(sx.getMessage(), sx);
      log.error(sx.getMessage(), sx.getCause());
    } catch (Throwable ex) {
      filterConfig.getServletContext().log(ex.getMessage());
      log.error(ex.getMessage(), ex);
    }
  }

  // Clean up resources
  public void destroy() {
    super.destroy();
  }
}
