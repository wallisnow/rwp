package com.congxing.system.syscode.service;

import java.util.List;
import java.util.Map;

import com.congxing.core.service.CommonService;
import com.congxing.system.syscode.model.SysCodeVO;

public interface SysCodeService extends CommonService {
	
	/**
	 * 查询 kind <> '*' 信息
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public List<SysCodeVO> doFindSysCodeVOByType(String type) throws Exception;
	
	/**
	 * 查询 kind <> '*' 信息,并包含其他查询条件信息
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public List<SysCodeVO> doFindSysCodeVOByType(String type, Map<String, Object>properties) throws Exception;
	
	/**
	 * 查询所有type信息,包括kind='*'
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public List<SysCodeVO> doFindSysCodeVOByTypeAll(String type) throws Exception;
	
	/**
	 * 根据type和kind查找SysCodeVO对象
	 * @param type
	 * @param kind
	 * @return
	 * @throws Exception
	 */
	public SysCodeVO doFindSysCodeVOByTypeAndKind(String type, String kind) throws Exception;

}
