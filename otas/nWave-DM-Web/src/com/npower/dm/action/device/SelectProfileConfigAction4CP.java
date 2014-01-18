//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package com.npower.dm.action.device;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;

import com.npower.dm.core.Carrier;
import com.npower.dm.core.Device;
import com.npower.dm.core.Model;
import com.npower.dm.core.ProfileCategory;
import com.npower.dm.core.ProfileTemplate;
import com.npower.dm.hibernate.entity.ProfileConfigEntity;
import com.npower.dm.management.CarrierBean;
import com.npower.dm.management.DeviceBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ProfileTemplateBean;
import com.npower.dm.management.SearchBean;

/**
 * MyEclipse Struts Creation date: 06-15-2006
 * 
 * XDoclet definition:
 * 
 * @struts.action path="/device/job/SelectProfileConfig"
 *                name="DeviceJobTypeForm" scope="request"
 * @struts.action-forward name="display"
 *                        path="/jsp/device/job/assign/profileconfigs.jsp"
 *                        contextRelative="true"
 */
public class SelectProfileConfigAction4CP extends BaseSelectProfileConfigAction {

  // --------------------------------------------------------- Instance
  // Variables

  protected ActionForward loadProfiles(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {

    ProfileAssignmentForm4Save form = (ProfileAssignmentForm4Save) rawForm;
    String searchProfileConfigName = (String)form.getValue("searchProfileConfigName");
    String searchProfileTemplateID = (String)form.getValue("searchProfileTemplateID");
    String searchProfileCategoryID = (String)form.getValue("searchProfileCategoryID");
    String searchProfileCarrierID = (String)form.getValue("searchProfileCarrierID");
    
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    ProfileTemplateBean templateBean = factory.createProfileTemplateBean();
    CarrierBean carrierBean = factory.createCarrierBean();
    
    DeviceBean deviceBean = factory.createDeviceBean();
    String deviceID = (String)form.getValue("deviceID");
    if (StringUtils.isEmpty(deviceID)) {
       deviceID = request.getParameter("deviceID");
    }
    Device device = deviceBean.getDeviceByID(deviceID);
    Model model = device.getModel();
    
    SearchBean searchBean = factory.createSearchBean();
    Criteria criteria = searchBean.newCriteriaInstance(ProfileConfigEntity.class);
    criteria.add(Expression.eq("isUserProfile", new Boolean(true)));
    
    Criteria templateCriteria = criteria.createCriteria("profileTemplate");
    Criteria profileMappingCriteria = templateCriteria.createCriteria("profileMappings");
    Criteria modelCriteria = profileMappingCriteria.createCriteria("modelProfileMaps");
    modelCriteria.add(Expression.eq("model", model));
    
    if (StringUtils.isNotEmpty(searchProfileCategoryID)) {
       ProfileCategory category = templateBean.getProfileCategoryByID(searchProfileCategoryID);
       if (category != null) {
          templateCriteria.add(Expression.eq("profileCategory", category));
       }
    }
   
    if (StringUtils.isNotEmpty(searchProfileTemplateID)) {
       ProfileTemplate template = templateBean.getTemplateByID(searchProfileTemplateID);
       if (template != null) {
          criteria.add(Expression.eq("profileTemplate", template));
       }
    }
  
    if (StringUtils.isNotEmpty(searchProfileConfigName)) {
       criteria.add(Expression.ilike("name", searchProfileConfigName, MatchMode.ANYWHERE));
    }

    if (StringUtils.isNotEmpty(searchProfileCarrierID)) {
      Carrier carrier = carrierBean.getCarrierByID(searchProfileCarrierID);
      criteria.add(Expression.eq("carrier", carrier));
    }

    criteria.addOrder(Order.asc("carrier"));
    templateCriteria.addOrder(Order.asc("profileCategory"));
    criteria.addOrder(Order.asc("name"));
    
    List result = criteria.list();
    request.setAttribute("results", result);
    return null;
  }

}
