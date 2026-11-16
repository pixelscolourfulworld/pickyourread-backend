package de.htw_berlin.pickyourread.api;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import de.htw_berlin.pickyourread.entities.ApiBook;

public class ApiWork {

    public ApiWork(String apiKey, String title, String description, List<String> authorKey, List<String> authorName, String coverEditionKey,
            int releaseYear, List<String> seriesKey, List<String> seriesName, List<String> seriesPos) {
                this.apiKey = apiKey;
                this.title = title;
                this.description = description;
                if(coverEditionKey == null) {
                    this.cover = null;
                } else setCover(coverEditionKey);
                this.releaseYear = releaseYear;

                Map<String,String> author = zipToMap(authorKey, authorName);

                this.author = author;

                Map<String,Map<String,String>> series;
                
                if(seriesKey == null || seriesName == null || seriesPos == null) {
                    series = null;
                } else {
                    series = zipToPropMap(seriesKey, "seriesName", seriesName,"seriesPos", seriesPos);
                }

                this.series = series;
            }

    public ApiWork(String apiKey, String title, String description, Map<String, String> author, String coverEditionKey,
            int releaseYear, Map<String,Map<String,String>> series) {
                this.apiKey = apiKey;
                this.title = title;
                this.description = description;
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
    private String description;
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
    public String getDesc() {
        return description;
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

    public ApiBookResponse fetchEditions(ApiHandler api) {

        ApiBookResponse response = api.callEditionsByWork(this);

        return response;
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

    private static <K, V> Map<K, V> zipToMap(List<K> keys, List<V> values) {
        try {
            return IntStream.range(0, keys.size()).boxed().collect(Collectors.toMap(keys::get, values::get));
        } catch (NullPointerException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static <K, V> Map<K, Map<String, V>> zipToPropMap(List<K> keys, String prop1, List<V> values1, String prop2, List<V> values2) {
        List<Map<String, V>> props = new ArrayList<Map<String, V>>(0);
        
        Iterator<V> it1 = values1.iterator();
        Iterator<V> it2 = values2.iterator();

        while( it1.hasNext() && it2.hasNext()) {
            props.add(Map.of(prop1, it1.next(), prop2, it2.next()));
        }
        

        return zipToMap(keys, props);
    }
    
}
