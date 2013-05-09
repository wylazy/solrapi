package com.ipjmc.solr.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ipjmc.solr.dao.WebPageDao;
import com.ipjmc.solr.data.WebPage;

public class WebPageDaoTest {

	private static WebPageDao dao;
	
	@BeforeClass
	public static void setUp() {
		dao = new WebPageDao();
	}
	
	@Test
	public void testSave() throws IOException {
		
		WebPage web = new WebPage();
		web.setUrl("http://www.baidu.com");
		web.setTitle("百度一下，你记知道");
		web.setContent("Baidu...");
		dao.persist(web);
		
		assertNotNull(web.getId());
	//	dao.delete(web);
	}
}
