package com.npower.dm.action.profile;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.npower.dm.action.BaseAction;
import com.npower.dm.core.DMException;
import com.npower.dm.core.ProfileCategory;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ProfileTemplateBean;

public class CategoryImportAction extends BaseAction {

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

    if (isCancelled(request)) {
      return (mapping.findForward("returnCategoryedit"));
    }

    String xmlfile = (String) request.getSession().getAttribute("filedir");

    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    factory.beginTransaction();
    ProfileTemplateBean templateBean = factory.createProfileTemplateBean();
    List<String> catelist = new ArrayList<String>();

    try {

      InputStream inputCategory = null;
      InputStream inputCategory2 = null;
      InputStream inputCategory3 = null;
      InputStream inputdisplay = null;

      inputCategory = new FileInputStream(xmlfile);
      inputCategory2 = new FileInputStream(xmlfile);
      inputCategory3 = new FileInputStream(xmlfile);
      inputdisplay = new FileInputStream(xmlfile);

      // category parse
      List parseCategory = templateBean.parseCategory(inputCategory);
      boolean categoryboorepeat1 = false;
      String categorystrrepeat = "";

      if (parseCategory.size() > 0) {

        for (Iterator it = parseCategory.iterator(); it.hasNext();) {
          ProfileCategory category = (ProfileCategory) it.next();
          String cateName = category.getName();

          ProfileCategory exit = null;
          exit = templateBean.getProfileCategoryByName(cateName);
          boolean categoryboorepeat2 = false;
          if (exit == null) {
            categoryboorepeat2 = false;
          } else {
            categoryboorepeat2 = true;
            categorystrrepeat = categorystrrepeat + cateName + "::";
          }
          categoryboorepeat1 = (categoryboorepeat1 || categoryboorepeat2);
        }
      }

      // display category
      List categorylist = templateBean.parseCategory(inputdisplay);
      for (Iterator it = categorylist.iterator(); it.hasNext();) {

        ProfileCategory category = (ProfileCategory) it.next();
        String cateName = category.getName();
        catelist.add(cateName);
      }
      inputdisplay.close();

      if (categoryboorepeat1) {
        ActionMessages errors = new ActionMessages();
        String categoryerr[] = categorystrrepeat.split("::");

        for (int i = 0; i < categoryerr.length; i++) {
          errors.add("repeatCategoryErr", new ActionMessage("xml.err.category.file.repeat", categoryerr[i]));
        }
        saveErrors(request, errors);
        return mapping.findForward("err");
      } else {
        // import category
        List cateimport = templateBean.parseCategory(inputCategory2);

        if (cateimport.size() > 0) {

          for (Iterator it = cateimport.iterator(); it.hasNext();) {
            ProfileCategory category = (ProfileCategory) it.next();
            String cateName = category.getName();
            ProfileCategory exit = null;
            exit = templateBean.getProfileCategoryByName(cateName);
            if (exit == null) {
              templateBean.importCategory(inputCategory3);
            }

          }
        }
      }

    } catch (DMException e) {

      if (factory != null) {
        factory.rollback();
      }

      String errmessage = e.getMessage();

      ActionMessages errors = new ActionMessages();
      errors.add("otherErr", new ActionMessage("xml.err.category.file", errmessage));
      saveErrors(request, errors);
      return mapping.findForward("err");

    } catch (Exception e) {

      if (factory != null) {
        factory.rollback();
      }

      String errmessage = e.getMessage();

      ActionMessages errors = new ActionMessages();
      errors.add("otherErr", new ActionMessage("xml.err.category.file", errmessage));
      saveErrors(request, errors);
      return mapping.findForward("err");

    } finally {

    }
    request.setAttribute("catelist", catelist);
    return mapping.findForward("display");
  }
}
