package com.ipjmc.solr.data;

import org.apache.solr.client.solrj.beans.Field;

public class WebDoc {

	public static final String TITLE = "title";
	public static final String CONTENT = "content";
	
	@Field
	private String id;
	
	@Field
	private String url;
	
	@Field
	private String title;
	
	@Field
	private String content;
	
	public WebDoc() {
	}

	public WebDoc(String url, String title, String content) {
		super();
		this.id = url;
		this.url = url;
		this.title = title;
		this.content = content;
	}
	
	public WebDoc(String id, String url, String title, String content) {
		super();
		this.id = id;
		this.url = url;
		this.title = title;
		this.content = content;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "WebDoc [id=" + id + ", url=" + url + ", title=" + title
				+ ", content=" + content + "]";
	}

}
