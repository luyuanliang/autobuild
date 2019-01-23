package org.web.autobuild.dao;

import java.util.List;
import org.web.autobuild.query.QueryPageAttribute;
import org.web.autobuild.domain.PageAttributeDO;

public interface PageAttributeDAO {

	public PageAttributeDO selectPageAttributeByPageAttributeId(Long pageAttributeId);

	public List< PageAttributeDO > selectPageAttributeList(QueryPageAttribute queryPageAttribute);

	public Integer countPageAttributeList(QueryPageAttribute queryPageAttribute);

	public int insertPageAttribute(PageAttributeDO pageAttributeDO);

	public int updatePageAttributeByPageAttributeId(PageAttributeDO pageAttributeDO);

}

