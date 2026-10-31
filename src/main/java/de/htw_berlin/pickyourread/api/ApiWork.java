package de.htw_berlin.pickyourread.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import de.htw_berlin.pickyourread.entities.ApiBook;
import tools.jackson.databind.ObjectMapper;

public class ApiWork {

    public ApiWork(String apiKey, String title, Map<String, String> author, String coverEditionKey,
            int releaseYear, Map<String,Map<String,String>> series) {
                this.apiKey = apiKey;
                this.title = title;
                this.author = author;
                if(coverEditionKey == null) {
                    this.cover = null;
                } else setCover(coverEditionKey);
                this.releaseYear = releaseYear;
                this.series = series;
            }

    //======================
    //      Attributes
    //======================

    private String apiKey;
    private String title;
    private Map<String,String> author;
    private Map<String,String> cover;
    private int releaseYear;

    /**This Map should be filled With the seriesKey as Key and a map with 'seriesName' and 'seriesPos'
     */
    private Map<String,Map<String,String>> series;
    
    private List<ApiBook> editions;

    //======================
    //      Getters
    //======================

    public String getTitle() {
        return title;
    }
    public Map<String,String> getAuthor() {
        return author;
    }
    public String getApiKey() {
        return apiKey;
    }
    public Map<String,String> getCover() {
        return cover;
    }
    public int getReleaseYear() {
        return releaseYear;
    }
    public List<ApiBook> getEditions() {
        return editions;
    }
    public Map<String,Map<String,String>> getSeries() {
        return series;
    }
    //======================
    //      Setters
    //======================

    private void setCover(String cover) {
        Map<String,String> coverSizes = Map.of(
            "s","https://covers.openlibrary.org/b/id/"+cover+"-S.jpg",
            "m","https://covers.openlibrary.org/b/id/"+cover+"-M.jpg",
            "l","https://covers.openlibrary.org/b/id/"+cover+"-L.jpg"
        );

        this.cover = coverSizes;
    }

    public List<ApiBook> fetchEditions(ApiHandler api, ResponseHandler response) {

        ResponseEntity<Object> e = api.callEditionsByWork(this.apiKey);

        List<ApiBook> re = response.toEditionList(response.responseToMap(e));

        editions = re;

        return editions;
    }

    @Override
    public boolean equals(Object obj) {

        // checking if the two objects
        // pointing to same object
        if (this == obj)
            return true;

        // checking for two condition:
        // 1) object is pointing to null
        // 2) if the objects belong to
        // same class or not
        if (obj == null
            || this.getClass() != obj.getClass())
            return false;

        ApiWork w1 = (ApiWork)obj; // type casting object to the
                           // intended class type

        boolean coverSame;
        if (cover == null || w1.cover == null) {
            coverSame = this.getCover() == w1.getCover(); 
        } else {
            coverSame = this.getCover().equals(w1.getCover());
        }

        boolean seriesSame;
        if (series == null || w1.series == null) {
            seriesSame = this.getSeries() == w1.getSeries(); 
        } else {
            seriesSame = this.getSeries().equals(w1.getSeries());
        }
        
        return this.getApiKey().equals(w1.getApiKey())
            && this.getAuthor().equals(w1.getAuthor())
            && this.getReleaseYear() == w1.getReleaseYear()
            && this.getTitle().equals(w1.getTitle())
            && coverSame
            && seriesSame;
    }

}
