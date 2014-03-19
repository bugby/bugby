package org.bugby.engine.model;

import japa.parser.ast.Node;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

public class TreeNodeDescriber {
	public List<Method> describeNodeClass(Class<? extends Node> nodeClass) {
		Method[] methods = nodeClass.getMethods();
		List<Method> treeMethods = new ArrayList<Method>();
		for (Method method : methods) {
			if (method.getName().startsWith("get") && method.getParameterTypes().length == 0) {
				// this is a getter
				Type returnType = method.getGenericReturnType();
				if (isNodeOrCollectionOfNode(returnType)) {
					treeMethods.add(method);
				}
			}
		}
		return treeMethods;
	}

	private boolean isNodeOrCollectionOfNode(Type returnType) {
		if (returnType instanceof ParameterizedType) {
			ParameterizedType ptype = (ParameterizedType) returnType;
			if (ptype.getActualTypeArguments().length != 1) {
				return false;
			}
			return List.class.isAssignableFrom((Class) ptype.getRawType())
					&& Node.class.isAssignableFrom((Class) ptype.getActualTypeArguments()[0]);
		}
		if (returnType instanceof Class) {
			return Node.class.isAssignableFrom((Class) returnType);
		}
		return false;
	}

	public ListMultimap<String, Node> describeNode(Node node) {
		List<Method> treeMethods = describeNodeClass(node.getClass());
		ListMultimap<String, Node> children = ArrayListMultimap.create();
		for (Method method : treeMethods) {
			try {
				Object ch = method.invoke(node);
				if (ch != null) {
					children.putAll(getPropertyName(method.getName()), ch instanceof List ? (List) ch : Collections.singletonList(ch));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return children;
	}

	private String getPropertyName(String methodName) {
		return methodName.substring(3, 4).toLowerCase() + methodName.substring(4);
	}

	public static void main(String[] args) {
		System.out.println(new TreeNodeDescriber().describeNodeClass(ClassOrInterfaceDeclaration.class));
	}
}
