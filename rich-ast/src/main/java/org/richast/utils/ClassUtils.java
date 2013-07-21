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
package org.richast.utils;

import japa.parser.ast.type.PrimitiveType;
import japa.parser.ast.type.Type;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import org.richast.type.ClassWrapper;
import org.richast.type.GenericArrayTypeImpl;
import org.richast.type.GenericArrayTypeWrapper;
import org.richast.type.PrimitiveTypes;
import org.richast.type.TypeWrapper;
import org.richast.type.TypeWrappers;

import com.google.common.primitives.Primitives;

public final class ClassUtils {
	/**
	 * these are packages that don't have the annotation but are considered as bridges
	 */
	private static final Pattern IMPLICIT_BRIDGE = Pattern.compile("java\\.lang.*|org\\.junit.*");

	private static final Set<String> BASIC_TYPE_NAMES = new HashSet<String>();

	private static final Set<String> INTEGER_TYPE_NAMES = new HashSet<String>(Arrays.asList("int", "long", "short",
			"byte"));

	static {
		for (Class<?> clazz : Primitives.allWrapperTypes()) {
			BASIC_TYPE_NAMES.add(clazz.getName());
		}
		BASIC_TYPE_NAMES.add(String.class.getName());
	}

	// private static Map<Class<?>, String> primitiveArrayId;

	private ClassUtils() {
		//
	}

	public static boolean isBasicType(Type type) {
		if (type instanceof PrimitiveType) {
			return true;
		}
		String typeName = type.toString();
		if (!typeName.contains(".")) {
			typeName = "java.lang." + typeName;
		}
		return BASIC_TYPE_NAMES.contains(typeName);
	}

	public static boolean isBasicType(TypeWrapper type) {
		if (type == null) {
			return true;
		}
		if (type instanceof ClassWrapper && ((ClassWrapper) type).getClazz().isPrimitive()) {
			return true;
		}
		String typeName = type.getName();

		return BASIC_TYPE_NAMES.contains(typeName);
	}

	public static boolean isIntegerType(TypeWrapper type) {
		String typeName = type.toString();
		return INTEGER_TYPE_NAMES.contains(typeName);
	}

	public static boolean isIntegerType(Type type) {
		String typeName = type.toString();
		return INTEGER_TYPE_NAMES.contains(typeName);
	}

	// public static boolean isBridge(Class<?> clazz) {
	// boolean ok = hasAnnotation(clazz, STJSBridge.class);
	// if (ok) {
	// return ok;
	// }
	// if (IMPLICIT_BRIDGE.matcher(clazz.getName()).matches()) {
	// return true;
	// }
	// return false;
	// }

	/**
	 * @return true if the type does not have a super class (or that the super class is java.lang.Object). It returns
	 *         true also for null types
	 */
	public static boolean isRootType(TypeWrapper type) {
		if (type == null) {
			return true;
		}
		TypeWrapper superClass = type.getSuperClass();

		return superClass == null || superClass.equals(TypeWrappers.wrap(Object.class));
	}

	/**
	 * @param type
	 * @return true if the given type is defined within another type
	 */
	public static boolean isInnerType(TypeWrapper type) {
		if (!(type instanceof ClassWrapper)) {
			return false;
		}
		ClassWrapper classWrapper = (ClassWrapper) type;
		return classWrapper.getDeclaringClass().isDefined();
	}

	// public static boolean isSyntheticType(TypeWrapper clazz) {
	// if (!(clazz instanceof ClassWrapper)) {
	// return false;
	// }
	// return isSyntheticType(((ClassWrapper) clazz).getClazz());
	// }

	// @SuppressWarnings("deprecation")
	// public static boolean isSyntheticType(Class<?> clazz) {
	// return hasAnnotation(clazz, org.stjs.javascript.annotation.DataType.class)
	// || hasAnnotation(clazz, SyntheticType.class);
	// }

	public static boolean hasAnnotation(ClassWrapper clazz, Class<? extends Annotation> annotation) {
		if (clazz == null) {
			return false;
		}
		return hasAnnotation(clazz.getClazz(), annotation);
	}

	public static boolean hasAnnotation(Class<?> clazz, Class<? extends Annotation> annotationClass) {
		return getAnnotation(clazz, annotationClass) != null;
	}

	/**
	 * the namespace is taken from the outermost declaring class
	 * 
	 * @param type
	 * @return
	 */
	// public static String getNamespace(TypeWrapper type) {
	// if (!(type instanceof ClassWrapper)) {
	// return null;
	// }
	// ClassWrapper outermost = (ClassWrapper) type;
	// while (outermost.getDeclaringClass().isDefined()) {
	// outermost = outermost.getDeclaringClass().getOrNull();
	// }
	// Namespace n = getAnnotation(outermost, Namespace.class);
	// if (n != null && !Strings.isNullOrEmpty(n.value())) {
	// return n.value();
	// }
	// return null;
	// }

	public static <T extends Annotation> T getAnnotation(TypeWrapper clazz, Class<T> annotationClass) {
		if (!(clazz instanceof ClassWrapper)) {
			return null;
		}
		return getAnnotation(((ClassWrapper) clazz).getClazz(), annotationClass);
	}

