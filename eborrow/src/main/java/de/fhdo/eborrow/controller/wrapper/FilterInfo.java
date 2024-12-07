package de.fhdo.eborrow.controller.wrapper;

public class FilterInfo {

	private String genre;
	private String developer;
	private String platform;

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getDeveloper() {
		return developer;
	}

	public void setDeveloper(String developer) {
		this.developer = developer;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	@Override
	public String toString() {
		return "FilterInfo{" + "genre='" + genre + '\'' + ", developer='" + developer + '\'' + ", platform='" + platform + '\'' + '}';
	}
}
