//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package com.npower.dm.action.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.npower.dm.action.BaseAction;
import com.npower.dm.core.ProfileAttribute;
import com.npower.dm.core.ProfileAttributeType;
import com.npower.dm.core.ProfileConfig;
import com.npower.dm.management.CarrierBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ProfileConfigBean;
import com.npower.dm.management.ProfileTemplateBean;

/**
 * MyEclipse Struts Creation date: 06-13-2006
 * 
 * XDoclet definition:
 * 
 * @struts.action path="/EditProfileConfig" name="ProfileConfigForm"
 *                scope="request"
 * @struts.action-forward name="edit" path="aaaa" contextRelative="true"
 */
public abstract class AbstractProfileAction extends BaseAction {

  // --------------------------------------------------------- Instance
  // Variables

  // --------------------------------------------------------- Methods
  
  protected void loadProfileTemplates(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    ProfileTemplateBean templateBean = factory.createProfileTemplateBean();
    List templates = templateBean.findTemplates("from ProfileTemplateEntity");
    request.setAttribute("profileTemplates", templates);
  }
  
  protected void loadCarriers(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    CarrierBean carrierBean = factory.createCarrierBean();
    List result = carrierBean.getAllCarriers();
    
    request.setAttribute("carriers", result);
  }
  
  protected void loadNapProfiles(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    ProfileConfigBean profileBean = factory.createProfileConfigBean();
    List result = profileBean.findProfileConfigs("from ProfileConfigEntity where profileType='" + ProfileConfig.PROFILE_TYPE_NAP + "'");
    request.setAttribute("napProfiles", result);
  }

  protected void loadProxyProfiles(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    ProfileConfigBean profileBean = factory.createProfileConfigBean();
    List result = profileBean.findProfileConfigs("from ProfileConfigEntity where profileType='" + ProfileConfig.PROFILE_TYPE_PROXY + "'");
    request.setAttribute("proxyProfiles", result);
  }
  
  protected void loadProfile(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    ProfileConfigForm4Save form = (ProfileConfigForm4Save) rawForm;
    String id = (String)form.getValue("ID");
    if (StringUtils.isNotEmpty(id)) {
       ManagementBeanFactory factory = this.getManagementBeanFactory(request);
       ProfileConfigBean configBean = factory.createProfileConfigBean();
       ProfileConfig profile = configBean.getProfileConfigByID(id);
       if (profile != null) {
          form.getMap().putAll(BeanUtils.describe(profile));
          request.setAttribute("profileConfig", profile);
          request.setAttribute("profileTemplate", profile.getProfileTemplate());
          form.setValue("templateID", "" + profile.getProfileTemplate().getID());
          if (profile.getCarrier() != null) {
             form.setValue("carrierID", "" + profile.getCarrier().getID());
          }
          if (profile.getProxyProfile() != null) {
             form.setValue("proxyProfileID", "" + profile.getProxyProfile().getID());
          }
          if (profile.getNAPProfile() != null) {
             form.setValue("napProfileID", "" + profile.getNAPProfile().getID());
          }
          request.setAttribute("attributeValues", profile.getAttributeValues());
          List<ProfileAttribute> attributes = new ArrayList<ProfileAttribute>();
          for (ProfileAttribute attribute: (Set<ProfileAttribute>)profile.getProfileTemplate().getProfileAttributes()) {
              if (ProfileAttributeType.TYPE_APLINK.equals(attribute.getProfileAttribType().getName())
                  || ProfileAttributeType.TYPE_APNAME.equals(attribute.getProfileAttribType().getName())) {
                 // APLink will be ignored
                 continue;
              }
              attributes.add(attribute);
          }
          
          // Pass target into AOP Audit
          request.setAttribute("profile", profile);
          request.setAttribute("attributes", attributes);
       }
    }
    
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
  public abstract ActionForward execute(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception ;

}
