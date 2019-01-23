package org.web.autobuild.domain.code;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class BuildCodeRequestDO {

	private PackageInfoDO packageInfoDO;

	private EntityNameDO entityNameDO;

	private List<String> list;

	private CodeAttributeDO primaryCodeAttributeDO;

	private Map<String, CodeAttributeDO> map;

	private String timeValue;
	private String owner;
	private String path;
	private String mark;
	private boolean hasUpdateVersion =false;

	public PackageInfoDO getPackageInfoDO() {
		return packageInfoDO;
	}

	public void setPackageInfoDO(PackageInfoDO packageInfoDO) {
		this.packageInfoDO = packageInfoDO;
	}

	public EntityNameDO getEntityNameDO() {
		return entityNameDO;
	}

	public void setEntityNameDO(EntityNameDO entityNameDO) {
		this.entityNameDO = entityNameDO;
	}

	public List<String> getList() {
		return list;
	}

	public void setList(List<String> list) {
		this.list = list;
	}

	public CodeAttributeDO getPrimaryCodeAttributeDO() {
		return primaryCodeAttributeDO;
	}

	public void setPrimaryCodeAttributeDO(CodeAttributeDO primaryCodeAttributeDO) {
		this.primaryCodeAttributeDO = primaryCodeAttributeDO;
	}

	public Map<String, CodeAttributeDO> getMap() {
		return map;
	}

	public void setMap(Map<String, CodeAttributeDO> map) {
		this.map = map;
	}

	public String getTimeValue() {
		return timeValue;
	}

	public void setTimeValue(String timeValue) {
		this.timeValue = timeValue;
	}

	public String getOwner() {
		if (StringUtils.isEmpty(owner)) {
			return "luyl";
		}
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public boolean isHasUpdateVersion() {
		return hasUpdateVersion;
	}

	public void setHasUpdateVersion(boolean hasUpdateVersion) {
		this.hasUpdateVersion = hasUpdateVersion;
	}


	
}
