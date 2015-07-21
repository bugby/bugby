package org.bugby.bugs.findbugs.badpractice;

import java.io.Externalizable;
import java.io.Serializable;

import org.bugby.api.annotation.Missing;
import org.bugby.api.annotation.Pattern;
import org.bugby.wildcard.type.Missing2Interfaces;

@Pattern(root = false)
public class NonSerializableAndExternalizableType implements Missing2Interfaces<Serializable, Externalizable> {
	@Missing
	void readObject() {
	}

	@Missing
	void writeObject() {
	}
}
