package de.htw_berlin.pickyourread;

import java.util.Date;
import java.util.List;
import java.util.Map;

import tools.jackson.databind.ObjectMapper;

public class ApiBook extends Book {

    public ApiBook(Map<String,Object> bookData) {
        ObjectMapper om = new ObjectMapper();

        this.setTitle(om.convertValue(bookData.get("title"),String.class));
        this.setCover(om.convertValue(bookData.get("cover_i"),String.class));
        this.setApiKey(om.convertValue(bookData.get("key"), String.class));
    }
   
    //======================
    //      Attributes
    //======================

    String apiKey;
    Date lastAPIRefresh;
    
    //======================
    //      Getters
    //======================

    public String getApiKey() {
        return apiKey;
    }
    
    public Date getLastAPIRefresh() {
        return lastAPIRefresh;
    }
    
    //======================
    //      Setters
    //======================

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
    
    public void setLastAPIRefresh(Date lastAPIRefresh) {
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
