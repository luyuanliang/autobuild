/**
 * @Description $buildCodeRequest.getEntity()ServiceImpl is generate by Tools. 
 * @author luyl
 * @time 2016-11-30 12:50:07
 */

package org.web.autobuild.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.web.autobuild.dao.PageAttributeDAO;
import org.web.autobuild.domain.PageAttributeDO;
import org.web.autobuild.domain.ServiceException;
import org.web.autobuild.query.QueryPageAttribute;

@Service("pageAttributeService")
public class PageAttributeService {

	private static Logger logger = Logger.getLogger(PageAttributeService.class);

	@Resource
	private PageAttributeDAO pageAttributeDAO;

	/**
	 * @Decription 根据主键查询记录
	 * @param pageAttributeId
	 * @author luyl
	 * @date 2016-11-30 12:50:07
	 * @return 返回唯一记录PageAttribute.
	 */
	public PageAttributeDO selectPageAttributeByPageAttributeId(Long pageAttributeId) throws ServiceException {
		if (pageAttributeId == null) {
			throw new ServiceException("PARAM_IS_EMPTY", "Query is null.");
		}
		return pageAttributeDAO.selectPageAttributeByPageAttributeId(pageAttributeId);
	}

	/**
	 * @Decription 根据查询条件,返回List.
	 * @param queryPageAttribute
	 *            封装了查询条件对象.
	 * @author luyl
	 * @date 2016-11-30 12:50:07
	 * @return 返回一组记录.
	 */
	public List<PageAttributeDO> selectPageAttributeList(QueryPageAttribute queryPageAttribute) throws ServiceException {

		if (queryPageAttribute == null) {
			throw new ServiceException("PARAM_IS_EMPTY", "Query is null.");
		}
		// TODO
		return pageAttributeDAO.selectPageAttributeList(queryPageAttribute);
	}

