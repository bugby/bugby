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
import java.util.Set;

import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeMirror;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import org.bugby.pattern.api.matcher.content.ContentMatcher;

@RequiredArgsConstructor
@Getter
public final class VarDecl implements BodyDecl {

	private final ContentMatcher<Set<Modifier>> modifiers;
	private final ContentMatcher<List<Annotation>> annotations;

	private final TypeMirror type;
	private final String var;
	private final Expr init;
}