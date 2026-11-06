package de.htw_berlin.pickyourread;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileReader;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.RequestEntity;

import de.htw_berlin.pickyourread.api.ApiHandler;

@SpringBootTest
class PickyourreadApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void testApiHandlerUserAgent() {
		ApiHandler ah = new ApiHandler();

		String expected = System.getenv("user_agent");

		System.out.println("expected: "+expected);
		System.out.println("acutal: "+ah.getUserAgent());

		assertEquals(expected, ah.getUserAgent());
	}

	
	@Test
	void testUserAgentHeader() {
        ApiHandler apiHandler = new ApiHandler();
		RequestEntity<Object> re = apiHandler.testCall();
		String actual = re.getHeaders().get("user-agent").get(0);

		for (String s:apiHandler.getHeaders().headerNames()){
			System.out.println(s+": "+apiHandler.getHeaders().get(s));
		}

		assertEquals(apiHandler.getUserAgent(), actual);

	}
	
	
}
