package org.bugby.bugs.pmd;

import static org.bugby.wildcard.Wildcards.someValue;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bugby.api.annotation.IgnoreFromMatching;
import org.bugby.api.annotation.Pattern;

@Pattern
public class CheckResultSetPrev {
	// TODO i should tell to ignore the throws
	public void someCode() throws SQLException {
		@IgnoreFromMatching
		ResultSet set = someValue();

		// TODO i should make sure this does not match if (set.next()) or while(set.next())
		set.previous();
	}
}
