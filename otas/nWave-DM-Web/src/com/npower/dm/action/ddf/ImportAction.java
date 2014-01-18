package com.npower.dm.action.ddf;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.npower.dm.action.BaseAction;
import com.npower.dm.core.DDFNode;
import com.npower.dm.core.DDFTree;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Model;
import com.npower.dm.management.DDFTreeBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ModelBean;

public class ImportAction extends BaseAction {

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

    if (isCancelled(request)) {
      return (mapping.findForward("returnmodeledit"));
    }

    String xmlfile = (String) request.getSession().getAttribute("filedir");
    Long modelID = (Long) request.getSession().getAttribute("modelID");

    ManagementBeanFactory factory = null;
    DDFTreeBean ddfTreeBean = null;
    ModelBean modelBean = null;
    try {
      factory = this.getManagementBeanFactory(request);
      factory.beginTransaction();
      ddfTreeBean = factory.createDDFTreeBean();
      modelBean = factory.createModelBean();

      InputStream in = new FileInputStream(new File(xmlfile));
      DDFTree tree = ddfTreeBean.parseDDFTree(in);
      in.close();

      Model model = modelBean.getModelByID(modelID.toString());

      Set<DDFNode> nodes = tree.getRootDDFNodes();
      for (DDFNode node : nodes) {

        String nodePath = node.getNodePath();

        for (Iterator it = node.getChildren().iterator(); it.hasNext();) {
          DDFNode ddfnode = (DDFNode) it.next();
          String nodename = ddfnode.getName();

          nodePath = ddfnode.getNodePath();

          DDFNode databaseNode = ddfTreeBean.findDDFNodeByNodePath(model, nodePath);

          if (databaseNode != null) {
            ddfTreeBean.delete(databaseNode);
          }

          request.setAttribute("nodename", nodename);
        }

      }

      ddfTreeBean.addDDFTree(tree);
      modelBean.attachDDFTree(model, tree.getID());

      factory.commit();
      
      // Pass target into AOP Audit
      request.setAttribute("model", model);
    } catch (DMException e) {
      if (factory != null) {
        factory.rollback();
      }
      throw e;
    } catch (Exception e) {
      if (factory != null) {
        factory.rollback();
      }
      throw e;
    } finally {
    }
    return mapping.findForward("display");
  }

}
