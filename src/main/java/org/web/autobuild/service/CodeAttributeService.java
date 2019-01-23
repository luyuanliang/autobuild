/**
 * @Description CodeAttributeServiceImpl is generate by Tools. 
 * @author luyl
 * @Time  2016-11-26 01:14:41
 */

package org.web.autobuild.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.web.autobuild.dao.CodeAttributeDAO;
import org.web.autobuild.domain.ServiceException;
import org.web.autobuild.domain.code.CodeAttributeDO;
import org.web.autobuild.query.QueryBase;
import org.web.autobuild.query.QueryCodeAttributeDO;

@Service("codeAttributeService")
public class CodeAttributeService {

	private static Logger logger = Logger.getLogger(CodeAttributeService.class);

	@Resource
	private CodeAttributeDAO codeAttributeDAO;

	public CodeAttributeDO queryCodeAttributeByCodeAttributeId(Integer codeAttributeId) throws ServiceException {
		if (codeAttributeId == null) {
			throw new ServiceException("PARAM_IS_EMPTY", "Query is null.");
		}
		return codeAttributeDAO.queryCodeAttributeByCodeAttributeId(codeAttributeId);
	}

	public List<CodeAttributeDO> queryCodeAttributeList(QueryCodeAttributeDO queryCodeAttributeDO) throws ServiceException {

		if (queryCodeAttributeDO == null) {
			// TODO
		}

		return codeAttributeDAO.queryCodeAttributeList(queryCodeAttributeDO);
	}

