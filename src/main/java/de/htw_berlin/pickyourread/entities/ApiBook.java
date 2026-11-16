package de.htw_berlin.pickyourread.entities;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import de.htw_berlin.pickyourread.api.ApiWork;
import de.htw_berlin.pickyourread.enums.Format;
import tools.jackson.databind.ObjectMapper;

public class ApiBook extends Book {

    public ApiBook(Map<String,Object> bookData) {
        ObjectMapper om = new ObjectMapper();

        this.title = om.convertValue(bookData.get("title"),String.class);
        this.setCover(om.convertValue(bookData.get("cover_i"),String.class));
        this.apiKey = om.convertValue(bookData.get("key"), String.class);
    }

    public ApiBook(ApiWork work, String apiKey, String title, String isbn10, String isbn13,
            String publisher, String cover, List<String> genre, List<String> tags,
            LocalDate releaseDate, Format format, int length, String desc) {
        super(title, work.getAuthor(), isbn10, isbn13, work.getDesc(), publisher, work.getSeries(), cover, genre, tags, releaseDate, format, length);
        this.apiKey = apiKey;
        this.apiWorkID = work.getApiKey();
        if (cover == null) this.cover = work.getCover();
        if (desc != null) this.desc = desc;
    }

    public ApiBook(String apiKey, String apiWorkID, String title, Map<String, String> author, String isbn10, String isbn13, String desc,
            String publisher,
            Map<String, Map<String, String>> series, String cover, List<String> genre, List<String> tags,
            LocalDate releaseDate, Format format, int length) {
        super(title, author, isbn10, isbn13, desc, publisher, series, cover, genre, tags, releaseDate, format, length);
        this.apiKey = apiKey;
        this.apiWorkID = apiWorkID;
    }
   
    //======================
    //      Attributes
    //======================

    private String apiKey;
    private String apiWorkID;
    private LocalDate lastAPIRefresh;
    
    //======================
    //      Getters
    //======================

    public String getApiKey() {
        return apiKey;
    }
    
    public String getApiWorkID() {
        return apiWorkID;
    }

    public LocalDate getLastAPIRefresh() {
        return lastAPIRefresh;
    }
    
    //======================
    //      Setters
    //======================
    
    public void setLastAPIRefresh(LocalDate lastAPIRefresh) {
        this.lastAPIRefresh = lastAPIRefresh;
    }

    @Override 
    public void setCover(String cover) {
        Map<String, String> coverSizes = Map.of(
            "s","https://covers.openlibrary.org/b/id/"+cover+"-S.jpg",
            "m","https://covers.openlibrary.org/b/id/"+cover+"-M.jpg",
            "l","https://covers.openlibrary.org/b/id/"+cover+"-L.jpg"
        );

        this.cover = coverSizes;
    }

    //======================
    //      Methods
    //======================

    public void update(Map<String,Object> bookData) { // TODO: update Bookdata from bookData-Map
        //ObjectMapper om = new ObjectMapper();
    }
}
