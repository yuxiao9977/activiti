package com.yx.sm.frame.xtgl.dao;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.yx.sm.frame.xtgl.vo.CommentsVO;
import com.yx.sm.frame.xtgl.vo.LeaveVO;
import com.yx.sm.frame.xtgl.vo.UserVO;

/**
 * 系统管理DAO
 * @author yx
 */
@Repository
public class XtDao {
	private Logger logger = Logger.getLogger(XtDao.class);
	
	@Autowired
	protected SqlSessionTemplate sqlTemplate;
	
	/**
	 * 登录检查
	 * @return
	 */
	public UserVO chkLogin(UserVO user) {
		return sqlTemplate.selectOne("xtglMapper.chkLogin", user);
	}
	
	/**
	 * 保存请假单
	 * @return
	 */
	public void saveLeave(LeaveVO leavevo) {
		sqlTemplate.selectOne("xtglMapper.saveLeave", leavevo);
	}
	
	public void updLeave(LeaveVO leavevo) {
		sqlTemplate.selectOne("xtglMapper.updLeave", leavevo);
	}
	
	public List<LeaveVO> notStartLeave(Map<String, Object> map) {
		return sqlTemplate.selectList("xtglMapper.notStartLeave", map);
	}
	
	public int notStartLeaveTotal() {
		return sqlTemplate.selectOne("xtglMapper.notStartLeaveTotal");
	}
	
	public UserVO getUserById(UserVO uservo) {
		return sqlTemplate.selectOne("xtglMapper.getUserById", uservo);
	}
	
	public LeaveVO getLeaveById(LeaveVO leavevo) {
		return sqlTemplate.selectOne("xtglMapper.getLeaveById", leavevo);
	}
	
	public void saveComment(CommentsVO vo) {
		sqlTemplate.selectOne("xtglMapper.saveComment", vo);
	}
	
	public void updateLeave(LeaveVO vo) {
		sqlTemplate.selectOne("xtglMapper.updateLeave", vo);
	}
	
	public List<CommentsVO> getCommentsByBId(CommentsVO vo) {
		return sqlTemplate.selectList("xtglMapper.getCommentsByBId", vo);
	}
	
	public List<LeaveVO> getLeaveByUserId(LeaveVO vo) {
		return sqlTemplate.selectList("xtglMapper.getLeaveByUserId", vo);
	}
	
	public void delLeaveById(LeaveVO vo) {
		sqlTemplate.selectOne("xtglMapper.delLeaveById", vo);
	}
	
}