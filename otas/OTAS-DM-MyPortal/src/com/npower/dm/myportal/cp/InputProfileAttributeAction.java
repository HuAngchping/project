/**
  * $Header: /home/master/OTAS-DM-MyPortal/src/com/npower/dm/myportal/cp/InputProfileAttributeAction.java,v 1.10 2008/04/15 10:18:21 zhao Exp $
  * $Revision: 1.10 $
  * $Date: 2008/04/15 10:18:21 $
  *
  * ===============================================================================================
  * License, Version 1.1
  *
  * Copyright (c) 1994-2007 NPower Network Software Ltd.  All rights reserved.
  *
  * This SOURCE CODE FILE, which has been provided by NPower as part
  * of a NPower product for use ONLY by licensed users of the product,
  * includes CONFIDENTIAL and PROPRIETARY information of NPower.
  *
  * USE OF THIS SOFTWARE IS GOVERNED BY THE TERMS AND CONDITIONS
  * OF THE LICENSE STATEMENT AND LIMITED WARRANTY FURNISHED WITH
  * THE PRODUCT.
  *
  * IN PARTICULAR, YOU WILL INDEMNIFY AND HOLD NPower, ITS RELATED
  * COMPANIES AND ITS SUPPLIERS, HARMLESS FROM AND AGAINST ANY CLAIMS
  * OR LIABILITIES ARISING OUT OF THE USE, REPRODUCTION, OR DISTRIBUTION
  * OF YOUR PROGRAMS, INCLUDING ANY CLAIMS OR LIABILITIES ARISING OUT OF
  * OR RESULTING FROM THE USE, MODIFICATION, OR DISTRIBUTION OF PROGRAMS
  * OR FILES CREATED FROM, BASED ON, AND/OR DERIVED FROM THIS SOURCE
  * CODE FILE.
  * ===============================================================================================
  */
package com.npower.dm.myportal.cp;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.npower.dm.action.PersistentManager;
import com.npower.dm.core.Carrier;
import com.npower.dm.core.Country;
import com.npower.dm.core.Model;
import com.npower.dm.core.ProfileAttribute;
import com.npower.dm.core.ProfileAttributeType;
import com.npower.dm.core.ProfileCategory;
import com.npower.dm.core.ProfileConfig;
import com.npower.dm.core.ProfileTemplate;
import com.npower.dm.decorator.DecoratorHelper;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.myportal.BaseWizardAction;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.10 $ $Date: 2008/04/15 10:18:21 $
 */
public class InputProfileAttributeAction extends BaseWizardAction {
  
  /* (non-Javadoc)
   * @see org.apache.struts.action.Action#execute(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
   */
  public ActionForward view(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse res) throws Exception {
    ClientProvWizardForm form = (ClientProvWizardForm)rawForm;

    ManagementBeanFactory factory = PersistentManager.getManagementBeanFactory(request);
    // Get Country
    Country country = getCountry(factory, form, request);
    if (country == null) {
       // Missing country
       return mapping.getInputForward();
    }
    request.setAttribute("country", DecoratorHelper.decorate(country, this.getLocale(request)));

    // Get Carrier
    Carrier carrier = this.getCarrier(factory, form, request);
    if (carrier == null) {
       return mapping.getInputForward();
    }
    request.setAttribute("carrier", carrier);

    // Get Model
    Model model = getModel(factory, form, request);
    if (model == null) {
       return mapping.getInputForward();
    }
    request.setAttribute("model", model);
    request.setAttribute("manufacturer", DecoratorHelper.decorate(model.getManufacturer(), this.getLocale(request)));

    // Get ProfileCategory
    ProfileCategory category = this.getProfileCategory(factory, form, request);
    if (category == null) {
       return mapping.getInputForward();
    }
    request.setAttribute("category",  DecoratorHelper.decorate(category, this.getLocale(request)));
    
    // Get Profile
    ProfileConfig profile = getProfile(factory, form, request);
    if (profile == null) {
       return mapping.getInputForward();
    }
    request.setAttribute("profile", profile);
    
    List<ProfileAttribute> attributes = new ArrayList<ProfileAttribute>();
    ProfileTemplate template = profile.getProfileTemplate();
    Object advanceProfileParameterMode = form.getValue("advanceProfileParameterMode");
    for (ProfileAttribute attribute: template.getProfileAttributes()) {
        if (ProfileAttributeType.TYPE_APLINK.equals(attribute.getProfileAttribType().getName())
            || ProfileAttributeType.TYPE_APNAME.equals(attribute.getProfileAttribType().getName())) {
           // APLink will be ignored
           continue;
        }
        if (advanceProfileParameterMode == null && !attribute.getIsUserAttribute()) {
           continue;
        }
        attributes.add(attribute);
    }
    request.setAttribute("attributes", DecoratorHelper.decorateProfileAttribute(attributes, this.getLocale(request)));
    return (mapping.findForward("view"));
  }

  public ActionForward next(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse res) throws Exception {
    ClientProvWizardForm form = (ClientProvWizardForm)rawForm;
    ManagementBeanFactory factory = PersistentManager.getManagementBeanFactory(request);
    // Get Profile
    ProfileConfig profile = getProfile(factory, form, request);
    if (profile == null) {
       return mapping.getInputForward();
    }
    
    // Create Action messages to hold validation information
    ActionMessages errors = new ActionMessages();
    Locale locale = this.getLocale(request);
    
    ProfileTemplate template = profile.getProfileTemplate();
    for (ProfileAttribute decoratee: template.getProfileAttributes()) {
        ProfileAttribute attribute = DecoratorHelper.decorate(decoratee, locale);
        long id = attribute.getID();
        // 检查必须输入的属性值是否输入
        if (attribute.getIsRequired() && attribute.getIsUserAttribute()) {
           String value = form.getString("attribute__" + id + "__value");
           if (StringUtils.isEmpty(value)) {
              String label = attribute.getDisplayName(); 
              errors.add("attribute__" + id + "__value", new ActionMessage("errors.required", label));
           }
        }
        // 检查两次口令是否一致
        if (ProfileAttributeType.TYPE_PASSWORD.equals(attribute.getProfileAttribType().getName())) {
           String value = form.getString("attribute__" + id + "__value");
           String valueAgain = form.getString("attribute__" + id + "__value__confirm");
           if (StringUtils.isEmpty(value) && StringUtils.isEmpty(valueAgain)) {
              continue;
           } else if (!value.equals(valueAgain)) {
             String label = attribute.getDisplayName(); 
             errors.add("attribute__" + id + "__value", new ActionMessage("errors.matched", label));
           }
        }
        
        // 检查Email类型的属性值
        if (ProfileAttributeType.TYPE_EMAIL.equals(attribute.getProfileAttribType().getName())) {
           String value = form.getString("attribute__" + id + "__value");
           if (StringUtils.isEmpty(value)) {
              continue;
           }
           //Set the email pattern string
           Pattern p = Pattern.compile(".+@.+\\.[a-z]+");

           //Match the given string with the pattern
           Matcher m = p.matcher(value);

           //check whether match is found 
           if (!m.matches()) {
              String label = attribute.getDisplayName(); 
              errors.add("attribute__" + id + "__value", new ActionMessage("errors.email", label));
           }
        }        
    }    
    if (!errors.isEmpty()) {
       saveErrors(request, errors);
       return this.unspecified(mapping, rawForm, request, res);
    } else {
      return (mapping.findForward("next"));
    }
  }
  
  public ActionForward prev(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse res) throws Exception {
    return (mapping.findForward("prev"));
  }
  
}
