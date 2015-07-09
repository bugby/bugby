package org.bugby;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;

import org.bugby.matcher.javac.ParsedSource;
import org.bugby.matcher.javac.SourceParser;

import com.google.common.base.Defaults;

public class SourceFromReflection {
	public SourceFromReflection() {
		// TODO Auto-generated constructor stub
	}

	protected void printModifiers(int flags, PrintWriter print) {
		if (Modifier.isAbstract(flags)) {
			print.print("abstract ");
		}
		if (Modifier.isPublic(flags)) {
			print.print("public ");
		}
		if (Modifier.isProtected(flags)) {
			print.print("protected ");
		}
		if (Modifier.isPrivate(flags)) {
			print.print("private ");
		}
		if (Modifier.isStatic(flags)) {
			print.print("static ");
		}
		if (Modifier.isFinal(flags)) {
			print.print("final ");
		}
	}

	protected void printType(Type genericType, PrintWriter print) {
		if (genericType instanceof GenericArrayType) {
			printType(((GenericArrayType) genericType).getGenericComponentType(), print);
			print.print("[]");
		} else if (genericType instanceof Class<?>) {
			Class<?> cls = (Class<?>) genericType;
			if (cls.isArray()) {
				print.print(cls.getComponentType().getName() + "[]");
			} else {
				print.print(cls.getName());
			}
		} else {
			print.print(genericType);
		}
	}

	protected void printField(Field field, PrintWriter print) {
		printModifiers(field.getModifiers(), print);
		printType(field.getGenericType(), print);
		print.print(" ");
		print.print(field.getName());
		if (Modifier.isFinal(field.getModifiers())) {
			print.print("=" + Defaults.defaultValue(field.getType()));
		}
		print.println(";");
	}

	protected void printMethod(Method method, PrintWriter print) {
		printModifiers(method.getModifiers(), print);
		printTypeParameters(method.getTypeParameters(), print);
		printType(method.getGenericReturnType(), print);
		print.print(" ");
		print.print(method.getName());
		print.print("(");
		for (int i = 0; i < method.getGenericParameterTypes().length; ++i) {
			printParameter(i, method.getGenericParameterTypes()[i], print);
		}
		print.print(")");
		if (method.getGenericExceptionTypes().length > 0) {
			print.print("throws ");
			for (int i = 0; i < method.getGenericExceptionTypes().length; ++i) {
				if (i > 0) {
					print.append(", ");
				}
				printType(method.getGenericExceptionTypes()[i], print);
			}
		}

		print.println("{");
		if (!method.getReturnType().equals(void.class)) {
			print.println("return " + Defaults.defaultValue(method.getReturnType()) + ";");
		}
		print.println("}");
	}

	protected void printParameter(int i, Type type, PrintWriter print) {
		//TODO add annotations
		if (i > 0) {
			print.print(", ");
		}
		printType(type, print);
		print.print(" ");
		print.print("p" + i);
	}

	protected void printConstructor(Constructor<?> constructor, PrintWriter print) {
		printModifiers(constructor.getModifiers(), print);
		printTypeParameters(constructor.getTypeParameters(), print);
		print.print(constructor.getDeclaringClass().getSimpleName());
		print.print("(");
		for (int i = 0; i < constructor.getGenericParameterTypes().length; ++i) {
			printParameter(i, constructor.getGenericParameterTypes()[i], print);
		}
		print.print(")");
		//add throws
		print.println("{");
		print.println("}");
	}

	protected void printTypeParameters(TypeVariable<?>[] typeParams, PrintWriter print) {
		if (typeParams.length > 0) {
			print.append("<");
			for (int i = 0; i < typeParams.length; ++i) {
				if (i > 0) {
					print.append(", ");
				}
				printType(typeParams[i], print);
			}
			print.append(">");
		}
	}

	public void generateSource(Class<?> clz, Writer output) {
		PrintWriter print = new PrintWriter(new BufferedWriter(output));

		print.println("package " + clz.getPackage().getName() + ";");
		print.println();

		printModifiers(clz.getModifiers(), print);
		print.print("class " + clz.getSimpleName());
		printTypeParameters(clz.getTypeParameters(), print);

		if (clz.getGenericSuperclass() != null) {
			print.append(" extends ");
			printType(clz.getGenericSuperclass(), print);
		}
		if (clz.getGenericInterfaces().length > 0) {
			print.append(" implements ");
			for (int i = 0; i < clz.getGenericInterfaces().length; ++i) {
				if (i > 0) {
					print.append(", ");
				}
				printType(clz.getGenericInterfaces()[i], print);
			}

		}

		print.println("{");

		for (Field field : clz.getDeclaredFields()) {
			printField(field, print);
		}

		for (Constructor<?> constructor : clz.getDeclaredConstructors()) {
			printConstructor(constructor, print);
		}

		for (Method method : clz.getDeclaredMethods()) {
			printMethod(method, print);
		}
		print.println("}");
		print.flush();
	}

	public static void main(String[] args) throws IOException {
		SourceFromReflection s = new SourceFromReflection();
		//s.generateSource(IOException.class, new OutputStreamWriter(System.out));
		Class<?> clz = ArrayList.class;
		FileWriter f = new FileWriter("target/" + clz.getSimpleName() + ".java");
		s.generateSource(clz, f);
		f.close();

		ParsedSource src =
				SourceParser.parse(new File("target/" + clz.getSimpleName() + ".java"), Thread.currentThread().getContextClassLoader(), "UTF-8");
		System.out.println(src.getCompilationUnitTree());
	}
}
