package de.htw_berlin.pickyourread;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PickyourreadController {

  @GetMapping("/")
  public List<Book> index() {
    return List.of(
      new Book("The Tainted Cup", "Bennet, Robert Jackson"), 
      new Book("Throne of Glass", "Maas, Sarah J."), 
      new Book("Scythe", "Shusterman, Neil"));
  }

}