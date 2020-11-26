package ignorelist.shrtnr.api.entities;

import javax.persistence.Column;
import javax.persistence.Table;

import de.disk0.dbutil.api.IdGeneratorClass;
import de.disk0.dbutil.api.entities.BaseGuidEntity;

@Table(name="shrtnd")
@IdGeneratorClass(value = IdGeneratorFastShort.class)
public class Shrtnd extends BaseGuidEntity {

	@Column(name="url")
	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
}
