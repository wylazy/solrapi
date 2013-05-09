package com.ipjmc.solr.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "web_page")
public class WebPage implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String url;
	private String title;
	private String content;
	
	public WebPage() {
	}

	public WebPage(Integer id, String url, String title, String content) {
		super();
		this.id = id;
		this.url = url;
		this.title = title;
		this.content = content;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "url", nullable = false)
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "title", nullable = false)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "content", nullable = false)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "WebInfo [id=" + id + ", url=" + url + ", title=" + title
				+ ", content=" + content + "]";
	}

}
