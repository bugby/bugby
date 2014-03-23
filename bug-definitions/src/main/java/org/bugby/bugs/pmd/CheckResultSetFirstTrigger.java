package org.bugby.bugs.pmd;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bugby.annotation.GoodExampleTrigger;

@GoodExampleTrigger(forExample = CheckResultSetFirst.class)
public class CheckResultSetFirstTrigger {
	// TODO i should tell to ignore the throws
	public void someCode(ResultSet someVar) throws SQLException {
		// TODO i should make sure this does not match if (set.next()) or while(set.next())
		someVar.first();
	}
}
