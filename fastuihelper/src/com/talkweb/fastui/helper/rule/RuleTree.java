/**   
 * 文件名：WTree.java   
 *   
 * 版本信息：   
 * 日期：2011-12-21 下午04:45:34
 * Copyright  Corporation 2011    
 * 版权所有   
 *   
 */

package com.talkweb.fastui.helper.rule;

import java.util.Map;

/**
 * 内容摘要:
 * 
 * @author: HuangYong
 * @version: 1.0
 * @Date: 2011-12-21 下午04:45:34
 * 
 *        修改历史: 修改日期 修改人员 版本 修改内容 ----------------------------------------------
 *        2011-12-21 HuangYong 1.0 XXXX
 * 
 *        版权: 版权所有(C)2011 公司: 拓维信息系统股份有限公司
 */
public class RuleTree {
	private String id;
	private String pid;
	private String val;
	private String chk;// 复选框是否选中或半选中状态true 为选中，false 未选中，part
						// 为部分选中,树控件默认为"false"
	private String asyn;// Boolean型，节点是否异步

	private Map map;//其它
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val;
	}

	public String getChk() {
		return chk;
	}

	public void setChk(String chk) {
		this.chk = chk;
	}

	public String getAsyn() {
		return asyn;
	}

	public void setAsyn(String asyn) {
		this.asyn = asyn;
	}
}
