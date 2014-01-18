/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.npower.help.action.admin.subjects;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.LookupDispatchAction;

import com.npower.help.action.ActionHelper;
import com.npower.help.action.HelpDAOPersistentManager;
import com.npower.help.core.Subject;
import com.npower.help.core.SubjectContent;
import com.npower.help.core.SubjectLocale;
import com.npower.help.dao.HelpDAOFactory;
import com.npower.help.dao.SubjectContentDAO;
import com.npower.help.dao.SubjectDAO;
import com.npower.help.dao.SubjectLocaleDAO;

/**
 * MyEclipse Struts Creation date: 06-30-2008
 * 
 * XDoclet definition:
 * 
 * @struts.action validate="true"
 */
public class SaveContentAction extends LookupDispatchAction {
  /*
   * Generated Methods
   */
  @Override
  protected Map<String, String> getKeyMethodMap() {
    Map<String, String> map = new HashMap<String, String>();
    map.put("page.help.admin.edit4content.page.message.save.button.lable", "save");
    map.put("page.help.admin.edit4content.page.message.create.button.lable", "create");
    return map;
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
  public ActionForward save(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    HelpDAOFactory factory = null;
    DynaActionForm form = (DynaActionForm) rawForm;
    try {
      factory = HelpDAOPersistentManager.getManagementBeanFactory(request);

      // Load Subjects Tree
      ActionHelper.loadSubjectsTree(this.getServlet().getServletContext(), request, factory);

      SubjectDAO subjectDAO = factory.getSubjectDAO();
      SubjectLocaleDAO localeDAO = factory.getSubjectLocaleDAO();
      SubjectContentDAO contentDAO = factory.getSubjectContentDAO();

      String subjectId = form.getString("subjectId");
      Subject subject = subjectDAO.findById(Long.parseLong(subjectId));

      String localeId = form.getString("localeId");
      SubjectLocale locale = localeDAO.findById(localeId);

      String name = form.getString("name");
      String description = form.getString("description");
      String keywords = form.getString("keywords");
      String text = form.getString("content");

      SubjectContent content = contentDAO.newInstance(subject, locale);
      content.setName(name);
      content.setDescription(description);
      content.setKeywords(keywords);
      content.setContent(text);

      factory.beginTransaction();
      contentDAO.save(content);
      factory.commit();

      request.setAttribute("currentContent", content);
    } catch (Exception e) {
      if (factory != null) {
        factory.rollback();
      }
      throw e;
    }
    return mapping.findForward("save");
  }

  public ActionForward create(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    return this.save(mapping, rawForm, request, response);
  }

  public ActionForward unspecified(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    return this.save(mapping, rawForm, request, response);
  }

}