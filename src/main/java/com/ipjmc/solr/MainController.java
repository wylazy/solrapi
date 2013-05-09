package com.ipjmc.solr;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.RequestContext;

import com.ipjmc.solr.dao.WebPageDao;
import com.ipjmc.solr.data.SearchResult;
import com.ipjmc.solr.data.WebDoc;
import com.ipjmc.solr.data.WebPage;
import com.ipjmc.solr.http.SolrApi;
import com.ipjmc.solr.utils.AppUtils;
import com.ipjmc.solr.utils.TextHelper;

@Controller
public class MainController {

	private static final Logger logger = Logger.getLogger(MainController.class);
	
	private SolrApi solrApi;
	private WebPageDao webPageDao;
	
	@RequestMapping(value = "/")
	public String execute(ModelMap mm) {
		mm.addAttribute("request", AppUtils.getRequest());
		return "index";
	}
	
	/**
	 * 将web页面存入MySQL
	 * @param url web页面的url
	 * @param title web页面标题
	 * @param content 页面内容
	 * @return
	 * @throws SolrServerException
	 * @throws IOException
	 */
	@RequestMapping(value = "/addWebPage")
	public @ResponseBody String addIndex(@RequestParam(value="url") String url,
			@RequestParam(value="title") String title,
			@RequestParam(value="content") String content) throws SolrServerException, IOException {
		
		WebPage webpage = webPageDao.findUnique("url", url);
		if (webpage == null) {
			webpage = new WebPage(null, url, title, content);
		} else {
			webpage.setTitle(title);
			webpage.setContent(content);
		}
		
		webPageDao.saveOrUpdate(webpage);
		
		return "{success:true}";
	}

	/**
	 * 调用solr搜索接口
	 * @param word 搜索关键词
	 * @param page 分页号
	 * @param mm
	 * @return
	 * @throws SolrServerException
	 * @throws IOException
	 */
	@RequestMapping(value = "/search")
	public String search(@RequestParam(value="q", required=false) String word, @RequestParam(value="page", required=false, defaultValue="1") Integer page, ModelMap mm) throws SolrServerException, IOException {
		
		mm.addAttribute("request", AppUtils.getRequest());
		
		if (TextHelper.isBlank(word)) {
			return "index";
		}
		
		SearchResult result = solrApi.search(word, (page-1)*10);
		mm.addAttribute("totalNum", result.getNumFound());
		mm.addAttribute("elapsedTime", result.getElapsedTime());
		mm.addAttribute("docs", result.getDocs());
		
		return "search";
	}
	
	public SolrApi getSolrApi() {
		return solrApi;
	}

	public void setSolrApi(SolrApi solrApi) {
		this.solrApi = solrApi;
	}

	public WebPageDao getWebPageDao() {
		return webPageDao;
	}

	public void setWebPageDao(WebPageDao webPageDao) {
		this.webPageDao = webPageDao;
	}

}
