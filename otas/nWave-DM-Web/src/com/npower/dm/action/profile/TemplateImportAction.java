package com.npower.dm.action.profile;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.npower.dm.action.BaseAction;
import com.npower.dm.core.DMException;
import com.npower.dm.core.ProfileAttributeType;
import com.npower.dm.core.ProfileCategory;
import com.npower.dm.core.ProfileTemplate;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ProfileTemplateBean;

public class TemplateImportAction extends BaseAction {

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

    if (isCancelled(request)) {
      return (mapping.findForward("returnTemplateedit"));
    }

    String xmlfile = (String) request.getSession().getAttribute("filedir");

    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    factory.beginTransaction();
    ProfileTemplateBean templateBean = factory.createProfileTemplateBean();

    try {

      InputStream inputCategory = null;
      InputStream inputCategory2 = null;
      InputStream inputType = null;
      InputStream inputType2 = null;
      InputStream inputTemplate = null;
      InputStream importTemplate = null;
      InputStream inputdisplay = null;

      inputCategory = new FileInputStream(xmlfile);
      inputCategory2 = new FileInputStream(xmlfile);
      inputType = new FileInputStream(xmlfile);
      inputType2 = new FileInputStream(xmlfile);
      inputTemplate = new FileInputStream(xmlfile);
      importTemplate = new FileInputStream(xmlfile);
      inputdisplay = new FileInputStream(xmlfile);

      InputStream inputTemplate1 = null;
      InputStream inputTemplate2 = null;
      inputTemplate1 = new FileInputStream(xmlfile);
      inputTemplate2 = new FileInputStream(xmlfile);

      // category parse
      List parseCategory = templateBean.parseCategory(inputCategory);
      boolean categoryboorepeat1 = false;
      String categorystrrepeat = "";
      boolean categoryboono1 = true;
      String categorystrno = "";
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
      } else {

        List parseTemplate1 = templateBean.parseProfileTemplate(inputTemplate1);

        for (Iterator it = parseTemplate1.iterator(); it.hasNext();) {
          ProfileTemplate template1 = (ProfileTemplate) it.next();
          String temCateName = template1.getProfileCategory().getName();
          ProfileCategory exit = null;
          exit = templateBean.getProfileCategoryByName(temCateName);
          boolean categoryboono2 = false;
          if (exit == null) {
            categoryboono2 = false;

          } else {
            categoryboono2 = true;
          }
          categoryboono1 = (categoryboono1 && categoryboono2);
        }
      }

      // type parse
      List parseType = templateBean.parseProfileAttributeType(inputType);
      boolean typeboorepeat1 = false;
      String typestrrepeat = "";
      boolean typeboono1 = true;
      String typestrno = "";

      if (parseType.size() > 0) {

        for (Iterator it = parseType.iterator(); it.hasNext();) {
          ProfileAttributeType type = (ProfileAttributeType) it.next();
          String typeName = type.getName();
          ProfileAttributeType exit = null;
          exit = templateBean.getProfileAttributeTypeByName(typeName);
          boolean typeboorepeat2 = false;
          if (exit == null) {
            typeboorepeat2 = false;
          } else {
            typeboorepeat2 = true;
            typestrrepeat = typestrrepeat + typeName + "::";
          }

          typeboorepeat1 = (typeboorepeat1 || typeboorepeat2);
        }
      } else {

        List parseTemplate2 = templateBean.parseProfileAttributeType(inputTemplate2);

        for (Iterator it = parseTemplate2.iterator(); it.hasNext();) {

          ProfileTemplate template1 = (ProfileTemplate) it.next();
          Set typeset = template1.getProfileAttributes();

          for (Iterator itt = typeset.iterator(); itt.hasNext();) {
            ProfileAttributeType protype = (ProfileAttributeType) itt.next();
            String temTypeName = protype.getName();
            ProfileAttributeType exit = null;
            exit = templateBean.getProfileAttributeTypeByName(temTypeName);
            boolean typeboono2 = false;
            if (exit == null) {
              typeboono2 = false;
              typestrno = typestrno + temTypeName + "::";
            } else {
              typeboono2 = true;
            }
            typeboono1 = (typeboono1 && typeboono2);
          }

        }
      }

