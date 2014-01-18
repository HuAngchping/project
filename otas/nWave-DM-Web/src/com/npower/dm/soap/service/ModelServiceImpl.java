package com.npower.dm.soap.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.npower.dm.core.Manufacturer;
import com.npower.dm.core.Model;
import com.npower.dm.decorator.DecoratorHelper;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ModelBean;
import com.npower.dm.server.engine.EngineConfig;
import com.npower.dm.soap.common.ManufacturerWS;
import com.npower.dm.soap.common.ModelService;
import com.npower.dm.soap.common.ModelWS;

public class ModelServiceImpl extends BaseService implements ModelService {

  private static Log log = LogFactory.getLog(ModelServiceImpl.class);

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.soap.IModelService#getManufacturers()
   */
  public List<ManufacturerWS> getManufacturers() {
    ManagementBeanFactory factory = null;
    List<ManufacturerWS> result = new ArrayList<ManufacturerWS>();
    try {
        factory = ManagementBeanFactory.newInstance(EngineConfig.getProperties());
        ModelBean modelBean = factory.createModelBean();
        Set<ManufacturerWS> rs = new TreeSet<ManufacturerWS>();
        for (Manufacturer manufacturer: modelBean.getAllManufacturers()) {
            ManufacturerWS m = new ManufacturerWS();
            m.setExternalID(manufacturer.getExternalId());
            m.setName(manufacturer.getName());
            m.setDescription(manufacturer.getDescription());
            rs.add(m);
        }
        result = new ArrayList<ManufacturerWS>(rs);
    } catch (Exception ex) {
      log.fatal("Failure in SOAP Service.", ex);
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
    return result;
  }

  /**
   * get decorated manufacturers
   * @param locale
   * @return
   */
  public List<ManufacturerWS> getDecoratorManufacturers(String language, String country) {
    ManagementBeanFactory factory = null;
    List<ManufacturerWS> result = new ArrayList<ManufacturerWS>();
    try {
        factory = ManagementBeanFactory.newInstance(EngineConfig.getProperties());
        ModelBean modelBean = factory.createModelBean();
        Set<ManufacturerWS> rs = new TreeSet<ManufacturerWS>();
        for (Manufacturer manufacturer: modelBean.getAllManufacturers()) {
            ManufacturerWS m = new ManufacturerWS();
            Locale locale = new Locale(language,country);
            Manufacturer decoratee = DecoratorHelper.decorate(manufacturer, locale );
            m.setName(manufacturer.getName());
            m.setExternalID(decoratee.getExternalId());
            m.setDescription(decoratee.getDescription());
            m.setDisplayName(decoratee.getName());
            rs.add(m);
        }
        result = new ArrayList<ManufacturerWS>(rs);
    } catch (Exception ex) {
      log.fatal("Failure in SOAP Service.", ex);
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
    return result;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.soap.IModelService#getModels(java.lang.String)
   */
  public List<ModelWS> getModels(String manufacturerExternalID) {
    ManagementBeanFactory factory = null;
    List<ModelWS> result = new ArrayList<ModelWS>();
    try {
        factory = ManagementBeanFactory.newInstance(EngineConfig.getProperties());
        ModelBean modelBean = factory.createModelBean();
        Manufacturer manufacturer = modelBean.getManufacturerByExternalID(manufacturerExternalID);
        if (manufacturer == null) {
           return result;
        }
        Set<Model> models = manufacturer.getModels();
        Set<ModelWS> rs = new TreeSet<ModelWS>();
        for (Model model: models) {
            ModelWS m = new ModelWS();
            m.setExternalID(model.getManufacturerModelId());
            m.setDescription(model.getDescription());
            m.setManufacturerExternalID(manufacturer.getExternalId());
            m.setName(model.getName());
            m.setOmaDmEnabled(model.getIsOmaDmEnabled());
            m.setServerAuthType(model.getServerAuthType());
            m.setSupportedDownloadMethods(model.getSupportedDownloadMethods());
            m.setIconImage(model.getIconBinary());
            rs.add(m);
        }
        result = new ArrayList<ModelWS>(rs);
    } catch (Exception ex) {
      log.fatal("Failure in SOAP Service.", ex);
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
    return result;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.soap.common.ModelService#getModel(java.lang.String)
   */
  public ModelWS getModel(String modelID , String manufacturerExternalID) {
    ManagementBeanFactory factory = null;
    ModelWS result = null;
    try {
        factory = ManagementBeanFactory.newInstance(EngineConfig.getProperties());
        ModelBean modelBean = factory.createModelBean();
        Manufacturer manufacturer = modelBean.getManufacturerByExternalID(manufacturerExternalID);
        if(manufacturer == null){
          return null;
        }
        Model model = modelBean.getModelByManufacturerModelID(manufacturer , modelID);
        if(model == null){
          return null;
        }
        result = new ModelWS();
        result.setExternalID(modelID);
        result.setManufacturerExternalID(manufacturerExternalID);
        result.setName(model.getName());
        result.setDescription(model.getDescription());
        result.setOmaDmEnabled(model.getIsOmaDmEnabled());
        result.setServerAuthType(model.getServerAuthType());
        result.setSupportedDownloadMethods(model.getSupportedDownloadMethods());
        result.setIconImage(model.getIconBinary());        
    } catch (Exception ex) {
      log.fatal("Failure in SOAP Service.", ex);
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
    return result;
  }

}