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
import com.npower.dm.core.ProfileAttributeType;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ProfileTemplateBean;

public class AttributeTypeImportAction extends BaseAction {

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

    if (isCancelled(request)) {
      return (mapping.findForward("returnAttributeTypeedit"));
    }

    String xmlfile = (String) request.getSession().getAttribute("filedir");

    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    factory.beginTransaction();
    ProfileTemplateBean templateBean = factory.createProfileTemplateBean();
    List<String> typelist = new ArrayList<String>();

    try {

      InputStream inputType = null;
      InputStream inputType2 = null;
      InputStream inputType3 = null;
      InputStream inputdisplay = null;

      inputType = new FileInputStream(xmlfile);
      inputType2 = new FileInputStream(xmlfile);
      inputType3 = new FileInputStream(xmlfile);
      inputdisplay = new FileInputStream(xmlfile);

      // type parse
      List parseType = templateBean.parseProfileAttributeType(inputType);
      boolean typeboorepeat1 = false;
      String typestrrepeat = "";

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
      }

      // display category
      List atttypelist = templateBean.parseProfileAttributeType(inputdisplay);
      for (Iterator it = atttypelist.iterator(); it.hasNext();) {

        ProfileAttributeType type = (ProfileAttributeType) it.next();
        String typeName = type.getName();
        typelist.add(typeName);
      }
      inputdisplay.close();

      if (typeboorepeat1) {
        ActionMessages errors = new ActionMessages();
        String typeerr[] = typestrrepeat.split("::");
        for (int i = 0; i < typeerr.length; i++) {
          errors.add("repeatTypeErr", new ActionMessage("xml.err.attributetype.file.repeat", typeerr[i]));
        }
        saveErrors(request, errors);
        return mapping.findForward("err");
      } else {
        // import type
        List typeimp = templateBean.parseProfileAttributeType(inputType2);

        if (typeimp.size() > 0) {
          for (Iterator it = typeimp.iterator(); it.hasNext();) {
            ProfileAttributeType type = (ProfileAttributeType) it.next();
            String typeName = type.getName();
            ProfileAttributeType exit = null;
            exit = templateBean.getProfileAttributeTypeByName(typeName);
            if (exit == null) {
              templateBean.importProfileAttributeType(inputType3);
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

      errors.add("otherErr", new ActionMessage("xml.err.attributetype.file", errmessage));
      saveErrors(request, errors);
      return mapping.findForward("err");

    } catch (Exception e) {
      if (factory != null) {
        factory.rollback();
      }

      String errmessage = e.getMessage();
      ActionMessages errors = new ActionMessages();

      errors.add("otherErr", new ActionMessage("xml.err.attributetype.file", errmessage));
      saveErrors(request, errors);
      return mapping.findForward("err");

    } finally {
    }
    
    request.setAttribute("typelist", typelist);
    return mapping.findForward("display");
  }
}
