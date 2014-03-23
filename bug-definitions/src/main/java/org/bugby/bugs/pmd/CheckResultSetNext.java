package org.bugby.bugs.pmd;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bugby.annotation.GoodExample;

@GoodExample
public class CheckResultSetNext {
	// TODO i should tell to ignore the throws
	public void someCode(ResultSet someVar) throws SQLException {
		// TODO i should make sure this does not match if (set.next()) or while(set.next())
		while (someVar.next()) {

		}
	}
}
