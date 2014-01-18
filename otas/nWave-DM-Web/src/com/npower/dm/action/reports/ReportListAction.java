/**
  * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/action/reports/ReportListAction.java,v 1.4 2009/02/13 10:33:15 zhao Exp $
  * $Revision: 1.4 $
  * $Date: 2009/02/13 10:33:15 $
  *
  * ===============================================================================================
  * License, Version 1.1
  *
  * Copyright (c) 1994-2007 NPower Network Software Ltd.  All rights reserved.
  *
  * This SOURCE CODE FILE, which has been provided by NPower as part
  * of a NPower product for use ONLY by licensed users of the product,
  * includes CONFIDENTIAL and PROPRIETARY information of NPower.
  *
  * USE OF THIS SOFTWARE IS GOVERNED BY THE TERMS AND CONDITIONS
  * OF THE LICENSE STATEMENT AND LIMITED WARRANTY FURNISHED WITH
  * THE PRODUCT.
  *
  * IN PARTICULAR, YOU WILL INDEMNIFY AND HOLD NPower, ITS RELATED
  * COMPANIES AND ITS SUPPLIERS, HARMLESS FROM AND AGAINST ANY CLAIMS
  * OR LIABILITIES ARISING OUT OF THE USE, REPRODUCTION, OR DISTRIBUTION
  * OF YOUR PROGRAMS, INCLUDING ANY CLAIMS OR LIABILITIES ARISING OUT OF
  * OR RESULTING FROM THE USE, MODIFICATION, OR DISTRIBUTION OF PROGRAMS
  * OR FILES CREATED FROM, BASED ON, AND/OR DERIVED FROM THIS SOURCE
  * CODE FILE.
  * ===============================================================================================
  */
package com.npower.dm.action.reports;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.npower.dm.action.BaseAction;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.4 $ $Date: 2009/02/13 10:33:15 $
 */
public class ReportListAction extends BaseAction {
  
  public class Report {
    private String title = null;
    private List<ReportItem> items = new ArrayList<ReportItem>();
    