	/**
	 * @Decription 根据查询条件,返回第一条记录.
	 * @param queryPageAttribute
	 *            封装了查询条件对象.
	 * @author luyl
	 * @date 2016-11-30 12:50:07
	 * @return 返回第一条记录.
	 */
	public PageAttributeDO selectOnePageAttribute(QueryPageAttribute queryPageAttribute) throws ServiceException {
		queryPageAttribute.setFirstRecord();
		List<PageAttributeDO> list = pageAttributeDAO.selectPageAttributeList(queryPageAttribute);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * @Decription 根据查询条件,查询满足条件的记录数.
	 * @param queryPageAttribute
	 *            封装了查询条件对象.
	 * @author luyl
	 * @date 2016-11-30 12:50:07
	 * @return 返回查询条件返回的记录总数.
	 */
	public Integer countPageAttributeList(QueryPageAttribute queryPageAttribute) throws ServiceException {
		if (queryPageAttribute == null) {
			throw new ServiceException("PARAM_IS_EMPTY", "Query is null.");
		}
		return pageAttributeDAO.countPageAttributeList(queryPageAttribute);
	}

	/**
	 * @Decription 插入一条新记录.
	 * @param pageAttributeDO
	 *            封装新增的对象.
	 * @author luyl
	 * @date 2016-11-30 12:50:07
	 * @return 返回原始对象，如果用到数据库自增主键，并会自动设置新增主键.
	 */
	public PageAttributeDO insertPageAttribute(PageAttributeDO pageAttributeDO) throws ServiceException {
		// check params first.
		checkInsert(pageAttributeDO);

		// TODO add default value.

		pageAttributeDAO.insertPageAttribute(pageAttributeDO);
		return pageAttributeDO;
	}

	/**
	 * @Decription 根据主键修改记录.
	 * @param $buildCodeRequest
	 *            .getEntityName() 封装修改的对象.
	 * @author $buildCodeRequest.getCreateUser()
	 * @date 2016-11-30 12:50:07
	 * @return 返回修改记录数.
	 */
	public int updatePageAttributeByPageAttributeId(PageAttributeDO pageAttributeDO) throws ServiceException {
		// check params first.
		checkUpdate(pageAttributeDO);

		return pageAttributeDAO.updatePageAttributeByPageAttributeId(pageAttributeDO);

	}

	/**
	 * According to DB info. check attribute allow empty or not, and check
	 * attribute's length is over upper limit of length or not. and this method
	 * is generate by tools.
	 */
	@SuppressWarnings({ "deprecation" })
	private void checkInsert(PageAttributeDO pageAttributeDO) throws ServiceException {
		if (pageAttributeDO == null) {
			throw new ServiceException("PARAM_IS_EMPTY", "Query is null.");
		} else if (StringUtils.isEmpty(pageAttributeDO.getViewMark())
				|| (StringUtils.isNotEmpty(pageAttributeDO.getViewMark()) && pageAttributeDO.getViewMark().length() > 255)) {
			throw new ServiceException("PARAM_IS_INVALID", "ViewMark is null or out of range, Upper limit of length is 255");
		} else if (StringUtils.isEmpty(pageAttributeDO.getAttribute())
				|| (StringUtils.isNotEmpty(pageAttributeDO.getAttribute()) && pageAttributeDO.getAttribute().length() > 255)) {
			throw new ServiceException("PARAM_IS_INVALID", "Attribute is null or out of range, Upper limit of length is 255");
		} else if (StringUtils.isEmpty(pageAttributeDO.getAlias())
				|| (StringUtils.isNotEmpty(pageAttributeDO.getAlias()) && pageAttributeDO.getAlias().length() > 255)) {
			throw new ServiceException("PARAM_IS_INVALID", "Alias is null or out of range, Upper limit of length is 255");
		} else if (StringUtils.isEmpty(pageAttributeDO.getType())
				|| (StringUtils.isNotEmpty(pageAttributeDO.getType()) && pageAttributeDO.getType().length() > 8)) {
			throw new ServiceException("PARAM_IS_INVALID", "Type is null or out of range, Upper limit of length is 8");
		} else if (pageAttributeDO.getLength() == null || (pageAttributeDO.getLength() != null && String.valueOf(pageAttributeDO.getLength()).length() > 11)) {
			throw new ServiceException("PARAM_IS_INVALID", "Length is null or out of range, Upper limit of length is 11");
		} else if (StringUtils.isEmpty(pageAttributeDO.getQueryTag())
				|| (StringUtils.isNotEmpty(pageAttributeDO.getQueryTag()) && pageAttributeDO.getQueryTag().length() > 3)) {
			throw new ServiceException("PARAM_IS_INVALID", "QueryTag is null or out of range, Upper limit of length is 3");
		} else if (StringUtils.isEmpty(pageAttributeDO.getListTag())
				|| (StringUtils.isNotEmpty(pageAttributeDO.getListTag()) && pageAttributeDO.getListTag().length() > 3)) {
			throw new ServiceException("PARAM_IS_INVALID", "ListTag is null or out of range, Upper limit of length is 3");
		} else if (StringUtils.isEmpty(pageAttributeDO.getDetailTag())
				|| (StringUtils.isNotEmpty(pageAttributeDO.getDetailTag()) && pageAttributeDO.getDetailTag().length() > 3)) {
			throw new ServiceException("PARAM_IS_INVALID", "DetailTag is null or out of range, Upper limit of length is 3");
		} else if (StringUtils.isEmpty(pageAttributeDO.getUpdateTag())
				|| (StringUtils.isNotEmpty(pageAttributeDO.getUpdateTag()) && pageAttributeDO.getUpdateTag().length() > 6)) {
			throw new ServiceException("PARAM_IS_INVALID", "UpdateTag is null or out of range, Upper limit of length is 6");
		}

	}

	@SuppressWarnings({ "deprecation" })
	private void checkUpdate(PageAttributeDO pageAttributeDO) throws ServiceException {
		if (pageAttributeDO == null) {
			throw new ServiceException("PARAM_IS_EMPTY", "Query is null.");
		} else if (pageAttributeDO.getPageAttributeId() != null && String.valueOf(pageAttributeDO.getPageAttributeId()).length() > 11) {
			throw new ServiceException("UPDATE_ERROR", "$entity.getAttributeName() is out of range, Upper limit of length is $entity.getLength()");
		} else if (StringUtils.isNotEmpty(pageAttributeDO.getViewMark()) && pageAttributeDO.getViewMark().length() > 255) {
			throw new ServiceException("UPDATE_ERROR", "$entity.getAttributeName() is out of range, Upper limit of length is $entity.getLength()");
		} else if (StringUtils.isNotEmpty(pageAttributeDO.getAttribute()) && pageAttributeDO.getAttribute().length() > 255) {
			throw new ServiceException("UPDATE_ERROR", "$entity.getAttributeName() is out of range, Upper limit of length is $entity.getLength()");
		} else if (StringUtils.isNotEmpty(pageAttributeDO.getAlias()) && pageAttributeDO.getAlias().length() > 255) {
			throw new ServiceException("UPDATE_ERROR", "$entity.getAttributeName() is out of range, Upper limit of length is $entity.getLength()");
		} else if (StringUtils.isNotEmpty(pageAttributeDO.getType()) && pageAttributeDO.getType().length() > 8) {
			throw new ServiceException("UPDATE_ERROR", "$entity.getAttributeName() is out of range, Upper limit of length is $entity.getLength()");
		} else if (pageAttributeDO.getLength() != null && String.valueOf(pageAttributeDO.getLength()).length() > 11) {
			throw new ServiceException("UPDATE_ERROR", "$entity.getAttributeName() is out of range, Upper limit of length is $entity.getLength()");
		} else if (StringUtils.isNotEmpty(pageAttributeDO.getQueryTag()) && pageAttributeDO.getQueryTag().length() > 3) {
			throw new ServiceException("UPDATE_ERROR", "$entity.getAttributeName() is out of range, Upper limit of length is $entity.getLength()");
		} else if (StringUtils.isNotEmpty(pageAttributeDO.getListTag()) && pageAttributeDO.getListTag().length() > 3) {
			throw new ServiceException("UPDATE_ERROR", "$entity.getAttributeName() is out of range, Upper limit of length is $entity.getLength()");
		} else if (StringUtils.isNotEmpty(pageAttributeDO.getDetailTag()) && pageAttributeDO.getDetailTag().length() > 3) {
			throw new ServiceException("UPDATE_ERROR", "$entity.getAttributeName() is out of range, Upper limit of length is $entity.getLength()");
		} else if (StringUtils.isNotEmpty(pageAttributeDO.getUpdateTag()) && pageAttributeDO.getUpdateTag().length() > 6) {
			throw new ServiceException("UPDATE_ERROR", "$entity.getAttributeName() is out of range, Upper limit of length is $entity.getLength()");
		}
	}
}
