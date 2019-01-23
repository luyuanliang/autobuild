
package org.web.autobuild.query;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class QueryPageAttribute extends QueryBase {

	
	private Long pageAttributeId;
					
	private String viewMark;
																																				

	
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
																																				
}

