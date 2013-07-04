package com.talkweb.fastui.helper.rule;

/**
 * 文件名称: RuleNavigation.java 内容摘要: 定义导航控件的属性映射规则
 * 
 * @author: zhangwen
 * @version: 1.0
 * @Date: 2012-3-6 下午03:51:57
 * 
 *        修改历史: 修改日期 修改人员 版本 修改内容 ----------------------------------------------
 *        2012-3-6 zhangwen 1.0 1.0 XXXX
 * 
 *        版权: 版权所有(C)2012 公司: 拓维信息系统股份有限公司
 */
public class RuleNavigation {
	private String id;
	private String group;
	private String text;
	private String url;
	private String target;
	private String ico;
	private String num;

   public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
	    
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getIco() {
		return ico;
	}

	public void setIco(String ico) {
		this.ico = ico;
	}

}
