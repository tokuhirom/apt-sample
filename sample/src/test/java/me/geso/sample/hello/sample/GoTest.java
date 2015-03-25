package me.geso.sample.hello.sample;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

public class GoTest {

	@Test
	public void test() {
		assertThat(new Blah().hello(), is("hello"));
	}
}
