package org.bugby.bugs.findbugs.badpractice;

import java.io.Serializable;

import org.bugby.wildcard.type.MissingInterface;

public class NonSerializable implements MissingInterface<Serializable> {
}