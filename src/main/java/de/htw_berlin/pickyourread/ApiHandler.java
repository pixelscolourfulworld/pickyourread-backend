package de.htw_berlin.pickyourread;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import tools.jackson.databind.ObjectMapper;

public class ApiHandler {
    
    String uriSearch = "https://openlibrary.org/search.json?q=";
   
    RestTemplate restTemplate = new RestTemplate();
    ObjectMapper om = new ObjectMapper();
    
    public ResponseEntity<Object> searchBook(String query){
        ResponseEntity<Object> result = restTemplate.getForEntity(uriSearch+query, Object.class);
        return result;
    }

    public Map<String,Object> searchBookBody(String query) {
        return responseToMap(searchBook(query));
    }

    @SuppressWarnings("unchecked")
    public Map<String,Object>[] searchBookBooks(String query) {
        return om.convertValue(searchBookBody(query).get("docs"), HashMap[].class);
    }

    public List<Book> searchBookList(String query) {
        return toBookArray(searchBookBooks(query));
    }

    @SuppressWarnings("unchecked")
    protected Map<String, Object> responseToMap(ResponseEntity<Object> re) {
        return om.convertValue(re.getBody(), HashMap.class);
    }

    protected List<Book> toBookArray(Map<String,Object>[] books) {
        List<Book> result = new ArrayList<Book>();

        Stream.of(books).forEach(m -> result.add(new Book(m)));
        
        return result;
    }

}
