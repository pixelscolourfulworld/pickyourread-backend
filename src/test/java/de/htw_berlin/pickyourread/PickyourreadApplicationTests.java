package de.htw_berlin.pickyourread;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

@SpringBootTest
class PickyourreadApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void testApiHandlerUserAgent() {
		ApiHandler ah = new ApiHandler();

		String expected;

		try {
            FileReader fr = new FileReader("src/main/resources/static/useragent.txt");
            expected = fr.readAllAsString();
            fr.close();
            
        } catch (Exception e) {
			expected = "Error 404: File not Found";
            e.printStackTrace();
        }

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
