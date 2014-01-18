/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.npower.help.action.admin.subjects;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.util.LabelValueBean;

import com.npower.help.action.ActionHelper;
import com.npower.help.action.HelpDAOPersistentManager;
import com.npower.help.core.Subject;
import com.npower.help.dao.HelpDAOFactory;
import com.npower.help.dao.SubjectDAO;

/**
 * MyEclipse Struts Creation date: 07-14-2008
 * 
 * XDoclet definition:
 * 
 * @struts.action validate="true"
 */
public class MoveSubjectAction extends DispatchAction {
  /*
   * Generated Methods
   */

  /**
   * Method move
   * 
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return ActionForward
   */
  public ActionForward move(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    HelpDAOFactory factory = null;
    DynaActionForm form = (DynaActionForm) rawForm;
    try {
      factory = HelpDAOPersistentManager.getManagementBeanFactory(request);

      // Load Subjects Tree
      ActionHelper.loadSubjectsTree(this.getServlet().getServletContext(), request, factory);
      SubjectDAO subjectDAO = factory.getSubjectDAO();
      String subjectsId[] = form.getStrings("move4subjectId");
      List<Subject> moveSubjectList = new ArrayList<Subject>();
      if (subjectsId != null) {
        for (int i = 0; i < subjectsId.length; i++) {
          String subjectId = subjectsId[i];
          Subject subject = subjectDAO.findById(Long.parseLong(subjectId));
          moveSubjectList.add(subject);
        }
        request.setAttribute("moveSubjectList", moveSubjectList);
      }

      List<Object> subjectList = new ArrayList<Object>();
      for (Subject subject : subjectDAO.findAll()) {
        Long subjectId = subject.getSubjectId();
        subjectList.add(new LabelValueBean(subject.getExternalId(), subjectId.toString()));
      }
      request.setAttribute("subjectList", subjectList);
    } catch (Exception e) {
      if (factory != null) {
        factory.rollback();
      }
      throw e;
    }
    return mapping.findForward("move");
  }

  public ActionForward save(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    HelpDAOFactory factory = null;
    DynaActionForm form = (DynaActionForm) rawForm;
    try {
      factory = HelpDAOPersistentManager.getManagementBeanFactory(request);

      // Load Subjects Tree
      ActionHelper.loadSubjectsTree(this.getServlet().getServletContext(), request, factory);
      SubjectDAO subjectDAO = factory.getSubjectDAO();
      String subjectId = form.getString("subjectId");
      String move4subjectId[] = form.getStrings("move4subjectId");

      moveSubject(factory, subjectDAO, subjectId, move4subjectId);

    } catch (Exception e) {
      if (factory != null) {
        factory.rollback();
      }
      throw e;
    }
    return mapping.findForward("success");
  }

  /**
   * @param factory
   * @param subjectDAO
   * @param targetSubjectId
   * @param move4subjectId
   * @throws Exception
   */
  private void moveSubject(HelpDAOFactory factory, SubjectDAO subjectDAO, String targetSubjectId,
      String[] move4subjectId) throws Exception {
    Subject targetSubject = subjectDAO.findById(Long.parseLong(targetSubjectId));
    Set<Subject> subjectSet = targetSubject.getChildren();
    for (int i = 0; i < move4subjectId.length; i++) {
      String moveId = move4subjectId[i];
      Subject move4subject = subjectDAO.findById(Long.parseLong(moveId));
      // isAncestors();���move4subject,�Լ�������ĸ�����Ƿ�ΪtargetSubject
      if (targetSubject.isAncestors(move4subject)) {
        subjectSet.add(move4subject);
        factory.beginTransaction();
        move4subject.setParent(targetSubject);
        subjectDAO.save(move4subject);
        factory.commit();
      } else {
        throw new Exception("move4subject Parent is targetSubject");
      }
    }
    factory.beginTransaction();
    targetSubject.setChildren(subjectSet);
    subjectDAO.save(targetSubject);
    factory.commit();
  }
}