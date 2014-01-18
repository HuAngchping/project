/**
  * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/action/model/ModelMatrixItem.java,v 1.1 2007/12/27 05:41:47 zhao Exp $
  * $Revision: 1.1 $
  * $Date: 2007/12/27 05:41:47 $
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
package com.npower.dm.action.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.npower.dm.core.ClientProvTemplate;
import com.npower.dm.core.Model;
import com.npower.dm.core.ProfileMapping;
import com.npower.dm.management.ManagementBeanFactory;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2007/12/27 05:41:47 $
 */
public class ModelMatrixItem {
  private Model model;
  
  // Hold capabilities information
  private Map<String, Boolean> omaCPCapabilities = new HashMap<String, Boolean>();
  private Map<String, Boolean> omaDMCapabilities = new HashMap<String, Boolean>();
  private Map<String, Boolean> nokiaOtaCapabilities = new HashMap<String, Boolean>();
  
  /**
   * 
   */
  private ModelMatrixItem() {
    super();
  }

  /**
   * Return a ModelMatrixItem based on Model
   * @param model
   * @return
   */
  public static ModelMatrixItem getModelMatrixItem(ManagementBeanFactory factory, Model model) {
    ModelMatrixItem item = new ModelMatrixItem();
    item.setModel(model);
    Set<ClientProvTemplate> cpTemplates = model.getClientProvTemplates();
    for (ClientProvTemplate cpTemplate: cpTemplates) {
        String encoder = cpTemplate.getEncoder();
        if (ClientProvTemplate.OMA_CP_1_1_ENCODER.equalsIgnoreCase(encoder)) {
           item.getOmaCPCapabilities().put(cpTemplate.getProfileCategory().getName(), new Boolean(true));
        } else {
          item.getNokiaOtaCapabilities().put(cpTemplate.getProfileCategory().getName(), new Boolean(true));
        }
    }
    List<ProfileMapping> dmMappings = model.getProfileMappings();
    for (ProfileMapping dmMapping: dmMappings) {
        item.getOmaDMCapabilities().put(dmMapping.getProfileTemplate().getProfileCategory().getName(), new Boolean(true));
    }
    return item;
  }

  /**
   * @return the model
   */
  public Model getModel() {
    return model;
  }
  /**
   * @param model the model to set
   */
  public void setModel(Model model) {
    this.model = model;
  }
  /**
   * @return the omaCPCapabilities
   */
  public Map<String, Boolean> getOmaCPCapabilities() {
    return omaCPCapabilities;
  }
  /**
   * @param omaCPCapabilities the omaCPCapabilities to set
   */
  public void setOmaCPCapabilities(Map<String, Boolean> omaCPCapabilities) {
    this.omaCPCapabilities = omaCPCapabilities;
  }
  /**
   * @return the omaDMCapabilities
   */
  public Map<String, Boolean> getOmaDMCapabilities() {
    return omaDMCapabilities;
  }
  /**
   * @param omaDMCapabilities the omaDMCapabilities to set
   */
  public void setOmaDMCapabilities(Map<String, Boolean> omaDMCapabilities) {
    this.omaDMCapabilities = omaDMCapabilities;
  }
  /**
   * @return the nokiaOtaCapabilities
   */
  public Map<String, Boolean> getNokiaOtaCapabilities() {
    return nokiaOtaCapabilities;
  }
  /**
   * @param nokiaOtaCapabilities the nokiaOtaCapabilities to set
   */
  public void setNokiaOtaCapabilities(Map<String, Boolean> nokiaOtaDMCapabilities) {
    this.nokiaOtaCapabilities = nokiaOtaDMCapabilities;
  }
}
