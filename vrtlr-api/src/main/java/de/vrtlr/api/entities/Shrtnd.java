package de.vrtlr.api.entities;

import javax.persistence.Column;
import javax.persistence.Table;

import de.disk0.dbutil.api.IdGeneratorClass;
import de.disk0.dbutil.api.entities.BaseGuidEntity;

@Table(name="shrtnd")
@IdGeneratorClass(value = IdGeneratorFastShort.class)
public class Shrtnd extends BaseGuidEntity {

	@Column(name="url")
	private String url;

	@Column(name="title")
	private String title;

	@Column(name="mnemonic")
	private String mnemonic;

	@Column(name="description")
	private String desc;

	@Column(name="image")
	private String image;
	
	@Column(name="hash")
	private String hash;
	
	
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

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getMnemonic() {
		return mnemonic;
	}

	public void setMnemonic(String mnemonic) {
		this.mnemonic = mnemonic;
	}
	
}
