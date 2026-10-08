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
	void testISBN10valid() {
		Book testBook = new Book("Throne of Glass");
		
		String expected = "0-439-02348-3";

		testBook.setIsbn(expected);

		expected = expected.replaceAll("-", "");

		assertEquals(expected, testBook.getIsbn());
	}

	@Test
	void testISBN13valid() {
		Book testBook = new Book("Throne of Glass");
		
		String expected = "978-0-439-02348-1";

		testBook.setIsbn(expected);

		expected = expected.replaceAll("-", "");

		assertEquals(expected, testBook.getIsbn());
	}
}
