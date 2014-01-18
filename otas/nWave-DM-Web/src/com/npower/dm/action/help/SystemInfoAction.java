package com.npower.dm.action.help;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.npower.dm.util.SystemInfo;

public class SystemInfoAction extends Action {
  public static final String IS_SYSTEM_INFO_KEY = "isSystemInfo";

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    request.setAttribute(IS_SYSTEM_INFO_KEY, Boolean.TRUE);
    
    SystemInfo sysInfo = new SystemInfo();
    sysInfo.setServletContext(this.getServlet().getServletContext());
    this.getServlet().getServletContext().setAttribute("systemInfo", sysInfo);
    return mapping.findForward("success");
  }
}