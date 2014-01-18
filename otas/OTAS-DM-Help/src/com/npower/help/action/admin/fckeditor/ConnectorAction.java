/**
 * test
 */
package com.npower.help.action.admin.fckeditor;

import java.io.InputStream;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;

import com.npower.help.action.HelpDAOPersistentManager;
import com.npower.help.core.Subject;
import com.npower.help.core.SubjectContent;
import com.npower.help.core.SubjectImage;
import com.npower.help.core.SubjectLocale;
import com.npower.help.dao.HelpDAOFactory;
import com.npower.help.dao.SubjectContentDAO;
import com.npower.help.dao.SubjectDAO;
import com.npower.help.dao.SubjectImageDAO;
import com.npower.help.dao.SubjectLocaleDAO;

/**
 * @author Huang
 * 
 */
public class ConnectorAction extends DispatchAction {
  public ActionForward GetFoldersAndFiles(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    HelpDAOFactory factory = null;
    DynaActionForm form = (DynaActionForm) rawForm;
    try {
      factory = HelpDAOPersistentManager.getManagementBeanFactory(request);
      SubjectDAO subjectDAO = factory.getSubjectDAO();
      SubjectLocaleDAO localeDAO = factory.getSubjectLocaleDAO();
      SubjectContentDAO contentDAO = factory.getSubjectContentDAO();
      SubjectContent currentContent = getCurrentContent(form, subjectDAO, localeDAO, contentDAO, request);

      Set<SubjectImage> images = currentContent.getSubjectImages();
      request.setAttribute("images", images);
    } catch (Throwable e) {
      if (factory != null) {
        factory.rollback();
      }
      throw new Exception(e.getMessage(), e);
    }
    return mapping.findForward("GetFoldersAndFiles");
  }

  public ActionForward FileUpload(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    HelpDAOFactory factory = null;
    DynaActionForm form = (DynaActionForm) rawForm;
    try {
      factory = HelpDAOPersistentManager.getManagementBeanFactory(request);
      SubjectDAO subjectDAO = factory.getSubjectDAO();
      SubjectLocaleDAO localeDAO = factory.getSubjectLocaleDAO();
      SubjectContentDAO contentDAO = factory.getSubjectContentDAO();
      SubjectImageDAO imageDAO = factory.getSubjectImageDAO();

      SubjectContent currentContent = getCurrentContent(form, subjectDAO, localeDAO, contentDAO, request);

      FormFile fileForm = (FormFile) form.get("NewFile");
      String filename = fileForm.getFileName();
      InputStream ins = fileForm.getInputStream();

      SubjectImage image = imageDAO.newInstance();
      image.setSubjectContent(currentContent);
      image.setMimeType(fileForm.getContentType());
      image.setBinary(ins);
      image.setFilename(filename);
      factory.beginTransaction();
      imageDAO.save(image);
      factory.commit();

      request.setAttribute("image", image);
    } catch (Throwable e) {
      if (factory != null) {
        factory.rollback();
      }
      throw new Exception(e.getMessage(), e);
    }
    return mapping.findForward("FileUpload");
  }

  /**
   * @param form
   * @param subjectDAO
   * @param localeDAO
   * @param contentDAO
   * @return
   */
  private SubjectContent getCurrentContent(DynaActionForm form, SubjectDAO subjectDAO, SubjectLocaleDAO localeDAO,
      SubjectContentDAO contentDAO, HttpServletRequest request) {
    String subjectId = (String) request.getSession().getAttribute("subjectId");
    Subject subject = subjectDAO.findById(Long.parseLong(subjectId));
    String localeId = (String) request.getSession().getAttribute("localeId");
    SubjectLocale currentLocale = null;
    if (StringUtils.isNotEmpty(localeId)) {
      currentLocale = localeDAO.findById(localeId);
    } else {
      localeId = (String) request.getSession().getAttribute("defaultLocaleId");
      currentLocale = localeDAO.findById(localeId);
    }

    SubjectContent currentContent = contentDAO.getSubjectContent(subject, currentLocale);
    return currentContent;
  }
}
