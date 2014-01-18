/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/tags/TimeItem.java,v 1.2 2009/02/23 08:13:36 zhaowx Exp $
 * $Revision: 1.2 $
 * $Date: 2009/02/23 08:13:36 $
 *
 * ===============================================================================================
 * License, Version 1.1
 *
 * Copyright (c) 1994-2009 NPower Network Software Ltd.  All rights reserved.
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

package com.npower.dm.tags;

import java.util.Locale;
import java.util.ResourceBundle;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.util.RequestUtils;

/**
 * @author Zhao wanxiang
 * @version $Revision: 1.2 $ $Date: 2009/02/23 08:13:36 $
 */

public class TimeItem {
  
  private long MILLISECOND = 1;
  private long SECOND = MILLISECOND * 1000;
  private long MINUTE =  60 * SECOND;
  private long HOUR = MINUTE * 60;
  private long DAY = HOUR * 24;

  private long millisecond;
  private long second;
  private long minute;
  private long hour;
  private long day;
  private boolean isMilliSecond = false;
  private HttpServletRequest request = null;
  
     
  public long getSecond() {
    return second;
  }

  public void setSecond(long second) {
    this.second = second;
  }

  public long getMinute() {
    return minute;
  }

  public void setMinute(long minute) {
    this.minute = minute;
  }

  public long getHour() {
    return hour;
  }

  public void setHour(long hour) {
    this.hour = hour;
  }

  public long getDay() {
    return day;
  }

  public void setDay(long day) {
    this.day = day;
  }

  public long getMillisecond() {
    return millisecond;
  }

  public void setMillisecond(long millisecond) {
    this.millisecond = millisecond;
  }

 
  public HttpServletRequest getRequest() {
    return request;
  }

  public void setRequest(HttpServletRequest request) {
    this.request = request;
  }

  
  
  public boolean isMilliSecond() {
    return isMilliSecond;
  }

  public void setMilliSecond(boolean isMilliSecond) {
    this.isMilliSecond = isMilliSecond;
  }

  /**
   * 
   */
  public TimeItem(long second,boolean isMilliSecond) {
    this.isMilliSecond = isMilliSecond;
    long chushu;
    long yushu;
    if (this.isMilliSecond() == false){
      
      this.SECOND = 1;
      this.MINUTE =  60 * SECOND;
      this.HOUR = MINUTE * 60;
      this.DAY = HOUR * 24;

      
      chushu = (long)Math.rint(second / this.DAY);      
      if(chushu > 0 ){
        this.setDay(chushu);
      }      
      yushu = second % this.DAY;
      chushu = (long)Math.rint(yushu / this.HOUR);
      if(chushu > 0 ){
        this.setHour(chushu);
      }
      yushu = yushu % this.HOUR;
      chushu = (long)Math.rint(yushu / this.MINUTE);
      if(chushu > 0 ){
        this.setMinute(chushu);
      }
      yushu = yushu % this.MINUTE;
      this.setSecond(yushu);            
    }
            
    if (this.isMilliSecond() == true) {
      long milliSecond = second * 1000;
      
      chushu = (long)Math.rint(milliSecond / this.DAY);      
      if(chushu > 0 ){
        this.setDay(chushu);
      }      
      yushu = milliSecond % this.DAY;
      chushu = (long)Math.rint(yushu / this.HOUR);
      if(chushu > 0 ){
        this.setHour(chushu);
      }
      yushu = yushu % this.HOUR;
      chushu = (long)Math.rint(yushu / this.MINUTE);
      if(chushu > 0 ){
        this.setMinute(chushu);
      }
      yushu = yushu % this.MINUTE;
      chushu = (long)Math.rint(yushu / this.SECOND);           
      if(chushu > 0 ){
        this.setSecond(chushu);      
      }
      yushu = yushu % this.SECOND;
      this.setMillisecond(yushu);
    }
  }
  
  public String toString(){
    StringBuffer sb = new StringBuffer();
    if (this.getDay()!= 0){
      sb.append(String.valueOf(this.getDay())).append(getResource().getString("page.carrier.view.day.lable"));
    }
    if (this.getHour()!= 0){
      sb.append(String.valueOf(this.hour)).append(getResource().getString("page.carrier.view.hour.lable"));
    }
    if (this.getMinute()!= 0){
      sb.append(String.valueOf(this.minute)).append(getResource().getString("page.carrier.view.minute.lable"));
    }
    if (this.isMilliSecond() == true){
      if (this.getSecond()!= 0){
        sb.append(String.valueOf(this.second)).append(getResource().getString("page.carrier.view.second.lable"));
      }
      sb.append(String.valueOf(this.millisecond)).append(getResource().getString("page.carrier.view.millisecond.lable"));
    }else {
      sb.append(String.valueOf(this.second)).append(getResource().getString("page.carrier.view.second.lable"));
    }
    return sb.toString();
  }
  
  private ResourceBundle getResource(){
    ResourceBundle resourceBundle = ResourceBundle.getBundle("displaytag", getLocale());
    return resourceBundle;
  }

  private Locale getLocale(){
    Locale lcoale = RequestUtils.getUserLocale(this.getRequest(), null);
    return lcoale;
  }


}