	public static <T extends Annotation> T getAnnotation(Class<?> clazz, Class<T> annotationClass) {
		if (clazz == null) {
			return null;
		}
		// TODO : cache?
		T ann = clazz.getAnnotation(annotationClass);
		if (ann != null) {
			return ann;
		}

		if (clazz.getPackage() == null) {
			return null;
		}

		return clazz.getPackage().getAnnotation(annotationClass);
	}

	// public static boolean isAdapter(TypeWrapper clazz) {
	// if (clazz == null) {
	// return false;
	// }
	// return clazz.hasAnnotation(Adapter.class);
	// }
	//
	// public static boolean isAdapter(Class<?> clazz) {
	// if (clazz == null) {
	// return false;
	// }
	// return hasAnnotation(clazz, Adapter.class);
	// }
	//
	// public static boolean isJavascriptFunction(TypeWrapper clazz) {
	// if (clazz == null) {
	// return false;
	// }
	// return clazz.hasAnnotation(JavascriptFunction.class);
	// }

	/**
	 * @param resolvedType
	 * @param arrayCount
	 * @return the ClassWrapper representing an array of the given type with the given number of dimensions
	 */
	public static TypeWrapper arrayOf(TypeWrapper resolvedType, int arrayCount) {
		if (arrayCount == 0) {
			return resolvedType;
		}
		if (resolvedType.getClass() == ClassWrapper.class) {
			return new ClassWrapper(Array.newInstance((Class<?>) resolvedType.getType(), new int[arrayCount])
					.getClass());
		}

		TypeWrapper returnType = resolvedType;
		for (int i = 0; i < arrayCount; ++i) {
			returnType = new GenericArrayTypeWrapper(new GenericArrayTypeImpl(returnType.getType()));
		}
		return returnType;
	}

	public static Method findDeclaredMethod(Class<?> clazz, String name) {
		for (Method m : clazz.getDeclaredMethods()) {
			if (m.getName().equals(name)) {
				return m;
			}
		}
		return null;
	}

	public static Constructor<?> findConstructor(Class<?> clazz) {
		if (clazz.getDeclaredConstructors().length != 0) {
			return clazz.getDeclaredConstructors()[0];
		}
		return null;
	}

	public static boolean isAssignableFromType(final Class<?> cls, final java.lang.reflect.Type type) {
		if (type instanceof Class<?>) {
			return isAssignableFromClass(cls, (Class<?>) type);
		}
		if (type instanceof GenericArrayType) {
			return isAssignableFromGenericArrayType(cls, (GenericArrayType) type);
		}
		if (type instanceof ParameterizedType) {
			return isAssignableFromParameterizedType(cls, (ParameterizedType) type);
		}
		if (type instanceof TypeVariable<?>) {
			return isAssignableFromTypeVariable(cls, (TypeVariable<?>) type);
		}
		if (type instanceof WildcardType) {
			return isAssignableFromWildcardType(cls, (WildcardType) type);
		}

		throw new IllegalArgumentException("Unsupported type: " + type);
	}

	private static boolean isAssignableFromClass(final Class<?> cls, final Class<?> otherClass) {
		if (cls.isAssignableFrom(otherClass)) {
			return true;
		}
		// try primitives
		if (Primitives.wrap(cls).isAssignableFrom(Primitives.wrap(otherClass))) {
			return true;
		}

		// go on with primitive rules double/float -> accept long/int -> accept byte/char (but this only if there is
		// none more specific!)
		if (PrimitiveTypes.isAssignableFrom(cls, otherClass)) {
			return true;
		}
		return false;
	}

	private static boolean isAssignableFromGenericArrayType(final Class<?> cls, final GenericArrayType genericArrayType) {
		if (!cls.isArray()) {
			return false;
		}

		final java.lang.reflect.Type componentType = genericArrayType.getGenericComponentType();
		return isAssignableFromType(cls.getComponentType(), componentType);
	}

	private static boolean isAssignableFromParameterizedType(final Class<?> cls,
			final ParameterizedType parameterizedType) {
		return isAssignableFromType(cls, parameterizedType.getRawType());
	}

	private static boolean isAssignableFromTypeVariable(final Class<?> cls, final TypeVariable<?> typeVariable) {
		return isAssignableFromUpperBounds(cls, typeVariable.getBounds());
	}

	private static boolean isAssignableFromWildcardType(final Class<?> cls, final WildcardType wildcardType) {
		return isAssignableFromUpperBounds(cls, wildcardType.getUpperBounds());
	}

	private static boolean isAssignableFromUpperBounds(final Class<?> cls, final java.lang.reflect.Type[] bounds) {
		for (java.lang.reflect.Type bound : bounds) {
			if (isAssignableFromType(cls, bound)) {
				return true;
			}
		}
		return false;
	}

	public static Class<?> getRawClazz(java.lang.reflect.Type type) {
		if (type instanceof Class<?>) {
			return (Class<?>) type;
		}
		if (type instanceof ParameterizedType) {
			return getRawClazz(((ParameterizedType) type).getRawType());
		}
		if (type instanceof GenericArrayType) {
			return Object[].class;
		}
		// TODO what to do exacly here !?
		return Object.class;
	}

	public static String getPropertiesFileName(String className) {
		return className.replace('.', '/') + ".stjs";
	}

}
