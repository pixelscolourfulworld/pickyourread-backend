package de.htw_berlin.pickyourread;

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

import de.htw_berlin.pickyourread.exceptions.CustomEntryException;
import tools.jackson.databind.ObjectMapper;


public class ApiHandler {

    public ApiHandler() {
        try {
            FileReader fr = new FileReader("src/main/resources/static/useragent.txt");

            userAgent.add(fr.readAllAsString());
            fr.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    List<String> userAgent = new ArrayList<String>();
    ObjectMapper om = new ObjectMapper();
    HttpHeaders headers = new HttpHeaders(MultiValueMap.fromMultiValue(Map.of("user-agent",userAgent)));
    RestTemplate restTemplate = new RestTemplate();
    List<String> fields = List.of(
        "author_key",
        "author_name",
        "edition_count",
        "key",
        "series_key",
        "series_name",
        "title");
    
    public String getUserAgent() {
        return userAgent.get(0);
    }
    public HttpHeaders getHeaders() {
        return headers;
    }

    public ResponseEntity<Object> testCall(URI uri) {
        RequestEntity<Object> request = new RequestEntity<Object>(headers, HttpMethod.GET,uri);
        return restTemplate.exchange(request, Object.class);
    }

    @Cacheable("searchAPI")
    protected ResponseEntity<Object> callSearch(String query) {
        URI uri;
        try {
            uri = new URI("https://openlibrary.org/search.json?q=" + query.replaceAll(" ","+"));
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return new ResponseEntity<Object>(HttpStatusCode.valueOf(400));
        }
        RequestEntity<Object> request = new RequestEntity<Object>(headers, HttpMethod.GET,uri);
        return restTemplate.exchange(request, Object.class);
    }

    // Delivers 403 response
    @Cacheable("worksAPI")
    protected ResponseEntity<Object> callWorkByKey(String key) {
        URI uri;
        try {
            uri = new URI("https://openlibrary.org" + key + ".json");
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return new ResponseEntity<Object>(HttpStatusCode.valueOf(400));
        }
        RequestEntity<Object> request = new RequestEntity<Object>(headers,
                HttpMethod.GET, uri);
        return restTemplate.exchange(request, Object.class);
    }

    public ResponseEntity<Object> searchBook(String query) {
        return callSearch(query);
    }

    public Map<String, Object> searchBookBody(String query) {
        return responseToMap(searchBook(query));
    }

    public Map<String, Object>[] searchBookBooks(String query) {
        return om.convertValue(searchBookBody(query).get("docs"), HashMap[].class);
    }

    public List<ApiBook> searchBookList(String query) {
        return toBookArray(searchBookBooks(query));
    }

    
    public Map<Object,Object> searchEntryKey(String key) {
        return om.convertValue(callWorkByKey(key), HashMap.class);
    }
    
    protected Map<String, Object> responseToMap(ResponseEntity<Object> re) {
        return om.convertValue(re.getBody(), HashMap.class);
    }

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