    /**
     * 
     */
    public Report() {
      super();
    }
    /**
     * @param title
     */
    public Report(String title) {
      super();
      this.title = title;
    }
    /**
     * @return the title
     */
    public String getTitle() {
      return title;
    }
    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
      this.title = title;
    }
    /**
     * @return the items
     */
    public List<ReportItem> getItems() {
      return items;
    }
    /**
     * @param items the items to set
     */
    public void setItems(List<ReportItem> items) {
      this.items = items;
    }
    
  }
  public class ReportItem {
    private String icon = null;
    private String label = null;
    private String url = null;
    private long size = 0;
    private Date generatedTime = null;
    
    
    /**
     * 
     */
    public ReportItem() {
      super();
    }
    /**
     * @param icon
     * @param label
     * @param url
     */
    public ReportItem(String icon, String label, String url) {
      super();
      this.icon = icon;
      this.label = label;
      this.url = url;
    }
    /**
     * @param icon
     * @param label
     * @param url
     * @param generatedTime
     */
    public ReportItem(String icon, String label, String url, Date generatedTime, long size) {
      super();
      this.icon = icon;
      this.label = label;
      this.url = url;
      this.generatedTime = generatedTime;
      this.size = size;
    }
    /**
     * @return the icon
     */
    public String getIcon() {
      return icon;
    }
    /**
     * @param icon the icon to set
     */
    public void setIcon(String icon) {
      this.icon = icon;
    }
    /**
     * @return the label
     */
    public String getLabel() {
      return label;
    }
    /**
     * @param label the label to set
     */
    public void setLabel(String label) {
      this.label = label;
    }
    /**
     * @return the url
     */
    public String getUrl() {
      return url;
    }
    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
      this.url = url;
    }
    /**
     * @return the generatedTime
     */
    public Date getGeneratedTime() {
      return generatedTime;
    }
    /**
     * @param generatedTime the generatedTime to set
     */
    public void setGeneratedTime(Date generatedTime) {
      this.generatedTime = generatedTime;
    }
    /**
     * @return the size
     */
    public long getSize() {
      return size;
    }
    /**
     * @param size the size to set
     */
    public void setSize(long size) {
      this.size = size;
    }
    
  }
  
  private File getReportFile(String template, String suffix) {
    File home = new File(System.getProperty("otas.dm.home"));
    File outputDir = new File(home, "httpd/server/default/deploy/otas-dm-report.war/output");
    File templateDir = new File(home, "httpd/server/default/deploy/otas-dm-report.war/report/reports");
    
    File templateFile = new File(templateDir, template);
    String name = templateFile.getName();
    name = name.substring(0, name.indexOf("."));
    
    File outputFile = new File(outputDir, name + "." + suffix);
    return outputFile;
  }
  
  /**
   * Method execute
   * 
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return ActionForward
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    List<Report> reports = new ArrayList<Report>();
    
    {
      Report report1 = new Report("page.report.devices_by_date.title");
      {
        File file = this.getReportFile("devices/devices_by_date.rptdesign", "pdf");
        if (file != null && file.exists()) {
           report1.getItems().add(new ReportItem("/common/images/icons/icon_acrobat.jpeg", 
                                              "page.report.format.pdf.label",
                                              "/otas-dm-report/output/devices_by_date.pdf",
                                              new Date(file.lastModified()),
                                              file.length()));
        }
      }
      {
        File file = this.getReportFile("devices/devices_by_date.rptdesign", "html");
        if (file != null && file.exists()) {
           report1.getItems().add(new ReportItem("/common/images/icons/icon_html.jpeg", 
                                              "page.report.format.html.label",
                                              "/otas-dm-report/output/devices_by_date.html",
                                              new Date(file.lastModified()),
                                              file.length()));
        }
      }
      report1.getItems().add(new ReportItem("/common/images/icons/icon_chart.jpeg", 
                                            "page.report.format.realtime.label",
                                            "/otas-dm-report/frameset?__report=report/reports/devices/devices_by_date.rptdesign"));
      reports.add(report1);
    }
    
    {
      Report report = new Report("page.report.devices_status.title");
      {
        File file = this.getReportFile("jobs/device_status.rptdesign", "pdf");
        if (file != null && file.exists()) {
          report.getItems().add(new ReportItem("/common/images/icons/icon_acrobat.jpeg", 
                                              "page.report.format.pdf.label",
                                              "/otas-dm-report/output/device_status.pdf",
                                              new Date(file.lastModified()),
                                              file.length()));
        }
      }
      {
        File file = this.getReportFile("devices/devices_by_date.rptdesign", "html");
        if (file != null && file.exists()) {
          report.getItems().add(new ReportItem("/common/images/icons/icon_html.jpeg", 
                                              "page.report.format.html.label",
                                              "/otas-dm-report/output/device_status.html",
                                              new Date(file.lastModified()),
                                              file.length()));
        }
      }
      report.getItems().add(new ReportItem("/common/images/icons/icon_chart.jpeg", 
                                            "page.report.format.realtime.label",
                                            "/otas-dm-report/frameset?__report=report/reports/jobs/device_status.rptdesign"));
      reports.add(report);
    }
    
    {
      Report report = new Report("page.report.model_capabilities.title");
      {
        File file = this.getReportFile("models/capabilities.rptdesign", "pdf");
        if (file != null && file.exists()) {
          report.getItems().add(new ReportItem("/common/images/icons/icon_acrobat.jpeg", 
                                              "page.report.format.pdf.label",
                                              "/otas-dm-report/output/capabilities.pdf",
                                              new Date(file.lastModified()),
                                              file.length()));
        }
      }
      {
        File file = this.getReportFile("devices/devices_by_date.rptdesign", "html");
        if (file != null && file.exists()) {
          report.getItems().add(new ReportItem("/common/images/icons/icon_html.jpeg", 
                                              "page.report.format.html.label",
                                              "/otas-dm-report/output/capabilities.html",
                                              new Date(file.lastModified()),
                                              file.length()));
        }
      }
      report.getItems().add(new ReportItem("/common/images/icons/icon_chart.jpeg", 
                                            "page.report.format.realtime.label",
                                            "/otas-dm-report/frameset?__report=report/reports/models/capabilities.rptdesign"));
      reports.add(report);
    }
    
    request.setAttribute("reports", reports);
    return (mapping.findForward("list"));
  }

}
