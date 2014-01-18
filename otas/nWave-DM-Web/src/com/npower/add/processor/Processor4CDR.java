/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/add/processor/Processor4CDR.java,v 1.3 2008/11/12 08:30:01 zhao Exp $
 * $Revision: 1.3 $
 * $Date: 2008/11/12 08:30:01 $
 *
 * ===============================================================================================
 * License, Version 1.1
 *
 * Copyright (c) 1994-2008 NPower Network Software Ltd.  All rights reserved.
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

package com.npower.add.processor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.npower.add.sgsn.core.DataItem4cdr;
import com.npower.add.sgsn.core.DataItem4sgsn;
import com.npower.add.sgsn.core.ReturnHeaderInfo;
import com.npower.add.sgsn.dispatcher.JobDispatcher;
import com.npower.add.sgsn.parser.DataItemParser;
import com.npower.add.sgsn.parser.ParseException;

/**
 * @author Zhao wanxiang
 * @version $Revision: 1.3 $ $Date: 2008/11/12 08:30:01 $
 */

/**
* @author Zhao wanxiang
* @version $Revision: 1.3 $ $Date: 2008/11/12 08:30:01 $
*/
public class Processor4CDR implements Processor {
  private static Log log = LogFactory.getLog(Processor4CDR.class);
  
  private JobDispatcher cRDDispatcher;  
  private DataItemParser parser = null;  
  private String fileName = null; 
  private String directory4ResRight = "./cdr/response/right";
  private String directory4ResBad = "./cdr/response/bad";
  private String directory4ResSemiwrong = "./cdr/response/semiwrong";  
      
  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  /**
   * 
   */
  public Processor4CDR() {
    super();
  }
    
  
  /**
   * @param dispatcher
   * @param parser
   * @param directory4ResRight
   * @param directory4ResBad
   * @param directory4ResSemiwrong
   */
  public Processor4CDR(JobDispatcher dispatcher, DataItemParser parser, String directory4ResRight,
      String directory4ResBad, String directory4ResSemiwrong) {
    super();
    cRDDispatcher = dispatcher;
    this.parser = parser;
    this.directory4ResRight = directory4ResRight;
    this.directory4ResBad = directory4ResBad;
    this.directory4ResSemiwrong = directory4ResSemiwrong;
  }

  public boolean process(Reader reader) throws Exception {
    return processCDR(reader);
  }  
  
  public boolean processCDR(Reader reader) throws Exception {

    BufferedReader in = new BufferedReader(reader);
    String line = in.readLine();
    int lineNumber = 1;
    //解析文件头
    ReturnHeaderInfo returnHeaderInfo = parseFileHeader(line, lineNumber);
    // 获得文件体输出流
    Writer responseBody = new FileWriter(this.getResponseBodyFile());
    
    DataItem4sgsn item = null;
    // 初始化计数器
    int totalRecords = 0;
    int totalSuccessRecords = 0;

    line = in.readLine(); 
    while (line != null) {
          item = null;
          String errorCode = "900";
          boolean tag = false;
          try {
              item = parser.parse(lineNumber, line);
              if (item != null) {
                tag = this.cRDDispatcher.dispatch(item);
              }
          } catch (ParseException e) {
            // 检测不同的错误, 并记录错误
            errorCode = "001";
            writeBadReturnFileBody(responseBody, errorCode, item);
            log.error("Pasring data error in line#" + lineNumber, e);
          }  catch (Exception e) {
            // 检测不同的错误, 并记录错误
            errorCode = "900";
            writeBadReturnFileBody(responseBody, errorCode, item);            
            log.error("Fail to process data in line#" + lineNumber, e);
          } finally {
            if (tag) {
              totalSuccessRecords++;
            }
            totalRecords++;
          }
          
          line = in.readLine();
          lineNumber++;         
    }
    
    responseBody.close();
    
    returnHeaderInfo.setSuccessRecords(String.valueOf(totalSuccessRecords));
    returnHeaderInfo.setRecords(String.valueOf(totalRecords));
    
    //如果错误的记录数等于总记录数，则生成全部错误回执文件        
    if (totalSuccessRecords == 0) {
      // 按全部错误输出完整的回执文件
      Writer responseHeader = new FileWriter(this.getResponseFile4Bad());
      writeReturnFileHeader(responseHeader, returnHeaderInfo);
      responseHeader.close();
      return false;
    } else if (totalRecords == totalSuccessRecords) {
      // 按全部正确输出完整的回执文件
      Writer responseHeader = new FileWriter(this.getResponseFile4Right());
      writeReturnFileHeader(responseHeader, returnHeaderInfo);
      responseHeader.close();
      return true;
    } else if (totalRecords != totalSuccessRecords) {
      // 按部分错误输出完整的回执文件 
      Writer responseHeader = new FileWriter(this.getResponseFile4Semiwrong());
      writeReturnFileHeader(responseHeader, returnHeaderInfo);
      Reader responseReader = new FileReader(this.getResponseBodyFile());
      BufferedReader  br = new BufferedReader(responseReader);
      String bodyStr = br.readLine();
      
      while(bodyStr != null){
        responseHeader.write(bodyStr);
        responseHeader.write("\n");
        bodyStr = br.readLine();
      }
      br.close();
      responseHeader.close();
      return false;
    } 
    return false;
  }
  
