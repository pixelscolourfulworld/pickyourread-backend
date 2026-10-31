package de.htw_berlin.pickyourread.entities;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import de.htw_berlin.pickyourread.api.ApiWork;
import tools.jackson.databind.ObjectMapper;

public class ApiBook extends Book {

    public ApiBook(Map<String,Object> bookData) {
        ObjectMapper om = new ObjectMapper();

        this.setTitle(om.convertValue(bookData.get("title"),String.class));
        this.setCover(om.convertValue(bookData.get("cover_i"),String.class));
        this.apiKey = om.convertValue(bookData.get("key"), String.class);
    }

    public ApiBook(ApiWork work, String apiKey) {
        this.apiKey = apiKey;
        this.apiWorkID = work.getApiKey();
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
        List<String> coverSizes = List.of(
            "https://covers.openlibrary.org/b/id/"+cover+"-S.jpg",
            "https://covers.openlibrary.org/b/id/"+cover+"-M.jpg",
            "https://covers.openlibrary.org/b/id/"+cover+"-L.jpg"
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
