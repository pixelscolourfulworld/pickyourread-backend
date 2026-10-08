package de.htw_berlin.pickyourread;

import java.util.List;

import de.htw_berlin.pickyourread.exceptions.InvalidISBNException;

import java.util.Date;

public class Book {

    //======================
    //      Constructors
    //======================
    
    public Book(String title) {
        this.title = title;
    }
    
    public Book(String title, String author) {
        this.title = title;
        this.author = author;
    }

    //======================
    //      Attributes
    //======================

    String title;
    String author;
    String isbn;
    String desc;
    String publisher;
    String apiKey;
    String series;
    List<String> genre;
    List<String> tags;
    Date releaseDate;
    Date lastAPIRefresh;
    Format format;
    
    int length;
    boolean custEntry;
    boolean read;
    boolean isSeries;
    float seriesSort;
    
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
    public String getPublisher() {
        return publisher;
    }
    public String getApiKey() {
        return apiKey;
    }
    public String getSeries() {
        return series;
    }
    public List<String> getGenre() {
        return genre;
    }
    public List<String> getTags() {
        return tags;
    }
    public Date getReleaseDate() {
        return releaseDate;
    }
    public Date getLastAPIRefresh() {
        return lastAPIRefresh;
    }
    public Format getFormat() {
        return format;
    }
    public String getIsbn() {
        return isbn;
    }
    public int getLength() {
        return length;
    }
    public String getLengthString(){
        if (format != null) {
            return length+" "+format.getQuantifier();
        } else return length+" pages";
    }
    public boolean isCustEntry() {
        return custEntry;
    }
    public boolean isRead() {
        return read;
    }
    public boolean isSeries() {
        return isSeries;
    }
    public float getSeriesSort() {
        return seriesSort;
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
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
    public void setSeries(String series) {
        this.series = series;
    }
    public void setGenre(List<String> genre) {
        this.genre = genre;
    }
    public void setTags(List<String> tags) {
        this.tags = tags;
    }
    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }
    public void setLastAPIRefresh(Date lastAPIRefresh) {
        this.lastAPIRefresh = lastAPIRefresh;
    }
    public void setFormat(Format format) {
        this.format = format;
    }
    public void setIsbn(String isbn) {
        try {
            checkISBN(isbn);
            this.isbn = new String(isbn).replaceAll("-", "");
        } catch (InvalidISBNException e) {
            System.out.println(e.getMessage()+" ISBN-attribute remains unchanged.");
            return;
        }
    }
    public void setLength(int length) {
        this.length = length;
    }
    public void setCustEntry(boolean custEntry) {
        this.custEntry = custEntry;
    }
    public void setRead(boolean read) {
        this.read = read;
    }
    public void setSeries(boolean isSeries) {
        this.isSeries = isSeries;
    }
    public void setSeriesSort(float seriesSort) {
        this.seriesSort = seriesSort;
    }
    
    //======================
    //      Checks
    //======================

    private void checkISBN(String isbn) throws InvalidISBNException {

        String checkString = new String(isbn).replaceAll("-", "");
        
        InvalidISBNException invalid = new InvalidISBNException(checkString+" is not a valid ISBN.");

        int len = checkString.length();

        if(len != 13 && len != 10) throw invalid;

        if (len == 13) {

            int checkSum = 0;
            
            for (int i = 1; i < 14 ;i++) {
                if (i % 2 == 0) checkSum += 3*checkString.charAt(i-1);
                else checkSum += checkString.charAt(i-1);
            }

            if (checkSum % 10 != 0) throw invalid;

        } else {
            int checkSum = 0;
            int j = 0;
            for (int i = 10; i > 1 ;i--) {
                checkSum += i*checkString.charAt(j);
                j++;
            }

            checkSum += checkString.charAt(9);

            if (checkSum % 11 != 0) throw invalid;
        }

    }
}
