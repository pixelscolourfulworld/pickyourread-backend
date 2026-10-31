package de.htw_berlin.pickyourread.api;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;

import de.htw_berlin.pickyourread.entities.ApiBook;

public class ApiWork {
    //======================
    //      Attributes
    //======================

    private String title;
    private String author;
    private String desc;
    private String series;
    private String apiKey;
    private List<String> cover;
    private List<String> genre;
    private LocalDate releaseDate;
    private boolean isSeries;
    private float seriesSort;
    
    private List<ApiBook> editions;

    //======================
    //      Getters
    //======================

    public String getTitle() {
        return title;
    }
    public String getAuthor() {
        return author;
    }
    public String getDesc() {
        return desc;
    }
    public String getSeries() {
        return series;
    }
    public String getApiKey() {
        return apiKey;
    }
    public List<String> getCover() {
        return cover;
    }
    public List<String> getGenre() {
        return genre;
    }
    public LocalDate getReleaseDate() {
        return releaseDate;
    }
    public boolean isSeries() {
        return isSeries;
    }
    public float getSeriesSort() {
        return seriesSort;
    }
    public List<ApiBook> getEditions() {
        return editions;
    }
    //======================
    //      Setters
    //======================

    public void setTitle(String title) {
        this.title = title;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    public void setSeries(String series) {
        this.series = series;
    }
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
    public void setCover(String cover) {
        List<String> coverSizes = List.of(
            "https://covers.openlibrary.org/b/id/"+cover+"-S.jpg",
            "https://covers.openlibrary.org/b/id/"+cover+"-M.jpg",
            "https://covers.openlibrary.org/b/id/"+cover+"-L.jpg"
        );

        this.cover = coverSizes;
    }
    public void setGenre(List<String> genre) {
        this.genre = genre;
    }
    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }
    public void setSeries(boolean isSeries) {
        this.isSeries = isSeries;
    }
    public void setSeriesSort(float seriesSort) {
        this.seriesSort = seriesSort;
    }

    public List<ApiBook> fetchEditions(ApiHandler api, ResponseHandler response) {

        ResponseEntity<Object> e = api.callEditionsByWork(this.apiKey);

        List<ApiBook> re = response.toEditionList(e);

        editions = re;

        return editions;
    }

}
