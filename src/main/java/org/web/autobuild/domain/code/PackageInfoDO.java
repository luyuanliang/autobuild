package org.web.autobuild.domain.code;

public class PackageInfoDO {
	
	private String queryPackage;
	private String daoPackage;
	private String servicePackage;
	private String domainPackage;
	
	public String getQueryPackage() {
		return queryPackage;
	}
	public void setQueryPackage(String queryPackage) {
		this.queryPackage = queryPackage;
	}
	public String getDaoPackage() {
		return daoPackage;
	}
	public void setDaoPackage(String daoPackage) {
		this.daoPackage = daoPackage;
	}
	public String getServicePackage() {
		return servicePackage;
	}
	public void setServicePackage(String servicePackage) {
		this.servicePackage = servicePackage;
	}
	public String getDomainPackage() {
		return domainPackage;
	}
	public void setDomainPackage(String domainPackage) {
		this.domainPackage = domainPackage;
	}
	
	
}
