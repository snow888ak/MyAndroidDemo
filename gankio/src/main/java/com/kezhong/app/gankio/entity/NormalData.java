package com.kezhong.app.gankio.entity;

/**
 * Created by kezhong.
 * QQ:396926020@qq.com
 * on 2017/8/6
 */

public class NormalData {

	/**
	 * _id : 598029be421aa90c9203d448
	 * createdAt : 2017-08-01T15:11:58.619Z
	 * desc : Storybook 3.2 引入 Vue.js 支持
	 * publishedAt : 2017-08-03T12:08:07.258Z
	 * source : chrome
	 * type : Android
	 * url : https://zhuanlan.zhihu.com/p/28239408
	 * used : true
	 * who : 王下邀月熊
	 */

	private String _id;
	private String createdAt;
	private String desc;
	private String publishedAt;
	private String source;
	private String type;
	private String url;
	private boolean used;
	private String who;

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getPublishedAt() {
		return publishedAt;
	}

	public void setPublishedAt(String publishedAt) {
		this.publishedAt = publishedAt;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean isUsed() {
		return used;
	}

	public void setUsed(boolean used) {
		this.used = used;
	}

	public String getWho() {
		return who;
	}

	public void setWho(String who) {
		this.who = who;
	}
}
