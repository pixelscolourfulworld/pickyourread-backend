package de.htw_berlin.pickyourread;

import java.util.List;

import de.htw_berlin.pickyourread.exceptions.InvalidISBNException;

import java.util.Date;

public abstract class Book {

    //======================
    //      Constructors
    //======================
    
    public Book() {}

    public Book(String title) {
        this.setTitle(title);
    }

    public Book(String title, String author) {
        this.setTitle(title);
        this.setAuthor(author);
    }


    protected Book(Book b) {
        title = b.title;
        this.author = b.author;
        this.isbn = b.isbn;
        this.desc = b.desc;
        this.publisher = b.publisher;
        this.series = b.series;
        this.cover = b.cover;
        this.genre = b.genre;
        this.tags = b.tags;
        this.releaseDate = b.releaseDate;
        this.format = b.format;

        this.length = b.length;
        this.read = b.read;
        this.isEdition = b.isEdition;
        this.isSeries = b.isSeries;
        this.seriesSort = b.seriesSort;

    }

    //======================
    //      Attributes
    //======================

    String title;
    String author;
    String isbn;
    String desc;
    String publisher;
    String series;
    List<String> cover;
    List<String> genre;
    List<String> tags;
    Date releaseDate;
    Format format;
    
    int length;
    boolean read;
    boolean isEdition;
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
    public String getSeries() {
        return series;
    }
    public List<String> getCover() {
        return cover;
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
    public void setSeries(String series) {
        this.series = series;
    }
    abstract public void setCover(String cover);

    public void setGenre(List<String> genre) {
        this.genre = genre;
    }
    public void setTags(List<String> tags) {
        this.tags = tags;
    }
    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
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

    protected void checkISBN(String isbn) throws InvalidISBNException {

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
            int checkSum = checkString.charAt(9);
            int j = 0;
            for (int i = 10; i > 1 ;i--) {
                checkSum += i*checkString.charAt(j);
                j++;
            }
            if (checkSum % 11 != 0) throw invalid;
        }

    }
}
