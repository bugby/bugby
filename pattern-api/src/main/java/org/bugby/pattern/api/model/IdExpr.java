/**
 * Copyright (C) Artem Melentyev
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.bugby.pattern.api.model;

import java.util.List;

import javax.lang.model.type.TypeMirror;

import lombok.Getter;

import org.bugby.pattern.api.matcher.content.ContentMatcher;

@Getter
public final class IdExpr extends Expr implements Annotable {

	private final ContentMatcher<List<Annotation>> annotations;
	private final ContentMatcher<String> id;

	public IdExpr(ContentMatcher<TypeMirror> resolvedType, ContentMatcher<List<Annotation>> annotations, ContentMatcher<String> id) {
		super(resolvedType);
		this.annotations = annotations;
		this.id = id;
	}

}
