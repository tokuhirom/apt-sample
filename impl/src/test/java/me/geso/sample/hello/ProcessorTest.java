package me.geso.sample.hello;

import static com.google.common.truth.Truth.assert_;
import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;

import org.junit.Test;

import com.google.common.io.Resources;
import com.google.testing.compile.JavaFileObjects;

public class ProcessorTest {

	@Test
	public void testProcess() throws Exception {
		assert_().about(javaSource())
			.that(JavaFileObjects.forResource(Resources.getResource("HelloWorld.java")))
			.processedWith(new MyProcessor())
			.compilesWithoutError()
			.and()
			.generatesSources(JavaFileObjects.forSourceString("foo.bar.baz.Blah", "package foo.bar.baz;\n"
					+ "\n"
					+ "import java.lang.String;\n"
					+ "import javax.annotation.Generated;\n"
					+ "\n"
					+ "@Generated({\"me.geso.sample.hello.MyProcessor\"})\n"
					+ "public class Blah {\n"
					+ "  public String hello() {\n"
					+ "    return \"hello\";\n"
					+ "  }\n"
					+ "}"));
	}
}
