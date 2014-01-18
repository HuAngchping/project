package com.npower.dm.action.audit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.npower.dm.action.BaseAction;
import com.npower.dm.core.ClientProvAuditLog;
import com.npower.dm.management.ClientProvAuditLogBean;
import com.npower.dm.management.ManagementBeanFactory;

/**
* @author Liu AiHui
* @version $Revision: 1.2 $ $Date: 2007/09/24 06:27:11 $
*/
public class ViewCPAuditLogsAction  extends BaseAction{
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    if(this.isCancelled(request)){
      return (mapping.findForward("display"));
    }
    String id = request.getParameter("ID");
    if(StringUtils.isEmpty(id)){
      return (mapping.findForward("display"));
    }
    Long cpAuditLogID = Long.valueOf(id);
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    ClientProvAuditLogBean bean = factory.createClientProvAuditLogBean();
        
    ClientProvAuditLog cpAuditLog = bean.getClientProvAuditLogByID(cpAuditLogID);
    request.setAttribute("cpAuditLog", cpAuditLog);
    
    Long cpAuditLogIDnext = bean.getNextID(cpAuditLogID);
    Long cpAuditLogIDprev = bean.getPrevID(cpAuditLogID);    
    request.setAttribute("cpAuditLogNextID", cpAuditLogIDnext);
    request.setAttribute("cpAuditLogPrevID", cpAuditLogIDprev);
    return (mapping.findForward("view"));
  }
}
