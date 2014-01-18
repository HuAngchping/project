package com.npower.dm.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.struts.util.ResponseUtils;

public class BoxTag extends BodyTagSupport {
  private String id;

  private String styleClass = "box";

  private String title;

  public int doEndTag() throws JspException {
    String id = this.id;
    if (id == null)
      id = title;
    StringBuffer results = new StringBuffer();
    results.append("<div class=\"" + styleClass + "\">");
    results.append("<h3 style=\"border-bottom-width:0px\">");
    results.append("<span class=\"boxIcon\">");
    results.append("   <span class=\"boxIconText\" id=\"" + id + "-switch\" onclick=\"toggle('" + id
        + "')\">[hide]</span>\n");
    results.append("</span>");
    results.append(title);
    results.append("</h3>");
    results.append("<div id=\"" + id + "\" class=\"boxBody\">");
    renderBody(results);
    results.append("</div>");
    results.append("</div>");

    ResponseUtils.write(pageContext, results.toString());

    return super.doEndTag();
  }

  protected void renderBody(StringBuffer results) {
    if (bodyContent != null) {
      results.append(bodyContent.getString());
    }
  }

  public void setStyleClass(String styleClass) {
    this.styleClass = styleClass;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setId(String id) {
    this.id = id;
  }
}