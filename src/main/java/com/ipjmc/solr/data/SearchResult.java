package com.ipjmc.solr.data;

import java.util.List;

public class SearchResult {

	private List<WebDoc> docs;
	private long numFound;
	private float elapsedTime;
	
	public SearchResult() {
	}

	public SearchResult(List<WebDoc> docs, long numFound, float elapsedTime) {
		super();
		this.docs = docs;
		this.numFound = numFound;
		this.elapsedTime = elapsedTime;
	}

	public List<WebDoc> getDocs() {
		return docs;
	}

	public void setDocs(List<WebDoc> docs) {
		this.docs = docs;
	}

	public long getNumFound() {
		return numFound;
	}

	public void setNumFound(long numFound) {
		this.numFound = numFound;
	}

	public float getElapsedTime() {
		return elapsedTime;
	}

	public void setElapsedTime(float elapsedTime) {
		this.elapsedTime = elapsedTime;
	}

	@Override
	public String toString() {
		return "SearchResult [docs=" + docs + ", numFound=" + numFound
				+ ", elapsedTime=" + elapsedTime + "]";
	}
	
}
