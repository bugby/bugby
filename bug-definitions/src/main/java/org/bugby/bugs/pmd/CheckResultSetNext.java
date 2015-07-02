package org.bugby.bugs.pmd;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bugby.api.Pattern;

@Pattern
public class CheckResultSetNext {
	// TODO i should tell to ignore the throws
	public void someCode(ResultSet someVar) throws SQLException {
		// TODO i should make sure this does not match if (set.next()) or while(set.next())

		//I NEED TO CHECK while is missing
		while (someVar.next()) {

		}
	}
}
