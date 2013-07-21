/**
 *  Copyright 2011 Alexandru Craciun, Eyal Kaspi
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.richast.scope;

import japa.parser.ast.expr.NameExpr;

import java.util.Set;

import org.richast.GenerationContext;
import org.richast.type.ClassLoaderWrapper;
import org.richast.type.ClassWrapper;
import org.richast.type.TypeWrappers;
import org.richast.utils.Option;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Sets;

public class CompilationUnitScope extends AbstractScope {

	private String packageName;

	private final Set<String> typeImportOnDemandSet = Sets.newHashSet();

	private final ClassLoaderWrapper classLoaderWrapper;

	public CompilationUnitScope(ClassLoaderWrapper classLoaderWrapper, GenerationContext context) {
		super(null, context);
		this.classLoaderWrapper = classLoaderWrapper;
		addType(TypeWrappers.wrap(String.class));
		addType(TypeWrappers.wrap(Integer.class));
		addType(TypeWrappers.wrap(Boolean.class));
		addType(TypeWrappers.wrap(Character.class));
		addType(TypeWrappers.wrap(Byte.class));
		addType(TypeWrappers.wrap(Short.class));
		addType(TypeWrappers.wrap(Float.class));
		addType(TypeWrappers.wrap(Double.class));
		addType(TypeWrappers.wrap(Exception.class));
		addType(TypeWrappers.wrap(RuntimeException.class));

	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	@Override
	public <T> T apply(ScopeVisitor<T> visitor) {
		return visitor.apply(this);
	}

	public void addTypeImportOnDemand(NameExpr name) {
		typeImportOnDemandSet.add(name.toString());
	}

	@VisibleForTesting
	public Set<String> getTypeImportOnDemandSet() {
		return typeImportOnDemandSet;
	}

	private TypeWithScope addTypeWithScope(String name) {
		Option<ClassWrapper> foundClass = classLoaderWrapper.loadClassOrInnerClass(name);
		if (foundClass.isEmpty()) {
			return null;
		}
		super.addType(foundClass.getOrNull());
		return new TypeWithScope(this, foundClass.getOrNull());
	}

	private TypeWithScope resolveInnerType(String name) {
		// could be A.B.C and part of the path to be imported
		int pos = name.lastIndexOf('.');
		if (pos >= 0) {
			String parent = name.substring(0, pos);
			String inner = name.substring(pos + 1, name.length());
			TypeWithScope ts = resolveType(parent);
			if (ts != null) {
				return addTypeWithScope(ts.getType().getName() + "." + inner);
			}
		}
		return null;
	}

	private TypeWithScope resolveTypeWithImports(String name) {
		TypeWithScope type = null;
		for (String pack : typeImportOnDemandSet) {
			type = addTypeWithScope(pack + "." + name);
			if (type != null) {
				return type;
			}
		}
		return type;
	}

	@Override
	public TypeWithScope resolveType(String name) {
		TypeWithScope type = super.resolveType(name);
		if (type != null) {
			return type;
		}
		// try in package
		type = addTypeWithScope(packageName + "." + name);
		if (type != null) {
			return type;
		}

		// fully qualified
		type = addTypeWithScope(name);
		if (type != null) {
			return type;
		}

		// try java.lang
		type = addTypeWithScope("java.lang." + name);
		if (type != null) {
			return type;
		}

		// try on demand
		type = resolveTypeWithImports(name);
		if (type != null) {
			return type;
		}

		type = resolveInnerType(name);

		return type;
	}

}
