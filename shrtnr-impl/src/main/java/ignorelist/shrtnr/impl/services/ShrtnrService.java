package ignorelist.shrtnr.impl.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.disk0.dbutil.api.exceptions.SqlException;
import ignorelist.shrtnr.api.entities.IdGeneratorFastShort;
import ignorelist.shrtnr.api.entities.Shrtnd;
import ignorelist.shrtnr.impl.repos.ShrtnrRepo;

@Component
public class ShrtnrService {

	@Autowired
	private ShrtnrRepo r;
	
	private IdGeneratorFastShort idg = new IdGeneratorFastShort();
	
	public Shrtnd create(String url) throws SqlException {
		Shrtnd s = new Shrtnd();
		s.setUrl(url);
		return r.save(s,idg.generateId());
	}

	public Shrtnd get(String id) throws SqlException {
		return r.get(id);
	}
	
}
