//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package com.npower.dm.myportal;

import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.npower.dm.action.BaseDispatchAction;
import com.npower.dm.core.Model;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ModelBean;

/**
 * MyEclipse Struts Creation date: 06-01-2006
 * 
 * XDoclet definition:
 * 
 * @struts.action parameter="method" validate="true"
 */
public class DownloadAction extends BaseDispatchAction {

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
  public ActionForward getModelIcon(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    
    String modelID = request.getParameter("modelID");
    if (StringUtils.isNotEmpty(modelID)) {
       ManagementBeanFactory factory = this.getManagementBeanFactory(request);
       ModelBean modelBean = factory.createModelBean();
       Model model = modelBean.getModelByID(modelID);
       if (model != null) {
          byte[] iconBytes = model.getIconBinary();
          if (iconBytes != null && iconBytes.length > 0) {
             response.setHeader("Content-Disposition", "inline;filename=\"" + modelID + ".jpg\";"); 
             response.setContentType("image/jpeg");
             response.setContentLength(iconBytes.length);
             OutputStream out = response.getOutputStream();
             out.write(iconBytes);
             out.flush();
             return null;
          }
     }        
    }
    return mapping.findForward("DefaultIcon");

    //response.sendError(HttpServletResponse.SC_NOT_FOUND, "Could not found deivce node id: " + modelID);
    //return null;
  }
  
  public ActionForward getModelIconHTML(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    String modelID = request.getParameter("modelID");
    if (StringUtils.isNotEmpty(modelID)) {
       ManagementBeanFactory factory = this.getManagementBeanFactory(request);
       ModelBean modelBean = factory.createModelBean();
       Model model = modelBean.getModelByID(modelID);
       byte[] iconBytes = model.getIconBinary();
       if (iconBytes != null && iconBytes.length > 0) {
          response.getWriter().write("<div id='specs-model-pic'><img src='" + request.getContextPath() + "/download.do?method=getModelIcon&modelID=" 
                                     + modelID + "' alt='" + model.getManufacturer().getName() + " " + model.getManufacturerModelId() + "'/></div>");
       } else {
         response.getWriter().write("<div id='specs-model-pic'><img src='" + request.getContextPath() + "/download.do?method=getModelIcon&modelID=" 
             + modelID + "' alt='" + model.getManufacturer().getName() + " " + model.getManufacturerModelId() + "'/></div>");
       }
    }
    response.getWriter().write("");
    return null;
    
  }
  

}
