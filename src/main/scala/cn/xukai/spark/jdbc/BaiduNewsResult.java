package cn.xukai.spark.jdbc;


/**
 *  Created by root on 2017/8/10.
 */
public class BaiduNewsResult extends IdEntity {

	private String title;
	private String url;
	private String source;
	private String time;
	private String intro;
	private String label; 	
    private String content;
	private String website;
	
	
	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public String getSource() {
		return source;
	}


	public void setSource(String source) {
		this.source = source;
	}


	public String getTime() {
		return time;
	}


	public void setTime(String time) {
		this.time = time;
	}


	public String getIntro() {
		return intro;
	}


	public void setIntro(String intro) {
		this.intro = intro;
	}


	public String getLabel() {
		return label;
	}


	public void setLabel(String label) {
		this.label = label;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public String getWebsite() {
		return website;
	}


	public void setWebsite(String website) {
		this.website = website;
	}


	@Override
	public String toString() {
		return "BaiduNewsResult [title=" + title + ", url=" + url + ", source=" + source + ", time=" + time + ", intro="
				+ intro + ", label=" + label + ", content=" + content + ", website=" + website + "]";
	}

	
	
}
