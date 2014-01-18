package com.npower.dm.action.profile;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;

import com.npower.dm.action.BaseAction;
import com.npower.dm.action.ddf.ValidationAction;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Manufacturer;
import com.npower.dm.core.Model;
import com.npower.dm.core.ProfileMapping;
import com.npower.dm.core.ProfileTemplate;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ModelBean;
import com.npower.dm.management.ProfileMappingBean;
import com.npower.dm.management.ProfileTemplateBean;
import com.npower.dm.management.SearchBean;

public class MappingImportAction extends BaseAction {

  private static Log log = LogFactory.getLog(ValidationAction.class);

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

    if (isCancelled(request)) {
      return (mapping.findForward("returnMappingedit"));
    }

    String xmlfile = (String) request.getSession().getAttribute("filedir");
    String manufacturerExternalID = "";
    String modelExternalID = "";
    List<String> template2model = new ArrayList<String>();
    boolean templateBoo = true;
    String temlateErrMess = "";

    boolean manuerr1 = true;
    String manExter = "";
    boolean modelerr1 = true;
    String modelname = "";
    boolean templerr1 = true;
    String templatename = "";

    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    ProfileMappingBean pmBean = factory.createProfileMappingBean();
    ModelBean modelbean = factory.createModelBean();
    SearchBean searchBean = factory.createSearchBean();

    ProfileTemplateBean templateBean = factory.createProfileTemplateBean();

    try {

      InputStream inputparse = null;
      InputStream inputimport = null;
      inputparse = new FileInputStream(xmlfile);
      inputimport = new FileInputStream(xmlfile);

      List parselist = pmBean.parsingProfileMapping(inputparse);
      for (Iterator it = parselist.iterator(); it.hasNext();) {

        ProfileMapping map = (ProfileMapping) it.next();
        manExter = map.getManufacturerExternalID();
        modelname = map.getModelExternalID();
        String templateerrmoname = map.getModelExternalID();

        Manufacturer manufacturer = modelbean.getManufacturerByExternalID(manExter);
        boolean manuerr2 = false;
        if (manufacturer == null) {

          manuerr2 = false;
          manExter = manExter + manufacturer.getName() + "::";

        } else {
          manuerr2 = true;
        }
        manuerr1 = (manuerr1 && manuerr2);

        Criteria criteria = searchBean.newCriteriaInstance(Model.class);
        criteria.add(Expression.eq("name", modelname));

        List result = modelbean.findModel(criteria);
        boolean modelerr2 = false;
        if (result == null) {

          modelerr2 = false;
          modelname = modelname + modelname + "::";

        } else {
          modelerr2 = true;
        }
        modelerr1 = (modelerr1 && modelerr2);

        if (map.getProfileTemplate() == null) {
          templateBoo = false;
          temlateErrMess = temlateErrMess + templateerrmoname + "::";
        } else {
          templateBoo = true;
        }

        templatename = map.getProfileTemplate().getName();

        ProfileTemplate template = templateBean.getTemplateByName(templatename);
        boolean templerr2 = false;
        if (template == null) {
          templerr2 = false;

          templatename = templatename + template.getName() + "::";

        } else {
          templerr2 = true;
        }
        templerr1 = (templerr1 && templerr2);
      }

      inputparse.close();

      if (!manuerr1 || !modelerr1 || !templerr1) {
        ActionMessages errors = new ActionMessages();
        if (!manuerr1) {

          String manumess[] = manExter.split("::");
          for (int i = 0; i < manumess.length; i++) {
            errors.add("noManufacturerErr", new ActionMessage("xml.err.mapping.file.no.manufacturer", manumess[i]));
          }
        }

        if (!modelerr1) {
          String modelnamemess[] = modelname.split("::");
          for (int i = 0; i < modelnamemess.length; i++) {
            errors.add("noModelErr", new ActionMessage("xml.err.mapping.file.no.model", modelnamemess[i]));
          }
        }

        if (!templerr1) {
          String messtemp[] = templatename.split("::");
          for (int i = 0; i < messtemp.length; i++) {
            errors.add("noTemplateErr", new ActionMessage("xml.err.mapping.file.no.template", messtemp[i]));
          }
        }

        saveErrors(request, errors);
        return mapping.findForward("err");
      }

      // import mapping
      List mappings = pmBean.importProfileMapping(inputimport);

      ModelBean modelBean = factory.createModelBean();

      factory.beginTransaction();
      for (int i = 0; i < mappings.size(); i++) {
        ProfileMapping map = (ProfileMapping) mappings.get(i);

        modelExternalID = map.getModelExternalID();
        manufacturerExternalID = map.getManufacturerExternalID();

        Manufacturer manufacturer = modelBean.getManufacturerByExternalID(manufacturerExternalID);
        Model model = modelBean.getModelByManufacturerModelID(manufacturer, modelExternalID);

        modelBean.attachProfileMapping(model, map.getID());

        String mName = map.getModelExternalID();
        String tName = map.getProfileTemplate().getName();

        template2model.add(mName + " ---------- mapping ---------- " + tName);
        
      }
      factory.commit();
      inputimport.close();
    } catch (DMException e) {
      if (factory != null) {
        factory.rollback();
      }

      log.error(e.getMessage(), e);

      ActionMessages errors = new ActionMessages();

      String othererr = e.getMessage();

      if (!templateBoo) {

        String messtemp[] = temlateErrMess.split("::");

        for (int i = 0; i < messtemp.length; i++) {
          String errmessage = messtemp[i] + " do not have the match template";
          errors.add("otherErr", new ActionMessage("xml.err.mapping.file.no.other", errmessage));
        }
      } else {
        errors.add("otherErr", new ActionMessage("xml.err.mapping.file.no.other", othererr));
      }

      saveErrors(request, errors);
      return mapping.findForward("err");

    } catch (Exception e) {
      if (factory != null) {
        factory.rollback();
      }

      log.error(e.getMessage(), e);

      ActionMessages errors = new ActionMessages();

      String othererr = e.getMessage();

      if (!templateBoo) {

        String messtemp[] = temlateErrMess.split("::");

        for (int i = 0; i < messtemp.length; i++) {
          String errmessage = messtemp[i] + " do not have the match template";
          errors.add("otherErr", new ActionMessage("xml.err.mapping.file.no.other", errmessage));
        }

      } else {
        errors.add("otherErr", new ActionMessage("xml.err.mapping.file.no.other", othererr));
      }
      saveErrors(request, errors);
      return mapping.findForward("err");

    } finally {
    }

    request.setAttribute("template2model", template2model);
    return mapping.findForward("display");

  }
}
