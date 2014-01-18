//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package com.npower.dm.action.device;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.navigator.menu.MenuComponent;
import net.sf.navigator.menu.MenuRepository;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

import com.npower.dm.action.BaseDispatchAction;
import com.npower.dm.core.DDFNode;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Device;
import com.npower.dm.core.DeviceTree;
import com.npower.dm.core.DeviceTreeNode;
import com.npower.dm.management.DeviceBean;
import com.npower.dm.management.ManagementBeanFactory;

/**
 */
public class DeviceTreeAction extends BaseDispatchAction {
  
  private static String nodeAction = "/device/DeviceTree";

  // --------------------------------------------------------- Instance
  // Variables
  
  private void convert(String modelID, Set<DeviceTreeNode> nodes, MenuRepository repository, MenuComponent parentMenu) {
    for (DeviceTreeNode node: nodes) {
        MenuComponent menu = new MenuComponent();
        menu.setName("ID" + node.getID());
        String title = node.getName();
        title = (title == null)?DDFNode.NAME_OF_DYNAMIC_NODE:title;
        menu.setTitle(title);
        menu.setParent(parentMenu);

        menu.setTarget("_blank");
        menu.setAction(nodeAction + "?deviceID=" + modelID + "&nodeID=" + node.getID());
        repository.addMenu(menu);
        Set<DeviceTreeNode> children = node.getChildren();
        if (!children.isEmpty()) {
           convert(modelID, children, repository, menu);
        }
    }
    
  }
  
  private MenuRepository loadDeviceTree(HttpServletRequest request, String deviceID) throws DMException {
    MenuRepository repository = new MenuRepository();
    //  Get the repository from the application scope - and copy the
    //  DisplayerMappings from it.
    MenuRepository defaultRepository = (MenuRepository)this.getServlet().getServletContext().getAttribute(MenuRepository.MENU_REPOSITORY_KEY);
    repository.setDisplayers(defaultRepository.getDisplayers());
    MenuComponent rootMenu = new MenuComponent();
    rootMenu.setName("root");
    repository.addMenu(rootMenu);
    
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    DeviceBean deviceBean = factory.createDeviceBean();
    Device device = deviceBean.getDeviceByID(deviceID);
    
    request.setAttribute("device", device);

    DeviceTree tree = device.getDeviceTree();

    if (tree != null) {
       DeviceTreeNode node = tree.getRootNode();
       rootMenu.setTitle(node.getName());
    
       convert(deviceID, node.getChildren(), repository, rootMenu);
    } else {
      // Device Tree is empty
      rootMenu.setTitle("./");
    }
    return repository;
  }
  
  private DeviceTreeNode loadDeviceTreeNode(HttpServletRequest request, String nodeID) throws DMException {
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    DeviceBean deviceBean = factory.createDeviceBean();
    DeviceTreeNode node = deviceBean.getDeviceTreeNodeByID(nodeID);
    return node;
  }

  // --------------------------------------------------------- Methods

  /**
   * Display mode
   * 
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return ActionForward
   */
  public ActionForward display(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {

    DynaValidatorForm form = (DynaValidatorForm) rawForm;
    String deviceID = form.getString("deviceID");
    if (deviceID == null || deviceID.trim().length() == 0) {
       throw new DMException("Please specified a device!");
    }
    
    // Load data for Tree
    MenuRepository repository = this.loadDeviceTree(request, deviceID);
    request.setAttribute("deviceTreeRepository", repository);

    // Load data for nodes
    String nodeID = (String)form.get("nodeID");
    if (nodeID != null && nodeID.trim().length() > 0) {
       DeviceTreeNode currentNode = this.loadDeviceTreeNode(request, nodeID);
       request.setAttribute("deviceTreeNode", currentNode);
       request.setAttribute("children", currentNode.getChildren());
    } else {
      ManagementBeanFactory factory = this.getManagementBeanFactory(request);
      DeviceBean deviceBean = factory.createDeviceBean();
      Device device = deviceBean.getDeviceByID(deviceID);
      DeviceTree tree = device.getDeviceTree();
      if (tree != null) {
         DeviceTreeNode rootNode = tree.getRootNode();
         request.setAttribute("deviceTreeNode", rootNode);
         request.setAttribute("children", rootNode.getChildren());
      }
      
    }
    
    return (mapping.findForward("success"));
     
  }
  
  /**
   * Default mode
   * 
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return ActionForward
   */
  public ActionForward unspecified(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    return this.display(mapping, rawForm, request, response);
  }  

  /**
   * Clean all of device tree nodes
   * 
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return ActionForward
   */
  public ActionForward cleanup(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    
    DynaValidatorForm form = (DynaValidatorForm) rawForm;
    ManagementBeanFactory factory = null;
    try {
        String deviceID = form.getString("deviceID");
        if (deviceID == null || deviceID.trim().length() == 0) {
           throw new DMException("Please specified a device!");
        }
        factory = this.getManagementBeanFactory(request);
        DeviceBean deviceBean = factory.createDeviceBean();
        Device device = deviceBean.getDeviceByID(deviceID);
        DeviceTree tree = device.getDeviceTree();
        if (tree != null) {
           factory.beginTransaction();
           deviceBean.delete(tree);
           factory.commit();
        }
        
        return this.display(mapping, rawForm, request, response);
    } catch (Exception ex) {
      if (factory != null) {
         factory.rollback();
      }
      throw ex;
    } finally {
    }
  }  

  /**
   * Delete a DeviceTreeNode
   * 
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return ActionForward
   */
  public ActionForward delete(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    
    DynaValidatorForm form = (DynaValidatorForm) rawForm;
    // Load delete node
    String nodeID = (String)form.get("deleteNodeID");
    if (nodeID != null && nodeID.trim().length() > 0) {
       ManagementBeanFactory factory = null;
       try {
           factory = this.getManagementBeanFactory(request);
           DeviceBean deviceBean = factory.createDeviceBean();
           DeviceTreeNode node = deviceBean.getDeviceTreeNodeByID(nodeID);
           if (node != null) {
              factory.beginTransaction();
              deviceBean.delete(node);
              factory.commit();
           }
       } catch (Exception ex) {
         if (factory != null) {
            factory.rollback();
         }
         throw ex;
       } finally {
       }
    }
    return this.display(mapping, rawForm, request, response);
  }  

}
