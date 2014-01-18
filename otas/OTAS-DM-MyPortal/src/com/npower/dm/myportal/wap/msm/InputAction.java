/**
  * $Header: /home/master/OTAS-DM-MyPortal/src/com/npower/dm/myportal/wap/msm/InputAction.java,v 1.9 2008/06/05 10:34:39 zhao Exp $
  * $Revision: 1.9 $
  * $Date: 2008/06/05 10:34:39 $
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
package com.npower.dm.myportal.wap.msm;

import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import sync4j.framework.config.ConfigurationConstants;

import com.npower.dm.action.PersistentManager;
import com.npower.dm.core.Carrier;
import com.npower.dm.core.Country;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Manufacturer;
import com.npower.dm.core.Model;
import com.npower.dm.core.Software;
import com.npower.dm.core.SoftwarePackage;
import com.npower.dm.decorator.DecoratorHelper;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ModelBean;
import com.npower.dm.management.SoftwareBean;
import com.npower.dm.msm.SoftwareManagementJobAdapter;
import com.npower.dm.msm.SoftwareManagementJobAdapterImpl;
import com.npower.dm.myportal.ActionHelper4MyPortal;
import com.npower.dm.myportal.BaseWizardAction;
import com.npower.dm.myportal.cp.ClientProvWizardForm;
import com.npower.dm.util.ConfigHelper;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.9 $ $Date: 2008/06/05 10:34:39 $
 */
public class InputAction extends BaseWizardAction {
  
  
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
    
    // Get Software
    Software software = this.getSoftware(factory, form, request);
    if (software == null) {
       // 返回选择软件页面
       return this.prev(mapping, rawForm, request, res);
    }
    request.setAttribute("software", software);

    // Get Model
    String manullySelectModel = form.getString("manullySelectModel");
    Model model = this.getModel(factory, form, request);
    if (model == null) {
       if (StringUtils.isEmpty(manullySelectModel) || manullySelectModel.equalsIgnoreCase("false")) {
          model = getModelByAutoDetected(factory, request);
          request.setAttribute("isAutomaticDetected", new Boolean(true));
       }
    } else {
      request.setAttribute("isAutomaticDetected", new Boolean(false));
    }
    
    String phoneNumber = getPhoneNumber(factory, request, form);
    request.setAttribute("phoneNumber", phoneNumber);

    SoftwareBean bean = factory.createSoftwareBean();
    if (model != null) {
       // 检查用户选择的型号是否支持软件管理
       SoftwareManagementJobAdapter msmAdapter = new SoftwareManagementJobAdapterImpl(factory);
       if (msmAdapter.isSupported(model)) {
          request.setAttribute("supported_msm", new Boolean(true));
       } else {
         request.setAttribute("supported_msm", new Boolean(false));
       }
       
       // 获取Download URL
       String serverDownlaodURIPattern = ConfigHelper.getSecurityProperties().getProperty(ConfigurationConstants.CFG_SERVER_SOFTWARE_DOWNLOAD_URI);
       String downloadURL = bean.getSoftwareDownloadURL(software, model, serverDownlaodURIPattern);
       if (downloadURL != null) {
          request.setAttribute("downloadURL", downloadURL);
       }

       request.setAttribute("model", model);
       // Decorate for I18n
       request.setAttribute("manufacturer", DecoratorHelper.decorate(model.getManufacturer(), this.getLocale(request)));
    } else {
      Manufacturer manufacturer = this.getManufacturer(factory, form, request);
      if (manufacturer != null) {
        request.setAttribute("manufacturer", DecoratorHelper.decorate(manufacturer, this.getLocale(request)));
        Set<Model> models = new TreeSet<Model>();
        for (SoftwarePackage pkg: software.getPackages()) {
            Set<Model> targetModels = bean.getTargetModels(pkg);
            for (Model m: targetModels) {
                if (manufacturer.getID() == m.getManufacturer().getID()) {
                   models.add(m);
                }
            }
        }
        request.setAttribute("models", models);
      } else {
        // 返回型号选择页面的厂商列表
        Set<Manufacturer> manufacturers = new TreeSet<Manufacturer>();
        for (SoftwarePackage pkg: software.getPackages()) {
            Set<Model> targetModels = bean.getTargetModels(pkg);
            for (Model m: targetModels) {
              manufacturers.add(m.getManufacturer());
            }
        }
        
        // 返回型号选择页面的厂商列表
        request.setAttribute("manufacturers", DecoratorHelper.decorateManufacturer(manufacturers, this.getLocale(request)));
        // 返回型号选择页面的主流厂商列表
        request.setAttribute("specialManufacturers", getSpecialManufacturers(factory, request));
      }
    }

    return (mapping.findForward("view"));
  }

  /**
   * 自动检测手机型号.
   * @param factory
   * @param request
   * @return
   * @throws DMException
   */
  private Model getModelByAutoDetected(ManagementBeanFactory factory, HttpServletRequest request) throws DMException {
    //String uaInfo = request.getHeader("User-Agent");
    //String uaInfo = "Mozilla/5.0 (SymbianOS/9.2; U; Series60/3.1 Nokia6120c/3.83; Profile/MIDP-2.0 Configuration/CLDC-1.1 ) AppleWebKit/413 (KHTML, like Gecko) Safari/413";
    //String uaInfo = "Mozilla/5.0 (SymbianOS/9.2; U; Series60/3.1 Nokia6120c/3.46; Profile/MIDP-2.0 Configuration/CLDC-1.1 ) AppleWebKit/413 (KHTML, like Gecko) Safari/413";
    String uaInfo = ActionHelper4MyPortal.getUA(request);
    ModelBean modelBean = factory.createModelBean();
    Model model = null;
    if (StringUtils.isNotEmpty(uaInfo)) {
       model = modelBean.getModelFromUA(uaInfo);
    }
    return model;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.myportal.cp.BaseWizardAction#next(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
   */
  public ActionForward next(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse res) throws Exception {
    return (mapping.findForward("next4dm"));
  }

  /* (non-Javadoc)
   * @see com.npower.dm.myportal.cp.BaseWizardAction#prev(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
   */
  public ActionForward prev(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse res) throws Exception {
    return (mapping.findForward("prev"));
  }
  
}
