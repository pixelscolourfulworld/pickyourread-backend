package de.htw_berlin.pickyourread;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PickyourreadApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void testISBN10validDash() {
		Book testBook = new Book("Hunger Games");
		
		String expected = "0-439-02348-3";

		testBook.setIsbn(expected);

		expected = expected.replaceAll("-", "");

		assertEquals(expected, testBook.getIsbn());
	}

	@Test
	void testISBN13validDash() {
		Book testBook = new Book("Hunger Games");
		
		String expected = "978-0-439-02348-1";

		testBook.setIsbn(expected);

		expected = expected.replaceAll("-", "");

		assertEquals(expected, testBook.getIsbn());
	}

	@Test
	void testISBN10valid() {
		Book testBook = new Book("Hunger Games");
		
		String expected = "0439023483";

		testBook.setIsbn(expected);

		expected = expected.replaceAll("-", "");

		assertEquals(expected, testBook.getIsbn());
	}

	@Test
	void testISBN13valid() {
		Book testBook = new Book("Hunger Games");
		
		String expected = "9780439023481";

		testBook.setIsbn(expected);

		expected = expected.replaceAll("-", "");

		assertEquals(expected, testBook.getIsbn());
	}

	@Test
	void testISBN10invalid() {
		Book testBook = new Book("Hunger Games");
		
		String expected = null;

		testBook.setIsbn("0439623483");

		assertEquals(expected, testBook.getIsbn());
	}

	@Test
	void testISBN13invalid() {
		Book testBook = new Book("Hunger Games");
		
		String expected = null;

		testBook.setIsbn("9780439623481");

		assertEquals(expected, testBook.getIsbn());
	}
}
