package org.bugby.matcher.javac.source;

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
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

import org.bugby.api.BugbyException;

import com.google.common.base.Defaults;

public class ReflectionSourceParser extends AbstractSourceParser {
	private final String sourceEncoding;
	private final ClassLoader builtProjectClassLoader;

	public ReflectionSourceParser(String sourceEncoding, ClassLoader builtProjectClassLoader) {
		this.sourceEncoding = sourceEncoding;
		this.builtProjectClassLoader = builtProjectClassLoader;
	}

	protected void printModifiers(int flags, IndentPrinter print) {
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
		if (Modifier.isVolatile(flags)) {
			print.print("volatile ");
		}
		//		if (Modifier.isTransient(flags)) {
		//			print.print("transient ");
		//		}
	}

	protected void printDefaultValue(Class<?> type, IndentPrinter print) {
		if (type.equals(Character.class) || type.equals(char.class)) {
			print.print("' '");
			return;
		}
		print.print("" + Defaults.defaultValue(type));
		if (type.equals(Float.class) || type.equals(float.class)) {
			print.print("f");
		}
	}

	protected void printType(Type genericType, IndentPrinter print) {
		printType(genericType, print, false);
	}

	protected void printType(Type genericType, IndentPrinter print, boolean withBounds) {
		if (genericType instanceof GenericArrayType) {
			printType(((GenericArrayType) genericType).getGenericComponentType(), print);
			print.print("[]");
		} else if (genericType instanceof Class<?>) {
			Class<?> cls = (Class<?>) genericType;
			if (cls.isArray()) {
				print.print(cls.getComponentType().getName() + "[]");
			} else {
				//XXX may not work with classes having $ in their name
				print.print(cls.getName().replace('$', '.'));
			}
		} else if (genericType instanceof ParameterizedType) {
			ParameterizedType pt = (ParameterizedType) genericType;
			printType(pt.getRawType(), print);
			print.print("<");
			boolean first = true;
			for (Type arg : pt.getActualTypeArguments()) {
				if (!first) {
					print.print(", ");
				}
				printType(arg, print, false);
				first = false;
			}
			print.print(">");
		} else if (genericType instanceof TypeVariable<?>) {
			TypeVariable<?> tv = (TypeVariable<?>) genericType;
			print.print(tv.getName());

			if (withBounds && hasBounds(tv)) {
				print.print(" extends ");
				boolean first = true;
				for (Type arg : tv.getBounds()) {
					if (!first) {
						print.print("&");
					}
					printType(arg, print);
					first = false;
				}
			}

		} else {
			print.print(genericType.toString());
		}
	}

	private boolean hasBounds(TypeVariable<?> tv) {
		return tv.getBounds().length > 0;
	}

	protected void printField(Field field, IndentPrinter print) {
		printModifiers(field.getModifiers(), print);
		printType(field.getGenericType(), print);
		print.print(" ");
		print.print(field.getName());
		if (Modifier.isFinal(field.getModifiers())) {
			print.print("=");
			printDefaultValue(field.getType(), print);
		}
		print.println(";");
	}

	protected void printMethod(Method method, IndentPrinter print) {
		if (method.isBridge() || method.isSynthetic()) {
			return;
		}
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
					print.print(", ");
				}
				printType(method.getGenericExceptionTypes()[i], print);
			}
		}

		print.println("{");
		print.incrementIndent();
		if (!method.getReturnType().equals(void.class)) {
			print.print("return ");
			printDefaultValue(method.getReturnType(), print);
			print.println(";");
		}
		print.decrementIndent();
		print.println("}");
		print.println();
	}

	protected void printParameter(int i, Type type, IndentPrinter print) {
		//TODO add annotations
		if (i > 0) {
			print.print(", ");
		}
		printType(type, print);
		print.print(" ");
		print.print("p" + i);
	}

	protected void printConstructor(Constructor<?> constructor, IndentPrinter print) {
		if (constructor.isSynthetic()) {
			return;
		}
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
		print.println();
	}

	protected void printTypeParameters(TypeVariable<?>[] typeParams, IndentPrinter print) {
		if (typeParams.length > 0) {
			print.print("<");
			for (int i = 0; i < typeParams.length; ++i) {
				if (i > 0) {
					print.print(", ");
				}
				printType(typeParams[i], print, true);
			}
			print.print(">");
		}
	}

	private void printClassDef(Class<?> clz, IndentPrinter print) {
		printModifiers(clz.getModifiers(), print);
		print.print("class " + clz.getSimpleName());
		printTypeParameters(clz.getTypeParameters(), print);

		if (clz.getGenericSuperclass() != null) {
			print.print(" extends ");
			printType(clz.getGenericSuperclass(), print);
		}
		if (clz.getGenericInterfaces().length > 0) {
			print.print(" implements ");
			for (int i = 0; i < clz.getGenericInterfaces().length; ++i) {
				if (i > 0) {
					print.print(", ");
				}
				printType(clz.getGenericInterfaces()[i], print);
			}

		}

		print.println("{");
		print.incrementIndent();
		for (Class<?> subtype : clz.getDeclaredClasses()) {
			printClassDef(subtype, print);
		}

		for (Field field : clz.getDeclaredFields()) {
			printField(field, print);
		}
		print.println();

		for (Constructor<?> constructor : clz.getDeclaredConstructors()) {
			printConstructor(constructor, print);
		}

		for (Method method : clz.getDeclaredMethods()) {
			printMethod(method, print);
		}
		print.decrementIndent();
		print.println("}");
		print.println();
	}

	public void generateSource(Class<?> clz, Writer output) {
		PrintWriter rawPrint = new PrintWriter(new BufferedWriter(output));
		IndentPrinter print = new IndentPrinter(rawPrint, "\t");

		print.println("package " + clz.getPackage().getName() + ";");
		print.println();

		print.println("@SuppressWarnings(\"restriction\")");
		printClassDef(clz, print);
		print.flush();
	}

	@Override
	public ParsedSource parseSource(String typeName) throws BugbyException {
		try {
			Class<?> cls = builtProjectClassLoader.loadClass(typeName);
			FileWriter f = new FileWriter("target/" + cls.getSimpleName() + ".java");
			generateSource(cls, f);
			f.flush();
			f.close();
			return super.parse(null, new File("target/" + cls.getSimpleName() + ".java"), typeName, builtProjectClassLoader, sourceEncoding);
		}
		catch (ClassNotFoundException | IOException e) {
			throw new BugbyException("Cannot parse:" + typeName, e);
		}
	}

	public static void main(String[] args) throws IOException {
		ReflectionSourceParser s = new ReflectionSourceParser("UTF-8", Thread.currentThread().getContextClassLoader());
		//s.generateSource(IOException.class, new OutputStreamWriter(System.out));
		Class<?> clz = Class.class;
		FileWriter f = new FileWriter("target/" + clz.getSimpleName() + ".java");
		s.generateSource(clz, f);
		f.close();

		ParsedSource src = s.parseSource(clz.getName());
		System.out.println(src.getCompilationUnitTree());
	}

	@Override
	public String toString() {
		return getClass().getSimpleName();
	}

}
