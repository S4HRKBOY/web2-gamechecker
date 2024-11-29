package de.fhdo.eborrow.controller.wrapper;

public class Query {

	private String query;

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	@Override
	public String toString() {
		return "Query{" + "query='" + query + '\'' + '}';
	}
}
