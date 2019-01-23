package org.web.autobuild.domain.code;

import org.web.autobuild.tool.BuildCodeTool;

public class EntityNameDO {

	private String entityName;
	private String domainEntityName;
	private String daoEntityName;
	private String queryEntityName;
	private String serviceEntityName;
	private String tableName;

	private String insertMethod;
	private String countMethod;
	private String updateMethod;
	private String queryListMethod;
	private String queryByIdMethod;

	public String getInsertMethod() {
		return insertMethod;
	}

	public void setInsertMethod(String insertMethod) {
		this.insertMethod = insertMethod;
	}

	public String getCountMethod() {
		return countMethod;
	}

	public void setCountMethod(String countMethod) {
		this.countMethod = countMethod;
	}

	public String getUpdateMethod() {
		return updateMethod;
	}

	public void setUpdateMethod(String updateMethod) {
		this.updateMethod = updateMethod;
	}

	public String getQueryListMethod() {
		return queryListMethod;
	}

	public void setQueryListMethod(String queryListMethod) {
		this.queryListMethod = queryListMethod;
	}

	public String getQueryByIdMethod() {
		return queryByIdMethod;
	}

	public void setQueryByIdMethod(String queryByIdMethod) {
		this.queryByIdMethod = queryByIdMethod;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public String getDaoEntityName() {
		return daoEntityName;
	}
	public String getUpperDaoEntityName() {
		return BuildCodeTool.initUpper(daoEntityName);
	}
	public void setDaoEntityName(String daoEntityName) {
		this.daoEntityName = daoEntityName;
	}

	public String getQueryEntityName() {
		return queryEntityName;
	}

	
	public void setQueryEntityName(String queryEntityName) {
		this.queryEntityName = queryEntityName;
	}

	public String getServiceEntityName() {
		return serviceEntityName;
	}

	public void setServiceEntityName(String serviceEntityName) {
		this.serviceEntityName = serviceEntityName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getDomainEntityName() {
		return domainEntityName;
	}

	public void setDomainEntityName(String domainEntityName) {
		this.domainEntityName = domainEntityName;
	}

	public String getUpperEntityName() {
		return BuildCodeTool.initUpper(entityName);
	}

	public String getUpperDomainEntityName() {
		return BuildCodeTool.initUpper(domainEntityName);
	}


	public String getUpperQueryEntityName() {
		return BuildCodeTool.initUpper(queryEntityName);
	}

	public String getUpperServiceEntityName() {
		return BuildCodeTool.initUpper(serviceEntityName);
	}

	// public String getEntityCLassName() {
	//
	// }
	//
	// public String getEntityCLassName() {
	//
	// }
	//
	// public String getDaosEntityCLassName() {
	//
	// }
	//
	// public String getQueryEntityCLassName() {
	//
	// }
	//
	// public String getServiceEntityCLassName() {
	//
	// }
}
