package de.htw_berlin.pickyourread;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PickyourreadController {

  ApiHandler apiHandler = new ApiHandler();
  List<Book> books = new ArrayList<Book>();

  @GetMapping("/")
  public List<Book> index() {
    return List.of(
        new Book("The Tainted Cup", "Bennet, Robert Jackson"),
        new Book("Throne of Glass", "Maas, Sarah J."),
        new Book("Scythe", "Shusterman, Neil"));
  }

  @GetMapping("/dev/api/booksearch/")
  public Object testQueryBooksearchFull(@RequestParam("test") String test) {

    String query = "the+lord+of+the+rings";

    switch (test) {
      case "full" : return apiHandler.searchBook(query);
      case "body" : return apiHandler.searchBookBody(query);
      case "books" : return apiHandler.searchBookBooks(query);
      case "booklist" :
        List<Book> booklistquery = apiHandler.searchBookList(query);
        for (Book b : booklistquery) books.add(b); 
        return booklistquery;      
      case "bookentry1": return apiHandler.searchEntryKey(books.get(0).getApiKey());
      default:return "Test Query Not Found";
    }
  }

  @GetMapping("/query")
  public ResponseEntity<Object> getMethodName(@RequestParam("q") String query) {
    return apiHandler.searchBook(query);
  }

}