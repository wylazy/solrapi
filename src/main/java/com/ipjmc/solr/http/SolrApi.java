package com.ipjmc.solr.http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

import com.ipjmc.solr.data.SearchResult;
import com.ipjmc.solr.data.WebDoc;

/**
 * 访问solr服务器的编程接口
 * @author wylazy
 *
 */
public class SolrApi {

	private static final Logger logger = Logger.getLogger(SolrApi.class);
	private HttpSolrServer solrServer;
	
	public SolrApi() {
	}
	
	/**
	 * 添加一片文档
	 * @param webDoc
	 * @return
	 * @throws SolrServerException
	 * @throws IOException
	 */
	public String addDocument(WebDoc webDoc) throws SolrServerException, IOException {
		UpdateResponse response = solrServer.addBean(webDoc);
		return response.getResponse().toString();
	}
	
	/**
	 * 提供搜索词，获取搜索结果。支持高亮显示匹配的关键词
	 * @param word
	 * @param offset
	 * @return
	 * @throws SolrServerException
	 * @throws UnsupportedEncodingException
	 */
	public SearchResult search(String word, int offset) throws SolrServerException, UnsupportedEncodingException {
		SolrQuery query = new SolrQuery();
		query.setQuery(word);
		query.setStart(offset);
		
		QueryResponse rsp = solrServer.query(query);
		SolrDocumentList docs = rsp.getResults();
		
		//logger.info("docs = " + docs);
		List<WebDoc> list = new LinkedList<WebDoc>();
		for (SolrDocument solrDoc : docs) {
			WebDoc webDoc = new WebDoc();
			webDoc.setId((String)solrDoc.getFieldValue("id"));
			webDoc.setUrl((String)solrDoc.getFieldValue("url"));
			
			//将title和content高亮处理
			Map<String, List<String>> highs = rsp.getHighlighting().get(webDoc.getId());
			if (highs != null) {
				
				List<String> highTitles = highs.get(WebDoc.TITLE);
						
				if (highTitles != null && !highTitles.isEmpty()) {
					webDoc.setTitle(highTitles.get(0));
				} else {
					webDoc.setTitle((String)solrDoc.getFieldValue(WebDoc.TITLE));
				}
				
				List<String> highContents = highs.get(WebDoc.CONTENT);
				if (highContents != null && !highContents.isEmpty()) {
					webDoc.setContent(highContents.get(0));
				} else {
					webDoc.setContent((String)solrDoc.getFieldValue(WebDoc.CONTENT));
				}

			}
			
			list.add(webDoc);
		}
		
		SearchResult result = new SearchResult();
		result.setDocs(list);
		result.setNumFound(docs.getNumFound());
		result.setElapsedTime(rsp.getElapsedTime()/1000.f);
		return result;
	}

	public HttpSolrServer getSolrServer() {
		return solrServer;
	}

	public void setSolrServer(HttpSolrServer solrServer) {
		this.solrServer = solrServer;
	}
	
}
