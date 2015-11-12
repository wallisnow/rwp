package com.congxing.system.generalconfig.dao;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.congxing.core.hibernate.DAOFactory;
import com.congxing.core.hibernate.HibernateDAO;
import com.congxing.core.hibernate.Strategy;
import com.congxing.core.web.constant.Constants;
import com.congxing.core.web.sequence.Sequence;
import com.congxing.system.generalconfig.model.GeneralConfigVO;
import com.congxing.system.generalconfig.model.PreviewConfigVO;
import com.congxing.system.user.model.UserLogVO;
import com.congxing.system.user.model.UserVO;

public class UserConfigFormStrategy implements Strategy {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private GeneralConfigVO generalConfigVO;

	private List<PreviewConfigVO> varFieldConfList;

	private UserVO userVO;

	private String oprType = Constants.OPRTYPE_ADD;

	public UserConfigFormStrategy(GeneralConfigVO generalConfigVO, List<PreviewConfigVO> varFieldConfList,
			UserVO userVO) {
		super();
		this.varFieldConfList = varFieldConfList;
		this.generalConfigVO = generalConfigVO;
		this.userVO = userVO;
	}

	@SuppressWarnings("null")
	@Override
	public Object process() throws Exception {
		HibernateDAO generalConfigDAO = DAOFactory.getInstance().createHibernateDAO(GeneralConfigVO.class);
		HibernateDAO generalConfigLogDAO = DAOFactory.getInstance().createHibernateDAO(GeneralConfigVO.class);
		HibernateDAO varFieldConfDAO = DAOFactory.getInstance().createHibernateDAO(PreviewConfigVO.class);
		HibernateDAO varFieldConfLogDAO = DAOFactory.getInstance().createHibernateDAO(PreviewConfigVO.class);

		// 不可插入相同的报表id
		if (Constants.OPRTYPE_ADD.equals(oprType)) {
			List<?> list = generalConfigDAO.findByHQL(" from GeneralConfigVO where generalconfigId = ? ",
					new Object[] { generalConfigVO.getGeneralconfigId() });
			if (null != list && list.size() > 0) {
				throw new Exception("此报表已经存在!");
			}
		}

		// 报表名称不可以相同
		if (Constants.OPRTYPE_ADD.equals(oprType)) {
			List<?> listFromName = generalConfigDAO.findByHQL(" from GeneralConfigVO where formname = ? ",
					new Object[] { generalConfigVO.getFormname() });
			if (null != listFromName && listFromName.size() > 0) {
				throw new Exception("报自定义报表表名不能重复!");
			}
		}

		// 不可插入相同id的config
		if (Constants.OPRTYPE_ADD.equals(oprType)) {
			Iterator<PreviewConfigVO> it = varFieldConfList.iterator();
			if (it.hasNext()) {
				List<?> listc = varFieldConfDAO.findByHQL(" from PreviewConfigVO where generalConfigData_id = ? ",
						new Object[] { it.next().getGeneralConfigData_id() });
				if (null != listc && listc.size() > 0) {
					throw new Exception("此配置已经存在！");
				}
			}
		}

		UserLogVO logVO = new UserLogVO();
		logVO.setLogId(Sequence.getSequence());
		logVO.setOprCode(userVO.getUserId());
		logVO.setOprTime(new Date());
		logVO.setOprType(oprType);

		if (Constants.OPRTYPE_ADD.equals(oprType)) {
			BeanUtils.copyProperties(logVO, generalConfigVO);
			generalConfigLogDAO.save(logVO);
			generalConfigDAO.save(generalConfigVO);

			varFieldConfLogDAO.save(logVO);
			for (PreviewConfigVO previewCfgVO : varFieldConfList) {
				BeanUtils.copyProperties(logVO, previewCfgVO);
				varFieldConfLogDAO.save(logVO);
				varFieldConfDAO.save(previewCfgVO);
			}

		} else if (Constants.OPRTYPE_MODIFY.equals(oprType)) {
			GeneralConfigVO gc = (GeneralConfigVO) generalConfigDAO.findByPK(generalConfigVO.getGeneralconfigId());

			if (gc == null) {
				throw new Exception("自定义报表 " + "[" + gc.getGeneralconfigId() + "]不存在,修改失败");
			}

			for (PreviewConfigVO pcf : varFieldConfList) {
				PreviewConfigVO pc = (PreviewConfigVO) varFieldConfDAO.findByPK(pcf.getGeneralConfigData_id());
				if (pc == null) {
					throw new Exception("自定义报表配置 " + "[" + pcf.getGeneralConfigData_id() + "]不存在,修改失败");
				}
			}

			BeanUtils.copyProperties(logVO, generalConfigVO);
			generalConfigLogDAO.save(logVO);

			generalConfigDAO.getSession().merge(generalConfigVO);
			for (PreviewConfigVO previewCfgVO : varFieldConfList) {

				BeanUtils.copyProperties(logVO, previewCfgVO);
				varFieldConfLogDAO.save(logVO);

				varFieldConfDAO.getSession().merge(previewCfgVO);
			}

		} else {
			throw new Exception("操作标识错误");
		}

		return null;
	}

	public GeneralConfigVO getGeneralConfigVO() {
		return generalConfigVO;
	}

	public void setGeneralConfigVO(GeneralConfigVO generalConfigVO) {
		this.generalConfigVO = generalConfigVO;
	}

	public List<PreviewConfigVO> getVarFieldConfList() {
		return varFieldConfList;
	}

	public void setVarFieldConfList(List<PreviewConfigVO> varFieldConfList) {
		this.varFieldConfList = varFieldConfList;
	}

	public UserVO getUserVO() {
		return userVO;
	}

	public void setUserVO(UserVO userVO) {
		this.userVO = userVO;
	}

	public String getOprType() {
		return oprType;
	}

	public void setOprType(String oprType) {
		this.oprType = oprType;
	}

}
