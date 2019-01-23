
/**
* PageAttributeDO entity encapsulation table page_attribute record. 
* @author Eclipse Tools Generate.
* @Time 2016-11-30 14:27:54
* Copyright by luyl
*/

package org.web.autobuild.domain;


public class PageAttributeDO {

	// 主键
	private Long pageAttributeId;
	// 视图标识
	private String viewMark;
	// 属性名称
	private String attribute;
	// 显示别名
	private String alias;
	// 显示方式
	private String type;
	// 长度
	private Long length;
	// 是否做为查询条件
	private String queryTag;
	// 是否列表展示
	private String listTag;
	// 是否支持增加
	private String addTag;
	// 是否详情页面展示
	private String detailTag;
	// 是否修改页面展示
	private String updateTag;
	

	public void setPageAttributeId(Long pageAttributeId){
		this.pageAttributeId = pageAttributeId;
	}

	public Long getPageAttributeId(){
		return pageAttributeId;
	}

	public void setViewMark(String viewMark){
		this.viewMark = viewMark;
	}

	public String getViewMark(){
		return viewMark;
	}

	public void setAttribute(String attribute){
		this.attribute = attribute;
	}

	public String getAttribute(){
		return attribute;
	}

	public void setAlias(String alias){
		this.alias = alias;
	}

	public String getAlias(){
		return alias;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}

	public void setLength(Long length){
		this.length = length;
	}

	public Long getLength(){
		return length;
	}

	public void setQueryTag(String queryTag){
		this.queryTag = queryTag;
	}

	public String getQueryTag(){
		return queryTag;
	}

	public void setListTag(String listTag){
		this.listTag = listTag;
	}

	public String getListTag(){
		return listTag;
	}

	public void setAddTag(String addTag){
		this.addTag = addTag;
	}

	public String getAddTag(){
		return addTag;
	}

	public void setDetailTag(String detailTag){
		this.detailTag = detailTag;
	}

	public String getDetailTag(){
		return detailTag;
	}

	public void setUpdateTag(String updateTag){
		this.updateTag = updateTag;
	}

	public String getUpdateTag(){
		return updateTag;
	}

	
}




