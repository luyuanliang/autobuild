package org.web.autobuild.dao;

import java.util.List;

import org.web.autobuild.domain.code.CodeAttributeDO;
import org.web.autobuild.query.QueryCodeAttributeDO;



public interface CodeAttributeDAO {

	public CodeAttributeDO queryCodeAttributeByCodeAttributeId(Integer codeAttributeId) ;

	public List< CodeAttributeDO >queryCodeAttributeList(QueryCodeAttributeDO queryCodeAttributeDO) ;

	public Integer countCodeAttributeList(QueryCodeAttributeDO queryCodeAttributeDO) ;

	public int insertCodeAttribute(CodeAttributeDO codeAttributeDO) ;

	public int updateCodeAttributeByCodeAttributeId(CodeAttributeDO codeAttributeDO) ;

	public int deleteRecordByMark(String mark) ;
}

