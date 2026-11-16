package de.htw_berlin.pickyourread.api;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


public class ApiHandler {

    /**
     * Creates an Instance of {@code ApiHandler} and reads 
     * the contents of {@code src/main/resources/static/useragent.txt}
     */
    public ApiHandler() {
        userAgent.add(System.getenv("user_agent"));
    }

    List<String> userAgent = new ArrayList<String>();
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
    public ApiWorkResponse callSearch(String query) {
        URI uri;
        try {
            uri = new URI("https://openlibrary.org/search.json?q=" + query.replaceAll(" ","+")); // TODO: Encode Query to always be a legal URI
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
        return new ApiWorkResponse(restTemplate.exchange(getRequest(uri), Object.class));
    }

    /**
     * sends GET-Request to the .json version of the openlibrary.org-site of the Work defined by _key_
     * @param key An API-Key for a WORK
     * @return {@code ResponseEntity<Object>} of the work
     */
    @Cacheable("worksAPI")
    public ApiWorkResponse callWorkByKey(String key) {
        URI uri;
        try {
            uri = new URI("https://openlibrary.org" + key + ".json");
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
        return new ApiWorkResponse(restTemplate.exchange(getRequest(uri), Object.class));
    }

    /**
     * sends GET-Request to the editions.json API of the openlibrary.org-site of the Work defined by _apiKey_
     * @param apiKey An API-Key for a WORK
     * @return {@code ResponseEntity<Object>} of the editions
     */
    public ApiBookResponse callEditionsByWork(ApiWork work) {
        URI uri;
        try {
            uri = new URI("https://openlibrary.org/works/"+work.getApiKey()+"/editions.json");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return new ApiBookResponse(work, restTemplate.exchange(getRequest(uri), Object.class));
    }
    
}
