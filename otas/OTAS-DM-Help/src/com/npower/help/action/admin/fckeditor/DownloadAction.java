/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.npower.help.action.admin.fckeditor;

import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.npower.help.action.HelpDAOPersistentManager;
import com.npower.help.core.SubjectImage;
import com.npower.help.dao.HelpDAOFactory;
import com.npower.help.dao.SubjectImageDAO;

/**
 * MyEclipse Struts Creation date: 07-05-2008
 * 
 * XDoclet definition:
 * 
 * @struts.action validate="true"
 */
public class DownloadAction extends Action {
  /*
   * Generated Methods
   */

  /**
   * Method execute
   * 
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return ActionForward
   */
  public ActionForward execute(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    HelpDAOFactory factory = null;
    try {
      factory = HelpDAOPersistentManager.getManagementBeanFactory(request);
      SubjectImageDAO imageDAO = factory.getSubjectImageDAO();
      String filename = request.getParameter("filename");
      int _index = filename.indexOf("_");
      String imageId = filename.substring(0, _index);
      SubjectImage image = imageDAO.findById(Long.parseLong(imageId));
      if (image != null) {
        OutputStream out = response.getOutputStream();
        Blob blob = image.getBinary();
        InputStream in = blob.getBinaryStream();
        int len = (int) blob.length();
        byte[] buf = new byte[len];
        while ((len = in.read(buf)) != -1) {
          out.write(buf, 0, len);
        }
      }
    } catch (Exception e) {
      if (factory != null) {
        factory.rollback();
      }
      throw e;
    }
    return null;
  }
}