      // template parse
      List parseTemplate = templateBean.parseProfileTemplate(inputTemplate);
      boolean temboorepeat1 = false;
      String temstrrepeat = "";
      for (Iterator it = parseTemplate.iterator(); it.hasNext();) {

        ProfileTemplate template = (ProfileTemplate) it.next();
        String templateName = template.getName();
        ProfileTemplate exit = null;
        exit = templateBean.getTemplateByName(templateName);
        boolean temboorepeat2 = true;
        if (exit != null) {
          temboorepeat2 = true;
          temstrrepeat = temstrrepeat + templateName + "::";

        } else {
          temboorepeat2 = false;
        }
        temboorepeat1 = (temboorepeat1 || temboorepeat2);
      }

      if (categoryboorepeat1 || !categoryboono1 || typeboorepeat1 || !typeboono1 || temboorepeat1) {

        ActionMessages errors = new ActionMessages();

        if (categoryboorepeat1) {
          String categoryerr[] = categorystrrepeat.split("::");
          for (int i = 0; i < categoryerr.length; i++) {
            errors.add("repeatCategoryErr", new ActionMessage("xml.err.template.file.repeat.category", categoryerr[i]));
          }

        }
        if (!categoryboono1) {
          String categoryerr[] = categorystrno.split("::");
          for (int i = 0; i < categoryerr.length; i++) {
            errors.add("noCategoryErr", new ActionMessage("xml.err.template.file.no.category", categoryerr[i]));

          }

        }

        if (typeboorepeat1) {
          String typeerr[] = typestrrepeat.split("::");
          for (int i = 0; i < typeerr.length; i++) {
            errors.add("repeatTypeErr", new ActionMessage("xml.err.template.file.repeat.type", typeerr[i]));
          }

        }
        if (!typeboono1) {
          String typeerr[] = typestrno.split("::");
          for (int i = 0; i < typestrno.length(); i++) {
            errors.add("noTypeErr", new ActionMessage("xml.err.template.file.no.type", typeerr[i]));
          }

        }
        if (temboorepeat1) {
          String temerr[] = temstrrepeat.split("::");
          for (int i = 0; i < temerr.length; i++) {
            errors.add("repeatTemplateErr", new ActionMessage("xml.err.template.file.repeat.template", temerr[i]));

          }

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
              templateBean.importCategory(inputCategory2);
            }

          }
        }

        // import type
        List typeimp = templateBean.parseProfileAttributeType(inputType2);

        if (typeimp.size() > 0) {
          for (Iterator it = typeimp.iterator(); it.hasNext();) {
            ProfileAttributeType type = (ProfileAttributeType) it.next();
            String typeName = type.getName();
            ProfileAttributeType exit = null;
            exit = templateBean.getProfileAttributeTypeByName(typeName);
            if (exit == null) {
              templateBean.importProfileAttributeType(inputType2);
            }
          }
        }

        // import template
        int total = templateBean.importProfileTemplates(importTemplate);

        // display tag
        List templates = templateBean.parseProfileTemplate(inputdisplay);
        request.setAttribute("templates", templates);
        inputdisplay.close();

        inputCategory.close();
        inputCategory2.close();
        inputTemplate1.close();
        inputType.close();
        inputType2.close();
        inputTemplate2.close();
        importTemplate.close();

        factory.commit();
      }
    } catch (Exception e) {

      String errmessage = e.getMessage();
      ActionMessages errors = new ActionMessages();

      errors.add("otherErr", new ActionMessage("xml.err.template.file.no.other", errmessage));
      saveErrors(request, errors);
      return mapping.findForward("err");

    } finally {

    }
    return mapping.findForward("display");
  }

}
