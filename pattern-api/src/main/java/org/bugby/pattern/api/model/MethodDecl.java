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

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor public final class MethodDecl extends BodyDecl {
private final 	List<TypeParam> TypeParameters;
	private final TypeMirror ReturnType;
	private final String Name;
private final 	List<MethodParam> Parameters;
private final 	List<TypeMirror> Throws;
	private final Block Body;
	/**
	 * Only for annotation types
	 */
	private final Expr DefaultValue;
	
	private final MethodKind Kind;
	
	enum MethodKind {
		Constructor,
		Initializer,
		Method,
		AnnotationMethod
	}
}
