/**
 * CodeAttributeDO entity encapsulation table code_attribute record. 
 * @author Eclipse Tools Generate.
 * @Time 2016-11-26 01:31:58
 * Copyright by LuYuanliang.
 */

package org.web.autobuild.domain.code;

import java.util.Date;

import org.web.autobuild.tool.BuildCodeTool;

public class CodeAttributeDO {

	// 主键
	private Integer codeAttributeId = null;
	// 一组信息的唯一标识,默认是表名
	private String mark = null;
	// 表字段名
	private String columnName = null;
	// 表字段类型
	private String columnType = null;
	// 表字段长度
	private String columnSize = null;
	// 是否允许为空
	private String allowNull = null;
	// 描述信息
	private String description = null;
	// java 属性名
	private String attributeName = null;
	// java 属性类型
	private String attributeType = null;
	// 是否支持equal查询
	private String equalSupport = null;
	// 是否支持IN查询
	private String inSupport = null;
	// 是否支持比较查询
	private String compareSupport = null;
	// 是否支持模糊查询
	private String indistinctSupport = null;
	// 是否是主键
	private String isPrimary = null;
	// 顺序
	private Integer sortOrder = null;
	// 创建时间
	private Date inputTime = null;

	public void setCodeAttributeId(Integer codeAttributeId) {
		this.codeAttributeId = codeAttributeId;
	}

	public Integer getCodeAttributeId() {
		return codeAttributeId;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public String getMark() {
		return mark;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}

	public String getColumnType() {
		return columnType;
	}

	public void setColumnSize(String columnSize) {
		this.columnSize = columnSize;
	}

	public String getColumnSize() {
		return columnSize;
	}

	public void setAllowNull(String allowNull) {
		this.allowNull = allowNull;
	}

	public String getAllowNull() {
		return allowNull;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	public String getAttributeName() {
		return attributeName;
	}

	public void setAttributeType(String attributeType) {
		this.attributeType = attributeType;
	}

	public String getAttributeType() {
		return attributeType;
	}

	public void setEqualSupport(String equalSupport) {
		this.equalSupport = equalSupport;
	}

	public String getEqualSupport() {
		return equalSupport;
	}

	public void setInSupport(String inSupport) {
		this.inSupport = inSupport;
	}

	public String getInSupport() {
		return inSupport;
	}

	public String getCompareSupport() {
		return compareSupport;
	}

	public void setCompareSupport(String compareSupport) {
		this.compareSupport = compareSupport;
	}

	public void setIndistinctSupport(String indistinctSupport) {
		this.indistinctSupport = indistinctSupport;
	}

	public String getIndistinctSupport() {
		return indistinctSupport;
	}

	public void setIsPrimary(String isPrimary) {
		this.isPrimary = isPrimary;
	}

	public String getIsPrimary() {
		return isPrimary;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

	public Integer getSortOrder() {
		return sortOrder;
	}

	public void setInputTime(Date inputTime) {
		this.inputTime = inputTime;
	}

	public Date getInputTime() {
		return inputTime;
	}

	public String getUpperAttributeName() {
		return BuildCodeTool.initUpper(attributeName);
	}
}
