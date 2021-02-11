package de.vrtlr.api.entities;

public class Link {

	private static final long serialVersionUID = -706243242873257798L;
	private String domain;
	private String url;
	private String title;
	private String desc;
	private String image;
	private String imageAlt;

	public Link(String domain, String url, String title, String desc, String image, String imageAlt) {
		this.domain = domain;
		this.url = url;
		this.title = title;
		this.desc = desc;
		this.image = image;
		this.imageAlt = imageAlt;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
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

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getImageAlt() {
		return imageAlt;
	}

	public void setImageAlt(String imageAlt) {
		this.imageAlt = imageAlt;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}