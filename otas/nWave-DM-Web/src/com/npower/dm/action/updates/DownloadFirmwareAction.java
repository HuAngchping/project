//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package com.npower.dm.action.updates;

import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.npower.dm.action.BaseDispatchAction;
import com.npower.dm.core.Update;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.UpdateImageBean;

/**
 * MyEclipse Struts Creation date: 06-01-2006
 * 
 * XDoclet definition:
 * 
 * @struts.action parameter="method" validate="true"
 */
public class DownloadFirmwareAction extends BaseDispatchAction {

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
    
    String updateID = request.getParameter("updateID");
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    UpdateImageBean updateBean = factory.createUpdateImageBean();
    Update update = updateBean.getUpdateByID(updateID);
    
    if (update != null) {
          response.setHeader("Content-Disposition", "inline;filename=\"" + updateID + ".bin\";"); 
          response.setContentType("application/octet-stream");
          OutputStream out = response.getOutputStream();
          byte[] bytes = update.getBytes();
          out.write(bytes);
          out.flush();
    } else {
      response.sendError(HttpServletResponse.SC_NOT_FOUND, "Could not found update id: " + updateID);
    }
    return null;
  }

}
