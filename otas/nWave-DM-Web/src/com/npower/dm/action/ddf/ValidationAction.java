package com.npower.dm.action.ddf;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.npower.dm.action.BaseAction;

public class ValidationAction extends BaseAction {
  
  private static Log log = LogFactory.getLog(ValidationAction.class);

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

    try {

      if (isCancelled(request)) {
        return (mapping.findForward("returnmodeledit"));
      }

      String xmlfilename = (String) request.getSession().getAttribute("filedir");

      DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
      dbfactory.setValidating(true);
      dbfactory.setNamespaceAware(true);
      DocumentBuilder domparser = dbfactory.newDocumentBuilder();

      @SuppressWarnings("unused")
      Document doc = domparser.parse(xmlfilename);

    } catch (SAXParseException ex) {

      ActionMessages errors = new ActionMessages();

      errors.add("errParseName", new ActionMessage("xml.err.parse.name"));
      errors.add("errParseLine", new ActionMessage("xml.err.parse.line", ex.getLineNumber()));
      errors.add("errParseMessage", new ActionMessage("xml.err.parse.message", ex.getLocalizedMessage()));

      saveErrors(request, errors);
      return mapping.findForward("err");

    } catch (SAXException ex) {

      ActionMessages errors = new ActionMessages();

      errors.add("errSAXExpName", new ActionMessage("xml.err.SAXExp.name"));
      errors.add("errSAXExpStr", new ActionMessage("xml.err.SAXExp.string", ex.toString()));
      errors.add("errSAXExpMessage", new ActionMessage("xml.err.SAXExp.message", ex.getMessage()));

      saveErrors(request, errors);
      return mapping.findForward("err");

    } catch (IOException ex) {

      ActionMessages errors = new ActionMessages();

      errors.add("errIOEName", new ActionMessage("xml.err.IOExp.name"));
      errors.add("errIOEStr", new ActionMessage("xml.err.IOExp.string", ex.toString()));

      saveErrors(request, errors);
      return mapping.findForward("err");

    } catch (ParserConfigurationException e) {

      ActionMessages errors = new ActionMessages();
      errors.add("errParserCfgExpName", new ActionMessage("xml.err.ParserConfigurationException.name"));
      saveErrors(request, errors);
      return mapping.findForward("err");

    } catch (FactoryConfigurationError e) {

      ActionMessages errors = new ActionMessages();
      errors.add("errFactoryCfgName", new ActionMessage("xml.err.FactoryConfigurationError.name"));
      saveErrors(request, errors);
      return mapping.findForward("err");

    } catch (Exception e) {
      log.fatal(e.getMessage(), e);
    }

    return mapping.findForward("import");
  }

}
