package de.htw_berlin.pickyourread;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.htw_berlin.pickyourread.api.ApiHandler;
import de.htw_berlin.pickyourread.entities.ApiBook;
import de.htw_berlin.pickyourread.entities.Book;
import de.htw_berlin.pickyourread.entities.CustBook;

import org.springframework.web.bind.annotation.RequestMapping;


@RestController
public class PickyourreadController {

  ApiHandler apiHandler = new ApiHandler();
  List<ApiBook> books = new ArrayList<ApiBook>();

  @GetMapping("/")
  public List<Book> index() {
    return List.of(
        new CustBook("The Tainted Cup", "Bennet, Robert Jackson"),
        new CustBook("Throne of Glass", "Maas, Sarah J."),
        new CustBook("Scythe", "Shusterman, Neil"));
  }

  @GetMapping("/dev/api/openlib/booksearch/")
  public Object testQueryBooksearchFull(@RequestParam("test") String test) {

    String query = "the+lord+of+the+rings";

    switch (test) {
      case "full" : return apiHandler.callSearch(query);
      case "body" : return apiHandler.searchBookBody(query);
      case "books" : return apiHandler.searchBookBooks(query);
      case "booklist" :
        List<ApiBook> booklistquery = apiHandler.searchBookList(query);
        for (ApiBook b : booklistquery) books.add(b); 
        return booklistquery;      
      case "bookentry1": return apiHandler.searchEntryKey(books.get(0).getApiKey());
      case "editions": return apiHandler.searchEditions(books.get(0).getApiKey());
      default:return "Test Query Not Found";
    }
  }

  @CrossOrigin 
  @RequestMapping("/dev/api/pickyourread/test")
  public ResponseEntity<List<Book>> requestMethodName() {
    List<Book> books = List.of(
      new CustBook("Book 1","Author 1"),
      new CustBook("Book 2","Author 2"),
      new CustBook("Book 3","Author 3")
    );

    try {
      return ResponseEntity.created(new URI("http://localhost:8080/dev/api/pickyourread")).body(books);
    } catch (URISyntaxException e) {
      e.printStackTrace();
      return new ResponseEntity<List<Book>>(HttpStatusCode.valueOf(500));
    }
  }
  

  @GetMapping("/query")
  public List<ApiBook> getMethodName(@RequestParam("q") String query) {
    return apiHandler.searchBookList(query);
  }

}