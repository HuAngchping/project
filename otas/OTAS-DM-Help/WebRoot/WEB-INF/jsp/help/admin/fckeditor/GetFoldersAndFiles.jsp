<%@ page language="java" pageEncoding="UTF-8" contentType="text/xml; charset=UTF-8"%><%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%><%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%><%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%><?xml version="1.0" encoding="utf-8" ?>
  <Connector command="GetFoldersAndFiles" resourceType="File">
    <CurrentFolder path="/" url="/OTAS-DM-Help/help/admin/fckeditor/download.do?filename=" />
    <logic:notEmpty name="images"><Files><logic:iterate id="file" name="images">
      <File name="${file.imageId}_${file.filename}" size="100" /></logic:iterate>
    </Files></logic:notEmpty>
</Connector>