	public CodeAttributeDO queryOneCodeAttribute(QueryCodeAttributeDO queryCodeAttributeDO) throws ServiceException {
		queryCodeAttributeDO.setFirstRecord();
		List<CodeAttributeDO> list = queryCodeAttributeList(queryCodeAttributeDO);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	public CodeAttributeDO insertCodeAttribute(CodeAttributeDO codeAttributeDO) throws ServiceException {

		// check params first.
		checkInsert(codeAttributeDO);

		// TODO add default value.

		codeAttributeDAO.insertCodeAttribute(codeAttributeDO);
		return codeAttributeDO;
	}

	public Integer updateCodeAttributeByCodeAttributeId(CodeAttributeDO codeAttributeDO) throws ServiceException {
		// check params first.
		checkUpdate(codeAttributeDO);

		return codeAttributeDAO.updateCodeAttributeByCodeAttributeId(codeAttributeDO);
	}

	public Map<String,CodeAttributeDO> queryCodeAttributeMap(QueryCodeAttributeDO queryCodeAttributeDO) throws ServiceException {
		Map<String,CodeAttributeDO> map = new HashMap<String,CodeAttributeDO>();
		List<CodeAttributeDO> list = queryCodeAttributeList(queryCodeAttributeDO);
		if (list != null && list.size() > 0) {
			for (CodeAttributeDO codeAttributeDO : list) {
				map.put(codeAttributeDO.getColumnName(), codeAttributeDO);
			}
		}
		return map;
	}
	
	public Integer countCodeAttributeList(QueryCodeAttributeDO queryCodeAttributeDO) throws ServiceException {
		return codeAttributeDAO.countCodeAttributeList(queryCodeAttributeDO);
	}

	/***
	 * According to DB info. check attribute allow empty or not, and check
	 * attribute's length is over upper limit of length or not. and this method
	 * is generate by tools.
	 **/
	@SuppressWarnings({ "deprecation" })
	private void checkInsert(CodeAttributeDO codeAttributeDO) throws ServiceException {
		if (codeAttributeDO == null) {
		} else if (StringUtils.isEmpty(codeAttributeDO.getMark())
				|| (StringUtils.isNotEmpty(codeAttributeDO.getMark()) && codeAttributeDO.getMark().length() > 255)) {
			throw new ServiceException("PARAM_IS_INVALID", "mark is null or out of range, Upper limit of length is 255");
		} else if (StringUtils.isEmpty(codeAttributeDO.getColumnName())
				|| (StringUtils.isNotEmpty(codeAttributeDO.getColumnName()) && codeAttributeDO.getColumnName().length() > 255)) {
			throw new ServiceException("PARAM_IS_INVALID", "columnName is null or out of range, Upper limit of length is 255");
		} else if (StringUtils.isEmpty(codeAttributeDO.getColumnType())
				|| (StringUtils.isNotEmpty(codeAttributeDO.getColumnType()) && codeAttributeDO.getColumnType().length() > 255)) {
			throw new ServiceException("PARAM_IS_INVALID", "columnType is null or out of range, Upper limit of length is 255");
		} else if (StringUtils.isEmpty(codeAttributeDO.getColumnSize())
				|| (StringUtils.isNotEmpty(codeAttributeDO.getColumnSize()) && codeAttributeDO.getColumnSize().length() > 255)) {
			throw new ServiceException("PARAM_IS_INVALID", "columnSize is null or out of range, Upper limit of length is 255");
		} else if (StringUtils.isEmpty(codeAttributeDO.getAllowNull())
				|| (StringUtils.isNotEmpty(codeAttributeDO.getAllowNull()) && codeAttributeDO.getAllowNull().length() > 3)) {
			throw new ServiceException("PARAM_IS_INVALID", "allowNull is null or out of range, Upper limit of length is 3");
		} else if (StringUtils.isEmpty(codeAttributeDO.getDescription())
				|| (StringUtils.isNotEmpty(codeAttributeDO.getDescription()) && codeAttributeDO.getDescription().length() > 500)) {
			throw new ServiceException("PARAM_IS_INVALID", "description is null or out of range, Upper limit of length is 500");
		} else if (StringUtils.isEmpty(codeAttributeDO.getAttributeName())
				|| (StringUtils.isNotEmpty(codeAttributeDO.getAttributeName()) && codeAttributeDO.getAttributeName().length() > 255)) {
			throw new ServiceException("PARAM_IS_INVALID", "attributeName is null or out of range, Upper limit of length is 255");
		} else if (StringUtils.isEmpty(codeAttributeDO.getAttributeType())
				|| (StringUtils.isNotEmpty(codeAttributeDO.getAttributeType()) && codeAttributeDO.getAttributeType().length() > 255)) {
			throw new ServiceException("PARAM_IS_INVALID", "attributeType is null or out of range, Upper limit of length is 255");
		} else if (StringUtils.isEmpty(codeAttributeDO.getEqualSupport())
				|| (StringUtils.isNotEmpty(codeAttributeDO.getEqualSupport()) && codeAttributeDO.getEqualSupport().length() > 3)) {
			throw new ServiceException("PARAM_IS_INVALID", "equalSupport is null or out of range, Upper limit of length is 3");
		} else if (StringUtils.isEmpty(codeAttributeDO.getCompareSupport())
				|| (StringUtils.isNotEmpty(codeAttributeDO.getCompareSupport()) && codeAttributeDO.getCompareSupport().length() > 3)) {
			throw new ServiceException("PARAM_IS_INVALID", "thanSupport is null or out of range, Upper limit of length is 3");
		} else if (StringUtils.isEmpty(codeAttributeDO.getIndistinctSupport())
				|| (StringUtils.isNotEmpty(codeAttributeDO.getIndistinctSupport()) && codeAttributeDO.getIndistinctSupport().length() > 3)) {
			throw new ServiceException("PARAM_IS_INVALID", "indistinctSupport is null or out of range, Upper limit of length is 3");
		} else if (StringUtils.isEmpty(codeAttributeDO.getIsPrimary())
				|| (StringUtils.isNotEmpty(codeAttributeDO.getIsPrimary()) && codeAttributeDO.getIsPrimary().length() > 3)) {
			throw new ServiceException("PARAM_IS_INVALID", "isPrimary is null or out of range, Upper limit of length is 0");
		} else if (codeAttributeDO.getSortOrder() == null
				|| (codeAttributeDO.getSortOrder() != null && String.valueOf(codeAttributeDO.getSortOrder()).length() > 20)) {
			throw new ServiceException("PARAM_IS_INVALID", "sortOrder is null or out of range, Upper limit of length is 20");
		}
	}

	@SuppressWarnings({ "deprecation" })
	private void checkUpdate(CodeAttributeDO codeAttributeDO) throws ServiceException {
		if (codeAttributeDO.getCodeAttributeId() == null) {
			throw new ServiceException("UPDATE_ERROR", "codeAttributeId can't be null.");
		} else if (codeAttributeDO.getCodeAttributeId() != null && String.valueOf(codeAttributeDO.getCodeAttributeId()).length() > 11) {
			throw new ServiceException("UPDATE_ERROR", "codeAttributeId is out of range, Upper limit of length is 11");
		} else if (StringUtils.isNotEmpty(codeAttributeDO.getMark()) && codeAttributeDO.getMark().length() > 255) {
			throw new ServiceException("UPDATE_ERROR", "mark is out of range, Upper limit of length is 255");
		} else if (StringUtils.isNotEmpty(codeAttributeDO.getColumnName()) && codeAttributeDO.getColumnName().length() > 255) {
			throw new ServiceException("UPDATE_ERROR", "columnName is out of range, Upper limit of length is 255");
		} else if (StringUtils.isNotEmpty(codeAttributeDO.getColumnType()) && codeAttributeDO.getColumnType().length() > 255) {
			throw new ServiceException("UPDATE_ERROR", "columnType is out of range, Upper limit of length is 255");
		} else if (StringUtils.isNotEmpty(codeAttributeDO.getColumnSize()) && codeAttributeDO.getColumnSize().length() > 255) {
			throw new ServiceException("UPDATE_ERROR", "columnSize is out of range, Upper limit of length is 255");
		} else if (StringUtils.isNotEmpty(codeAttributeDO.getAllowNull()) && codeAttributeDO.getAllowNull().length() > 3) {
			throw new ServiceException("UPDATE_ERROR", "allowNull is out of range, Upper limit of length is 3");
		} else if (StringUtils.isNotEmpty(codeAttributeDO.getDescription()) && codeAttributeDO.getDescription().length() > 500) {
			throw new ServiceException("UPDATE_ERROR", "description is out of range, Upper limit of length is 500");
		} else if (StringUtils.isNotEmpty(codeAttributeDO.getAttributeName()) && codeAttributeDO.getAttributeName().length() > 255) {
			throw new ServiceException("UPDATE_ERROR", "attributeName is out of range, Upper limit of length is 255");
		} else if (StringUtils.isNotEmpty(codeAttributeDO.getAttributeType()) && codeAttributeDO.getAttributeType().length() > 255) {
			throw new ServiceException("UPDATE_ERROR", "attributeType is out of range, Upper limit of length is 255");
		} else if (StringUtils.isNotEmpty(codeAttributeDO.getEqualSupport()) && codeAttributeDO.getEqualSupport().length() > 3) {
			throw new ServiceException("UPDATE_ERROR", "equalSupport is out of range, Upper limit of length is 3");
		} else if (StringUtils.isNotEmpty(codeAttributeDO.getCompareSupport()) && codeAttributeDO.getCompareSupport().length() > 3) {
			throw new ServiceException("UPDATE_ERROR", "thanSupport is out of range, Upper limit of length is 3");
		} else if (StringUtils.isNotEmpty(codeAttributeDO.getIndistinctSupport()) && codeAttributeDO.getIndistinctSupport().length() > 3) {
			throw new ServiceException("UPDATE_ERROR", "indistinctSupport is out of range, Upper limit of length is 3");
		} else if (StringUtils.isNotEmpty(codeAttributeDO.getIsPrimary()) && codeAttributeDO.getIsPrimary().length() > 0) {
			throw new ServiceException("UPDATE_ERROR", "isPrimary is out of range, Upper limit of length is 0");
		} else if (codeAttributeDO.getSortOrder() != null && String.valueOf(codeAttributeDO.getSortOrder()).length() > 20) {
			throw new ServiceException("UPDATE_ERROR", "sortOrder is out of range, Upper limit of length is 20");
		}
	}
}
