package com.npower.dm.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.upload.FormFile;
import org.apache.struts.upload.MultipartRequestHandler;

public class TemplateForm extends ActionForm {
  public static final String ERROR_PROPERTY_MAX_LENGTH_EXCEEDED = "org.apache.struts.webapp.upload.MaxLengthExceeded";

  protected FormFile theFile;

  public FormFile getTheFile() {
    return theFile;
  }

  public void setTheFile(FormFile theFile) {
    this.theFile = theFile;
  }

  public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {

    ActionMessages errors = null;

    Boolean maxLengthExceeded = (Boolean) request.getAttribute(MultipartRequestHandler.ATTRIBUTE_MAX_LENGTH_EXCEEDED);
    if ((maxLengthExceeded != null) && (maxLengthExceeded.booleanValue())) {
      errors = new ActionMessages();
      errors.add(ERROR_PROPERTY_MAX_LENGTH_EXCEEDED, new ActionMessage("maxLengthExceeded"));
    }
    return (ActionErrors) errors;
  }

}
