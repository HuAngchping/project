//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package com.npower.dm.action.config;

import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.npower.dm.action.BaseDispatchAction;
import com.npower.dm.core.ProfileAttributeValue;
import com.npower.dm.core.ProfileConfig;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ProfileConfigBean;

/**
 * MyEclipse Struts Creation date: 06-01-2006
 * 
 * XDoclet definition:
 * 
 * @struts.action parameter="method" validate="true"
 */
public class DownloadAttributeValueAction extends BaseDispatchAction {

  // --------------------------------------------------------- Methods

  /**
   * Return Model belongs to manufacturerID.
   * @param mapping
   * @param form
   * @param req
   * @param res
   * @return
   * @throws Exception
   */
  public ActionForward getBinary(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    
    String profileID = request.getParameter("profileID");
    String attributeName = request.getParameter("attributeName");
    if (StringUtils.isEmpty(profileID) || StringUtils.isEmpty(attributeName)) {
       response.sendError(HttpServletResponse.SC_NOT_FOUND, "Please specify download parameters.");
       return null;
    }
    
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    ProfileConfigBean bean = factory.createProfileConfigBean();
    ProfileConfig profile = bean.getProfileConfigByID(profileID);
    ProfileAttributeValue value = profile.getProfileAttributeValue(attributeName);
    if (value != null) {
          response.setHeader("Content-Disposition", "inline;filename=\"" + attributeName + ".bin\";"); 
          response.setContentType("application/octet-stream");
          OutputStream out = response.getOutputStream();
          InputStream in = value.getBinaryData().getBinaryStream();
          byte[] buf = new byte[512];
          int size = in.read(buf);
          while (size > 0) {
                out.write(buf, 0, size);
                size = in.read(buf);
                out.flush();
          }
    } else {
      response.sendError(HttpServletResponse.SC_NOT_FOUND, "Could not found attribute name: " + attributeName + " in profile ID: " + profileID);
    }
    return null;
  }

}
