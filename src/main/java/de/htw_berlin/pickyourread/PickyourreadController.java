package de.htw_berlin.pickyourread;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
public class PickyourreadController {

  @GetMapping("/")
  public List<Book> index() {
    return List.of(
      new Book("The Tainted Cup", "Bennet, Robert Jackson"), 
      new Book("Throne of Glass", "Maas, Sarah J."), 
      new Book("Scythe", "Shusterman, Neil"));
  }

  @GetMapping(value = "/testquery")
  public ResponseEntity testQuery() {

    String url = "https://openlibrary.org/search.json?q=the+lord+of+the+rings";
    
    RestTemplate restTemplate = new RestTemplate();
    
    ResponseEntity<Map> result = restTemplate.getForEntity(url, Map.class);
    return result;
  }
  
}