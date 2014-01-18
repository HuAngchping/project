package com.npower.dm.action.profile;

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
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import com.npower.dm.action.BaseAction;
import com.npower.dm.action.ddf.ValidationAction;

public class CategoryValidationAction extends BaseAction implements ErrorHandler {

  private static Log log = LogFactory.getLog(ValidationAction.class);

  private String agree = "";

  private long line = 0;

  private String message = "";

  public void warning(SAXParseException exception) throws SAXException {
    agree = "**Parsing Warning**";
    line = exception.getLineNumber();
    message = exception.getMessage();
    System.out.println("**Parsing Warning**\n" + "  Line:    " + exception.getLineNumber() + "\n" + "  URI:     " + exception.getSystemId() + "\n"
        + "  Message: " + exception.getMessage());
    throw new SAXException("Warning encountered");
  }

  public void error(SAXParseException exception) throws SAXException {
    agree = "**Parsing Error**";
    line = exception.getLineNumber();
    message = exception.getMessage();
    System.out.println("**Parsing Error**\n" + "  Line:    " + exception.getLineNumber() + "\n" + "  URI:     " + exception.getSystemId() + "\n"
        + "  Message: " + exception.getMessage());
    throw new SAXException("Error encountered");
  }

  public void fatalError(SAXParseException exception) throws SAXException {
    agree = "**Parsing Fatal Error**";
    line = exception.getLineNumber();
    message = exception.getMessage();
    System.out.println("**Parsing Fatal Error**\n" + "  Line:    " + exception.getLineNumber() + "\n" + "  URI:     " + exception.getSystemId() + "\n"
        + "  Message: " + exception.getMessage());
    throw new SAXException("Fatal Error encountered");
  }

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

    try {

      if (isCancelled(request)) {
        return (mapping.findForward("returnCategoryedit"));
      }

      String xmlfilename = (String) request.getSession().getAttribute("filedir");

      // ½âÎö1
      DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
      dbfactory.setValidating(true);
      dbfactory.setNamespaceAware(true);

      DocumentBuilder domparser = dbfactory.newDocumentBuilder();
      domparser.parse(xmlfilename);

      // ½âÎö2
      XMLReader reader = XMLReaderFactory.createXMLReader();
      reader.setFeature("http://xml.org/sax/features/validation", true);
      reader.setErrorHandler(new MappingValidationAction());
      reader.parse(xmlfilename);

    } catch (SAXParseException ex) {
      ActionMessages errors = new ActionMessages();

      errors.add("errParseName", new ActionMessage("xml.err.parse.name"));
      errors.add("errParseLine", new ActionMessage("xml.err.parse.line", ex.getLineNumber()));
      errors.add("errParseMessage", new ActionMessage("xml.err.parse.message", ex.getLocalizedMessage()));

      saveErrors(request, errors);

      return mapping.findForward("err");

    } catch (SAXException ex) {

      ActionMessages errors = new ActionMessages();

      errors.add("errParseName", new ActionMessage("xml.err.parse.name"));
      if (agree.equals("")) {
        errors.add("errSAXExpName", new ActionMessage("xml.err.SAXExp.name"));
        errors.add("errSAXExpStr", new ActionMessage("xml.err.SAXExp.string", ex.toString()));
        errors.add("errSAXExpMessage", new ActionMessage("xml.err.SAXExp.message", ex.getMessage()));
      } else {
        errors.add("errSAXExpName", new ActionMessage(agree));
        errors.add("errSAXExpStr", new ActionMessage("xml.err.SAXExp.string", line));
        errors.add("errSAXExpMessage", new ActionMessage("xml.err.SAXExp.message", message));
      }
      saveErrors(request, errors);

      // TODO zhaoyangTest XML have not DTD.
      return mapping.findForward("import");
      // return mapping.findForward("err");

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
