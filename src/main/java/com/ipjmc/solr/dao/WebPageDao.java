package com.ipjmc.solr.dao;

import com.ipjmc.solr.data.WebPage;

public class WebPageDao extends AbstractDAO<WebPage> {

	@Override
	public Class<WebPage> getEntityClass() {
		return WebPage.class;
	}

}
