package com.npower.dm.action.audit;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;
import org.apache.struts.validator.DynaValidatorForm;
import org.displaytag.pagination.PaginatedList;
import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;

import com.npower.dm.action.ActionHelper;
import com.npower.dm.action.BaseAction;
import com.npower.dm.core.DMException;
import com.npower.dm.core.ProfileCategory;
import com.npower.dm.displaytag.PaginatedListAdapter;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.PaginatedResult;
import com.npower.dm.management.ProfileTemplateBean;
import com.npower.dm.management.SearchBean;
import com.npower.dm.util.DisplayTagHelper;
import com.npower.unicom.ExportReportDAO;

/**
* @author Liu AiHui
* @version $Revision: 1.4 $ $Date: 2008/11/03 09:56:34 $
*/
public class CPAuditLogsAction extends BaseAction{
  
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    DynaValidatorForm searchForm = (DynaValidatorForm) form;
    try {
    
        PaginatedList result = this.search(mapping, form, request, response);
        request.setAttribute("cpAuditLogs", result);

        BaseAction.setRecordsPerPageOptions(request, searchForm);
        if (request.getParameter("recordsPerPage") == null) {
           searchForm.set("recordsPerPage", new Integer(100));
        }
        
        // Set carrier manufacturer options
        request.setAttribute("carrierOptions", ActionHelper.getCarrierExternalIDOptions(this.getManagementBeanFactory(request)));
        request.setAttribute("manufacturerOptions", ActionHelper.getManufacturerExternalOptions(this.getManagementBeanFactory(request), request));
        this.loadCategories(request);
        return (mapping.findForward("display"));  

    } catch (DMException e) {
      throw e;
    } 
  }
  
  
  /**
   * categoryOptions in request
   * @param request
   * @throws Exception
   */
  private void loadCategories(HttpServletRequest request) throws Exception {
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    ProfileTemplateBean bean = factory.createProfileTemplateBean();
    List<ProfileCategory> categories = bean.findProfileCategories("from ProfileCategoryEntity");
    List<LabelValueBean> result = new ArrayList<LabelValueBean>();
    for (ProfileCategory category: categories) {
        LabelValueBean labelValue = new LabelValueBean(category.getName(), category.getName());
        result.add(labelValue);
    }
    request.setAttribute("categoryOptions", result);
  }
  
  
  private PaginatedList search(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    DynaValidatorForm searchForm = (DynaValidatorForm) form;
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);

    SearchBean searchBean = factory.createSearchBean();
    Criteria criteria = searchBean.newCriteriaInstance(com.npower.dm.hibernate.entity.ClientProvAuditLogEntity.class);
    criteria.addOrder(Order.desc("timeStamp"));
    
    String searchPhoneNumber = searchForm.getString("searchPhoneNumber");
    if (StringUtils.isNotEmpty(searchPhoneNumber)) {
      criteria.add(Expression.ilike("devicePhoneNumber", searchPhoneNumber, MatchMode.ANYWHERE));
    }
    
    String searchManufacturer = searchForm.getString("searchManufacturer");
    if (StringUtils.isNotEmpty(searchManufacturer)) {
      criteria.add(Expression.ilike("manufacturerExtID", searchManufacturer));
    }

    String searchModel = searchForm.getString("searchModel");
    if (StringUtils.isNotEmpty(searchModel)) {
       criteria.add(Expression.ilike("modelExtID", searchModel, MatchMode.ANYWHERE));
    }
    
    String searchCarrier = searchForm.getString("searchCarrier");
    if (StringUtils.isNotEmpty(searchCarrier)) {
       criteria.add(Expression.ilike("carrierExtID", searchCarrier, MatchMode.ANYWHERE));
    }
    
    String searchStatus = searchForm.getString("searchStatus");
    if (StringUtils.isNotEmpty(searchStatus)) {
       criteria.add(Expression.ilike("status", searchStatus, MatchMode.ANYWHERE));
    }
      
    String searchProfileCategory = searchForm.getString("searchProfileCategory");
    if (StringUtils.isNotEmpty(searchProfileCategory)) {
       criteria.add(Expression.ilike("profileCategoryName", searchProfileCategory, MatchMode.ANYWHERE));
    }    

    
    String searchProfileName = searchForm.getString("searchProfileName");
    if (StringUtils.isNotEmpty(searchProfileName)) {
       criteria.add(Expression.ilike("profileName", searchProfileName, MatchMode.ANYWHERE));
    } 
    
    
    String searchText = searchForm.getString("searchText");
    if (StringUtils.isNotEmpty(searchText)) {
       criteria.add(Expression.ilike("memo", searchText, MatchMode.ANYWHERE));
    }
  
    String searchBeginTimeString = searchForm.getString("searchBeginTime");
    String searchEndTimeString = searchForm.getString("searchEndTime");
    Date searchBeginTime = null;
    Date searchEndTime = null;
    if (StringUtils.isNotEmpty(searchBeginTimeString)) {
       try {
           DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm");
           searchBeginTime = formatter.parse(searchBeginTimeString);
      } catch (ParseException e) {
        throw e;
      }
    }
    if (StringUtils.isNotEmpty(searchEndTimeString)) {
       try {
           DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm");
           searchEndTime = formatter.parse(searchEndTimeString);
       } catch (ParseException e) {
         throw e;
       }
    }
    if (searchBeginTime != null && searchEndTime != null) {
       criteria.add(Expression.and(Expression.ge("timeStamp", searchBeginTime), 
                                   Expression.le("timeStamp", searchEndTime)));
    } else if (searchBeginTime != null) {
      criteria.add(Expression.ge("timeStamp", searchBeginTime));
    } else if (searchEndTime != null) {
      criteria.add(Expression.le("timeStamp", searchEndTime));
    }
    
    int pageNumber = DisplayTagHelper.getPageNumber(request);
    int recordsPerPage = getRecordsPerPage(request);
    PaginatedResult result = searchBean.getPaginatedList(criteria, pageNumber, recordsPerPage);
    PaginatedList list = new PaginatedListAdapter(result);
    return list;
  }
}
