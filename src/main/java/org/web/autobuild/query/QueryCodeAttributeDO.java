/**
* CodeAttributeDO entity encapsulation table code_attribute record. 
* @author Eclipse Tools Generate.
* @Time 2016-11-26 01:31:58
* Copyright by LuYuanliang.
*/

package org.web.autobuild.query;

import java.util.List;

public class QueryCodeAttributeDO extends QueryBase {

	private Long codeAttributeId = null;
	private List < Integer > codeAttributeIdList = null;
	private String mark = null;
	private String columnName = null;
	private List < String > columnNameList = null;
	private String attributeName = null;
	private List < String > attributeNameList = null;
	private String sortOrder = null;
	
	public String getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}
	public void setCodeAttributeIdList ( List < Integer > codeAttributeIdList ) {
		this.codeAttributeIdList = codeAttributeIdList;
	}
	public List < Integer >  getCodeAttributeIdList(){
		return this.codeAttributeIdList ;
	}

	public Long getCodeAttributeId() {
		return codeAttributeId;
	}
	public void setCodeAttributeId(Long codeAttributeId) {
		this.codeAttributeId = codeAttributeId;
	}
	public void setMark(String mark) {
		this.mark=mark;
	}
	public String getMark(){
		return mark;
	}

	public void setColumnName(String columnName) {
		this.columnName=columnName;
	}
	public String getColumnName(){
		return columnName;
	}

	public void setColumnNameList ( List < String > columnNameList ) {
		this.columnNameList = columnNameList;
	}
	public List < String >  getColumnNameList(){
		return this.columnNameList ;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName=attributeName;
	}
	public String getAttributeName(){
		return attributeName;
	}

	public void setAttributeNameList ( List < String > attributeNameList ) {
		this.attributeNameList = attributeNameList;
	}
	public List < String >  getAttributeNameList(){
		return this.attributeNameList ;
	}


}


