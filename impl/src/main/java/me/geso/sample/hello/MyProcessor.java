package me.geso.sample.hello;

import java.io.IOException;
import java.io.Writer;
import java.util.Set;

import javax.annotation.Generated;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic.Kind;
import javax.tools.JavaFileObject;

import com.google.auto.common.MoreElements;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes("me.geso.sample.hello.*")
public class MyProcessor extends AbstractProcessor {
	@Override
	public boolean process(final Set<? extends TypeElement> annotations, final RoundEnvironment roundEnv) {
		if (annotations.isEmpty()) {
			System.out.println("no annotations");
			return true;
		}
		log("HUAAAAAAAAAAAAAAAAAAAAA");

		final Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(Hello.class);
		log(elements.toString());
		for (final Element element : elements) {
			System.out.println("ELEMENT!!!");
			final PackageElement aPackage = MoreElements.getPackage(element);
			final TypeSpec blah = TypeSpec.classBuilder("Blah")
				.addAnnotation(AnnotationSpec.builder(Generated.class)
					.addMember("value", "{$S}", getClass().getCanonicalName())
					.build())
				.addModifiers(Modifier.PUBLIC)
				.addMethod(MethodSpec.methodBuilder("hello")
					.addModifiers(Modifier.PUBLIC)
					.addCode(
						CodeBlock.builder()
							.add("return \"hello\";\n")
							.build()
					)
					.returns(TypeName.get(String.class))
					.build()
				).build();
			JavaFile javaFile = JavaFile.builder(aPackage.getQualifiedName().toString(), blah)
				.build();

			try {
				JavaFileObject sourceFile = processingEnv.getFiler().createSourceFile(aPackage.toString() + ".Blah");
				Writer writer = sourceFile.openWriter();
				javaFile.writeTo(writer);
				writer.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		return true;
	}

	private void log(String msg) {
		if (processingEnv.getOptions().containsKey("debug")) {
			processingEnv.getMessager().printMessage(Kind.NOTE, msg);
		}
	}

}
