package ignorelist.shrtnr.impl.repos;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import de.disk0.dbutil.api.Comparator;
import de.disk0.dbutil.api.Operator;
import de.disk0.dbutil.api.Select;
import de.disk0.dbutil.api.TableReference;
import de.disk0.dbutil.api.exceptions.SqlException;
import de.disk0.dbutil.impl.AbstractGuidRepository;
import de.disk0.dbutil.impl.mysql.MysqlStatementBuilder;
import ignorelist.shrtnr.api.entities.Shrtnd;

@Component
public class ShrtnrRepo extends AbstractGuidRepository<Shrtnd> {
	
	public Select createSelect(String id, String url, int offset, int max) {
		Select s = new MysqlStatementBuilder().createSelect();
		TableReference tr = s.fromTable(Shrtnd.class);
		if(!StringUtils.isEmpty(id)) {
			s.condition(Operator.AND,tr.field("id"),Comparator.EQ,tr.value(id));
		}
		if(!StringUtils.isEmpty(url)) {
			s.condition(Operator.AND,tr.field("url"),Comparator.EQ,tr.value(url));
		}
		s.limit(offset, max);
		return s;
	}
	
	
	public List<Shrtnd> find(String id, String url, int offset, int max) throws SqlException {
		Select s = createSelect(id, url, offset, max);
		return find(s.getSql(),s.getParams());
	}

}
