package org.web.autobuild.domain;

import java.util.List;
import java.util.Map;

import org.web.autobuild.tool.BuildCodeTool;

public class BuildPageRequestDO {

	private Map<String, PageAttributeDO> map;
	private List<String> list;

	private String primaryKey;
	private String webName;
	private String moduleName;
	private String actionName;

	public String getWebName() {
		return webName;
	}

	public void setWebName(String webName) {
		this.webName = webName;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public Map<String, PageAttributeDO> getMap() {
		return map;
	}

	public void setMap(Map<String, PageAttributeDO> map) {
		this.map = map;
	}

	public List<String> getList() {
		return list;
	}

	public void setList(List<String> list) {
		this.list = list;
	}

	public String getPrimaryKey() {
		return BuildCodeTool.initLower(primaryKey);
	}

	public String getUpperPrimaryKey() {
		return BuildCodeTool.initUpper(primaryKey);
	}

	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

}