  /**
   * 根据指定的filename, 返回一个文件对象.
   * 如果filename是相对路径, 则追加相对路径的基础目录为${otas.dm.home}.
   * 
   * @param filename
   * @return
   */
  private File getFile(String filename) {
    File file = new File(filename);
    if (!file.isAbsolute()) {
       file = new File(System.getProperty("otas.dm.home"), filename);
    }
    return file;
  }
  
  /**获得不包含后缀的文件名称
   * @param filename
   * @return
   */
  private String getFilePrefix(String filename) {
    int index = filename.lastIndexOf('.');
    if (index > 0) { 
       return filename.substring(0, index);
    }
    return filename;
  }
  
  /**获得部分错误回执文件体的File实例
   * @return
   */
  private File getResponseBodyFile() {
    File dir = this.getFile(directory4ResSemiwrong);
    File file = new File(dir, this.getFilePrefix(this.fileName) + ".res.body");
    return file;
  }
  
  /**获得全部正确回执文件的File实例
   * @return
   */
  private File getResponseFile4Right() {
    File dir = this.getFile(directory4ResRight);
    File file = new File(dir, this.getFilePrefix(this.fileName) + ".res");
    return file;
  }
  
  /**获得全部错误回执文件的File实例
   * @return
   */
  private File getResponseFile4Bad() {
    File dir = this.getFile(directory4ResBad);
    File file = new File(dir, this.getFilePrefix(this.fileName) + ".res");
    return file;
  }
  
  /**获得部分错误回执文件的File实例
   * @return
   */
  private File getResponseFile4Semiwrong() {
    File dir = this.getFile(directory4ResSemiwrong);
    File file = new File(dir, this.getFilePrefix(this.fileName) + ".res");
    return file;
  }
 
  
  /**解析文件头
   * @return
   */  
  private ReturnHeaderInfo parseFileHeader(String line, int lineNumber) throws ParseException {
    if (StringUtils.isEmpty(line)) {
      throw new ParseException("Request file header is null in line#" + lineNumber + ":" + line);
    }
    String[] cols = StringUtils.split(line, " ");
    if (cols == null || cols.length < 7) {
       throw new ParseException("Error in line#" + lineNumber + ":" + line);
    }
    String seriesNumber= cols[0];
    String versionNumber= cols[1];
    String createFileTime= cols[2];
    String fileCreatorCode= cols[3];
    String records= cols[6];
    String remark= cols[7];    
    
    ReturnHeaderInfo returnHeaderInfo = new ReturnHeaderInfo();
    returnHeaderInfo.setCreateFileTime(createFileTime);
    returnHeaderInfo.setFileCreatorCode(fileCreatorCode);
    returnHeaderInfo.setRecords(records);
    returnHeaderInfo.setRemark(remark);
    returnHeaderInfo.setSeriesNumber(seriesNumber);
    returnHeaderInfo.setVersionNumber(versionNumber);
    
    return returnHeaderInfo;
  }
   
  //写回执文件头
  private void writeReturnFileHeader(Writer writer, ReturnHeaderInfo returnHeaderInfo) throws IOException {
    String headerInfo = null;
    if (returnHeaderInfo != null) {
      headerInfo = returnHeaderInfo.getSeriesNumber() + " " + returnHeaderInfo.getVersionNumber()
      +" " + returnHeaderInfo.getCreateFileTime() + " " + returnHeaderInfo.getFileCreatorCode() + " " +
      returnHeaderInfo.getRecords() + " " + returnHeaderInfo.getSuccessRecords() + " " + 
      returnHeaderInfo.getRemark();
    }
    writer.write(headerInfo);
    writer.write("\n");
  }
  
  
  
  
  //写部分错误回执文件体 
  private void writeBadReturnFileBody(Writer responseBody, String errorCode, DataItem4sgsn item) throws IOException {
    String serialNumber = null;
    if (item != null && item instanceof DataItem4cdr) {
      serialNumber = ((DataItem4cdr)item).getSerialNumber();      
    }
    
    String fileBody = serialNumber + " " + errorCode;
    responseBody.write(fileBody);
    responseBody.write("\n");
  }      
   
}
