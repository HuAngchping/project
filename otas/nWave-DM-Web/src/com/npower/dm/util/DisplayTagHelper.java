/**
  * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/util/DisplayTagHelper.java,v 1.1 2007/06/18 10:28:52 zhao Exp $
  * $Revision: 1.1 $
  * $Date: 2007/06/18 10:28:52 $
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
package com.npower.dm.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.displaytag.tags.TableTagParameters;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2007/06/18 10:28:52 $
 */
public class DisplayTagHelper {

  /**
   * Private constructor.
   */
  private DisplayTagHelper() {
    super();
  }
  
  /**
   * Extract page number from HttpServletRequest.
   * @param request
   * @return
   */
  public static int getPageNumber(HttpServletRequest request) {
    String pageNumberString = request.getParameter(TableTagParameters.SORT_AMOUNT_PAGE);
    if (StringUtils.isNotEmpty(pageNumberString)) {
      try {
          int pageNumber = Integer.parseInt(pageNumberString);
          return pageNumber;
      } catch (Exception ex) {
        
      }
    }
    return 1;
  }

}
