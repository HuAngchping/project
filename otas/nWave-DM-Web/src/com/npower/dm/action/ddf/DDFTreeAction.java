//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package com.npower.dm.action.ddf;

import java.util.Set;
import java.util.TreeSet;

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
import com.npower.dm.core.DDFTree;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Model;
import com.npower.dm.management.DDFTreeBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ModelBean;

/**
 */
public class DDFTreeAction extends BaseDispatchAction {
  
  private static String nodeAction = "/DDFTree";

  // --------------------------------------------------------- Instance
  // Variables
  
  private void convert(String modelID, Set<DDFNode> nodes, MenuRepository repository, MenuComponent parentMenu) {
    for (DDFNode node: nodes) {
        MenuComponent menu = new MenuComponent();
        menu.setName("ID" + node.getID());
        String title = node.getName();
        title = (title == null)?DDFNode.NAME_OF_DYNAMIC_NODE:title;
        menu.setTitle(title);
        menu.setParent(parentMenu);

        menu.setTarget("_blank");
        menu.setAction(nodeAction + "?modelID=" + modelID + "&nodeID=" + node.getID());
        repository.addMenu(menu);
        Set<DDFNode> children = node.getChildren();
        if (!children.isEmpty()) {
           convert(modelID, children, repository, menu);
        }
    }
    
  }
  
  private MenuRepository loadDDFTree(HttpServletRequest request, String modelID) throws DMException {
    MenuRepository repository = new MenuRepository();
    //  Get the repository from the application scope - and copy the
    //  DisplayerMappings from it.
    MenuRepository defaultRepository = (MenuRepository)this.getServlet().getServletContext().getAttribute(MenuRepository.MENU_REPOSITORY_KEY);
    repository.setDisplayers(defaultRepository.getDisplayers());
    MenuComponent rootMenu = new MenuComponent();
    rootMenu.setName("root");
    rootMenu.setTitle("./");
    rootMenu.setAction(nodeAction + "?modelID=" + modelID + "&nodeID=");
    repository.addMenu(rootMenu);
    
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    ModelBean modelBean = factory.createModelBean();
    Model model = modelBean.getModelByID(modelID);

    Set<DDFTree> trees = model.getDDFTrees();

    Set<DDFNode> firstLevelNodes = new TreeSet<DDFNode>();
    for (DDFTree tree: trees) {
        Set<DDFNode> nodes = tree.getRootDDFNodes();
        DDFNode rootNode = nodes.iterator().next();
        Set<DDFNode> children = rootNode.getChildren();
        firstLevelNodes.addAll(children);
    }
    // Convert to TreeMenu.
    convert(modelID, firstLevelNodes, repository, rootMenu);

    return repository;
  }
  
  private DDFNode loadDDFNode(HttpServletRequest request, String nodeID) throws DMException {
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    DDFTreeBean ddfBean = factory.createDDFTreeBean();
    DDFNode node = ddfBean.getDDFNodeByID(nodeID);
    return node;
  }

  // --------------------------------------------------------- Methods

  /**
   * Display DDF Tree
   * 
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return ActionForward
   */
  public ActionForward display(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    
    DynaValidatorForm ddfTreeForm = (DynaValidatorForm) form;
    String modelID = (String)ddfTreeForm.get("modelID");
    if (modelID == null || modelID.trim().length() == 0) {
       throw new DMException("Please specified a model!");
    }
    
    // Load data for Tree
    MenuRepository repository = this.loadDDFTree(request, modelID);
    request.setAttribute("repository", repository);

    // Load data for nodes
    String nodeID = (String)ddfTreeForm.get("nodeID");
    if (nodeID != null && nodeID.trim().length() > 0) {
       DDFNode currentNode = this.loadDDFNode(request, nodeID);
       request.setAttribute("ddfNode", currentNode);
       request.setAttribute("children", currentNode.getChildren());
    } else {
      ManagementBeanFactory factory = this.getManagementBeanFactory(request);
      ModelBean modelBean = factory.createModelBean();
      Model model = modelBean.getModelByID(modelID);
      request.getSession().setAttribute("modelID", model.getID());

      Set<DDFTree> trees = model.getDDFTrees();
      Set<DDFNode> nodes = new TreeSet<DDFNode>();
      for (DDFTree tree: trees) {
          for (DDFNode rootNode: (Set<DDFNode>)tree.getRootDDFNodes()) {
              request.setAttribute("ddfNode", rootNode);
              nodes.addAll(rootNode.getChildren());
          }
      }
      request.setAttribute("children", nodes);
    }
    
    return (mapping.findForward("success"));
     
  }

  /**
   * Display DDF Tree
   * 
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return ActionForward
   */
  public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    return this.display(mapping, form, request, response);
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
        String modelID = form.getString("modelID");
        if (modelID == null || modelID.trim().length() == 0) {
           throw new DMException("Please specified a model!");
        }
        factory = this.getManagementBeanFactory(request);
        ModelBean modelBean = factory.createModelBean();
        DDFTreeBean ddfBean = factory.createDDFTreeBean();
        Model model = modelBean.getModelByID(modelID);
        Set<DDFTree> trees = (Set<DDFTree>)model.getDDFTrees();
        for (DDFTree tree: trees) {
            factory.beginTransaction();
            modelBean.dettachDDFTree(model, tree.getID());
            ddfBean.delete(tree);
            factory.commit();
        }
        
        // Pass target into AOP Audit
        request.setAttribute("model", model);
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
           DDFTreeBean ddfBean = factory.createDDFTreeBean();
           DDFNode node = ddfBean.getDDFNodeByID(nodeID);
           if (node != null) {
              factory.beginTransaction();
              ddfBean.delete(node);
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
