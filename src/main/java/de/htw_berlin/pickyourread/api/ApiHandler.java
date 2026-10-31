package de.htw_berlin.pickyourread.api;

import java.io.FileReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import de.htw_berlin.pickyourread.entities.ApiBook;
import de.htw_berlin.pickyourread.exceptions.CustomEntryException;
import tools.jackson.databind.ObjectMapper;


public class ApiHandler {

    /**
     * Creates an Instance of {@code ApiHandler} and reads 
     * the contents of {@code src/main/resources/static/useragent.txt}
     */
    public ApiHandler() {
        try (FileReader fr = new FileReader("src/main/resources/static/useragent.txt")) {
            userAgent.add(fr.readAllAsString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    List<String> userAgent = new ArrayList<String>();
    ObjectMapper om = new ObjectMapper();
    HttpHeaders headers = new HttpHeaders(MultiValueMap.fromMultiValue(Map.of("user-agent",userAgent)));
    RestTemplate restTemplate = new RestTemplate();
    ResponseEntity<Object> errorResponse = new ResponseEntity<>(HttpStatusCode.valueOf(400));
    
    public String getUserAgent() {
        return userAgent.get(0);
    }
    public HttpHeaders getHeaders() {
        return headers;
    }

    /**
     * Method to ensure Request is always created equal
     * @param uri
     * @return {@code RequestEntity<Object>}
     */
    private RequestEntity<Object> getRequest(URI uri) {
        return new RequestEntity<>(headers, HttpMethod.GET, uri);
    }

    /**
     * Test method to see if RequestEntity gets built correctly
     * @return {@code RequestEntity<Object>} with headers, GET-Method and URI null
     */
    public RequestEntity<Object> testCall() {
        URI testUri = null;
        return getRequest(testUri);
    }

    /**
     * Sends a GET-Request to the openlibrary.org/search.json API 
     * @param query
     * @return {@code ResponseEntity<Object>} returned from the API
     */
    @Cacheable("searchAPI")
    public ResponseEntity<Object> callSearch(String query) {
        URI uri;
        try {
            uri = new URI("https://openlibrary.org/search.json?q=" + query.replaceAll(" ","+")); // TODO: Encode Query to always be a legal URI
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return errorResponse;
        }
        return restTemplate.exchange(getRequest(uri), Object.class);
    }

    /**
     * sends GET-Request to the .json version of the openlibrary.org-site of the Work defined by _key_
     * @param key An API-Key for a WORK
     * @return {@code ResponseEntity<Object>} of the work
     */
    @Cacheable("worksAPI")
    public ResponseEntity<Object> callWorkByKey(String key) {
        URI uri;
        try {
            uri = new URI("https://openlibrary.org" + key + ".json");
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return errorResponse;
        }
        return restTemplate.exchange(getRequest(uri), Object.class);
    }

    /**
     * sends GET-Request to the editions.json API of the openlibrary.org-site of the Work defined by _apiKey_
     * @param apiKey An API-Key for a WORK
     * @return {@code ResponseEntity<Object>} of the editions
     */
    public ResponseEntity<Object> callEditionsByWork(String apiKey) {
        URI uri;
        try {
            uri = new URI("https://openlibrary.org"+apiKey+"/editions.json");
        } catch (Exception e) {
            e.printStackTrace();
            return errorResponse;
        }

        return restTemplate.exchange(getRequest(uri), Object.class);
    }

    /**
     * Calls the {@code callSearch()} function with query and returns the result in a {@code Map<String, Object>}
     * @param query
     * @return {@code Map<String, Object>}
     */
    public Map<String, Object> searchBookBody(String query) {
        return responseToMap(callSearch(query));
    }

    /**
     * Calls the {@code searchBookBody()} function with query and returns the found works in a {@code Map<String, Object>}
     * @param query
     * @return {@code Map<String, Object>}
     */
    @SuppressWarnings("unchecked") // TODO: Check Conversion
    public Map<String, Object>[] searchBookBooks(String query) {
        return om.convertValue(searchBookBody(query).get("docs"), HashMap[].class);
    }

    /**
     * Calls the {@code searchBookBooks()} function with query and turns the result into a {@code List<ApiBook>}
     * @param query
     * @return {@code List<ApiBook>}
     */
    public List<ApiBook> searchBookList(String query) {
        return toBookArray(searchBookBooks(query));
    }

    /**
     * Calls the {@code callWorkByKey()} function with a Key and returns the result in a {@code Map<String, Object>} using {@code responseToMap()}
     * @param key
     * @return {@code Map<String, Object>}
     */
    public Map<String, Object> searchEntryKey(String key) {
        return responseToMap(callWorkByKey(key));
    }

    /**
     * Calls the {@code callEditionsByWork()} function with a Key and returns the result in a {@code Map<String, Object>} using {@code responseToMap()}
     * @param key
     * @return {@code Map<String, Object>}
     */
    public Map<String, Object> searchEditions(String key) {
        return responseToMap(callEditionsByWork(key));
    }
    
    
    /**
     * Takes a {@code ResponseEntity} and converts it into a {@code Map<String, Object>}
     * @param re {@code ResponseEntity}
     * @return {@code Map<String, Object>}
     */
    @SuppressWarnings("unchecked") // TODO: Check Conversion
    protected Map<String, Object> responseToMap(ResponseEntity<Object> re) {
        return om.convertValue(re.getBody(), HashMap.class);
    }


    /**
     * Used to convert API Results into a list of {@code ApiBooks}
     * @param books
     * @return {@code List<ApiBooks>}
     */
    protected List<ApiBook> toBookArray(Map<String, Object>[] books) {
        List<ApiBook> result = new ArrayList<ApiBook>();

        Stream.of(books).forEach(m -> result.add(new ApiBook(m)));

        return result;
    }

    @CacheEvict("searchBook")
    public void updateBook(ApiBook b) throws CustomEntryException {
        b.update(searchBookBooks(b.getApiKey())[0]);
    }
    
}
