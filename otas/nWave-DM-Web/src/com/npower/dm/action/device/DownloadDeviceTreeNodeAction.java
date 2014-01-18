//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package com.npower.dm.action.device;

import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.npower.dm.action.BaseDispatchAction;
import com.npower.dm.core.DDFNode;
import com.npower.dm.core.DeviceTreeNode;
import com.npower.dm.management.DeviceBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.util.XMLPrettyFormatter;

/**
 * MyEclipse Struts Creation date: 06-01-2006
 * 
 * XDoclet definition:
 * 
 * @struts.action parameter="method" validate="true"
 */
public class DownloadDeviceTreeNodeAction extends BaseDispatchAction {

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
  public ActionForward getContent(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    
    String nodeID = request.getParameter("deviceTreeNodeID");
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    DeviceBean deviceBean = factory.createDeviceBean();
    DeviceTreeNode node = deviceBean.getDeviceTreeNodeByID(nodeID);

    if (node != null) {
       if (DDFNode.DDF_FORMAT_BIN.equalsIgnoreCase(node.getFormat())) {
          response.setHeader("Content-Disposition", "inline;filename=\"" + nodeID + ".bin\";"); 
          response.setContentType(node.getType());
          OutputStream out = response.getOutputStream();
          byte[] bytes = node.getBytes();
          out.write(bytes);
          out.flush();
       } else {
         response.setHeader("Content-Disposition", "inline;filename=\"" + nodeID + ".bin\";"); 
         response.setContentType(node.getType());
         String content = node.getStringValue();
         PrintWriter writer = response.getWriter();
         writer.write(content);
         writer.flush();
       }
    } else {
      response.sendError(HttpServletResponse.SC_NOT_FOUND, "Could not found deivce node id: " + nodeID);
    }
    return null;
  }

  /**
   * Return Model belongs to manufacturerID.
   * @param mapping
   * @param form
   * @param req
   * @param res
   * @return
   * @throws Exception
   */
  public ActionForward getContentAsXML(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    
    String nodeID = request.getParameter("deviceTreeNodeID");
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    DeviceBean deviceBean = factory.createDeviceBean();
    DeviceTreeNode node = deviceBean.getDeviceTreeNodeByID(nodeID);

    if (node != null) {
       if (DDFNode.DDF_FORMAT_BIN.equalsIgnoreCase(node.getFormat())) {
          response.setHeader("Content-Disposition", "inline;filename=\"" + nodeID + ".bin\";"); 
          response.setContentType(node.getType());
          OutputStream out = response.getOutputStream();
          byte[] bytes = node.getBytes();
          out.write(bytes);
          out.flush();
       } else {
         response.setHeader("Content-Disposition", "inline;filename=\"" + nodeID + ".bin\";"); 
         response.setContentType(node.getType());
         String content = node.getStringValue();
         XMLPrettyFormatter formatter = new XMLPrettyFormatter(content);
         PrintWriter writer = response.getWriter();
         content = formatter.format();
         writer.write(content);
         writer.flush();
       }
    } else {
      response.sendError(HttpServletResponse.SC_NOT_FOUND, "Could not found deivce node id: " + nodeID);
    }
    return null;
  }

}
