package org.bugby.bugs.pmd;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bugby.wildcard.Pattern;

@Pattern
public class CheckResultSetFirst {
	public void someCode(ResultSet someVar) throws SQLException {
		// TODO i should make sure this does not match if (set.next()) or while(set.next())
		if (someVar.first()) {

		}
	}
}
