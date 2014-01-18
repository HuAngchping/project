package com.npower.dm.action.ddf;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.upload.FormFile;

import com.npower.dm.action.BaseAction;
import com.npower.dm.form.UpLoadForm;

public class UpLoadAction extends BaseAction {

  public static boolean SAXvalidate(File xmlFile) throws Exception {

    boolean result = false;

    return result;
  }

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

    if (isCancelled(request)) {
      return (mapping.findForward("returnmodeledit"));
    }

    if (form instanceof UpLoadForm) {

      UpLoadForm theForm = (UpLoadForm) form;
      FormFile thefile = theForm.getTheFile();

      try {

        InputStream stream = thefile.getInputStream();
        File xmldir = new File(System.getProperty("java.io.tmpdir"), "DDFXml");

        if (!xmldir.exists()) {
          xmldir.mkdir();
        }

        SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
        String filedate = bartDateFormat.format(new Date());

        String filename = thefile.getFileName().substring(0, thefile.getFileName().indexOf(".")) + filedate
            + thefile.getFileName().substring(thefile.getFileName().indexOf("."), thefile.getFileName().length());

        File xmlFile = new File(xmldir, filename);
        OutputStream fileos = new FileOutputStream(new File(xmldir, filename));

        if ((filename.lastIndexOf(".xml") < 0) && (filename.trim().length() != 0)) {

          ActionMessages errors = new ActionMessages();
          errors.add("fileerr", new ActionMessage("xml.err.file.name"));
          saveErrors(request, errors);
          return mapping.findForward("fileerr");
        }

        int bytesRead = 0;
        byte[] buffer = new byte[8192];

        while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
          fileos.write(buffer, 0, bytesRead);
        }

        request.getSession().setAttribute("AbsoluteFile", xmlFile.getAbsolutePath());

        HttpSession session = request.getSession();
        session.setAttribute("filedir", xmlFile.getAbsolutePath());

        session.setAttribute("ddfName", xmlFile.getName());

        fileos.close();
        stream.close();
      } catch (Exception e) {
        System.err.print(e);
      }

      return mapping.findForward("validation");
    }
    return null;
  }
}
