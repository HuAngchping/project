/**
  * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/action/autoreg/AutoRegAction.java,v 1.6 2008/08/05 11:23:20 zhao Exp $
  * $Revision: 1.6 $
  * $Date: 2008/08/05 11:23:20 $
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
package com.npower.dm.action.autoreg;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.npower.dm.action.BaseAction;
import com.npower.dm.daemon.AutoRegistrationBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.util.IMEIUtil;
import com.npower.util.HelperUtil;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.6 $ $Date: 2008/08/05 11:23:20 $
 */
public class AutoRegAction extends BaseAction {
  
  /**
   * Decode Imei, orignial hex String: 494D45490A40047014676701FFFFFFFFFFFFFFFFFFFFFFFF00
   * Result: 004400741767618
   * @param hex
   * @return
   */
  public String decodeImei(byte[] bytes) {
    StringBuffer buf = new StringBuffer(bytes.length * 2);
    for (byte b: bytes) {
        int low = b & 0x0f;
        int high = (b & 0xf0) >> 4;
        buf.append(Integer.toHexString(low));
        buf.append(Integer.toHexString(high));
    }
    // trim first "A";
    String result = buf.toString();
    result = result.substring(1, 16);
    return result;
  }
  
  public String parseImei(DynaActionForm form) throws Exception {
    String imei = form.getString("imei");
    String rawData = form.getString("rawdata");
    if (StringUtils.isNotEmpty(rawData)) {
       return parseImei(rawData);
    } else {
      return imei;
    }
  }
  /**
   * @param rawData
   * @return
   * @throws UnsupportedEncodingException
   */
  public String parseImei(String rawData) throws UnsupportedEncodingException {
    byte[] bytes = HelperUtil.hexStringToBytes(rawData);
    String text = new String(bytes, "iso8859-1");
    if (text.indexOf("imei:") >= 0) {
       // Text mode
       String imei = text.substring(text.indexOf("imei:") + 5, text.length());
       return imei;
    } else {
      // Binary mode
      int index = text.indexOf("IMEI");
      byte[] imeiBytes = new byte[10];
      System.arraycopy(bytes, index + 4, imeiBytes, 0, 10);
      System.out.println(HelperUtil.bytesToHexString(imeiBytes));
      String result = this.decodeImei(imeiBytes);
      return result;
    }
  }
  /**
   * Method execute
   * 
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return ActionForward
   */
  public ActionForward execute(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response) throws Exception {
    DynaActionForm form = (DynaActionForm) rawForm;
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    String msisdn = form.getString("msisdn");
    String imei = this.parseImei(form);
    imei = IMEIUtil.calculateIMEI(imei);
    //String oldImei = form.getString("oldImei");
    if (StringUtils.isNotEmpty(msisdn) && StringUtils.isNotEmpty(imei)) {
        try {
            msisdn = msisdn.trim();
            if (msisdn.startsWith("+")) {
               msisdn = msisdn.substring(1, msisdn.length());
            }
            if (msisdn.startsWith("86")) {
               msisdn = msisdn.substring(2, msisdn.length());
            }
            AutoRegistrationBean reg = new AutoRegistrationBean();
            
            reg.setFactory(factory);
            reg.setCarrierExternalID("OTAS");
            reg.setClientPassword("otasdm");
            reg.setClientUsername("otasdm");
            reg.setServerPassword("otasdm");
    
            factory.beginTransaction();
            reg.bind(msisdn, imei);
            factory.commit();
            
            request.setAttribute("status", "success");
            
        } catch (Exception e) {
          factory.rollback();
          throw e;
        }
    }
    return mapping.findForward("success");
  }

}
