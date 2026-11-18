package de.htw_berlin.pickyourread.entities;

import java.util.List;
import java.util.Map;

import de.htw_berlin.pickyourread.enums.Format;
import de.htw_berlin.pickyourread.exceptions.InvalidISBNException;

import java.time.LocalDate;

public abstract class Book {

    //======================
    //      Constructors
    //======================
    
    public Book(String title, Map<String, String> author, String isbn10, String isbn13, String desc, String publisher,
            Map<String, Map<String, String>> series, String cover, List<String> genre, List<String> tags,
            LocalDate releaseDate, Format format, int length) {
        this.title = title;
        this.author = author;
        this.setIsbn(isbn10, isbn13);
        this.desc = desc;
        this.publisher = publisher;
        this.series = series;
        this.setCover(cover);
        this.genre = genre;
        this.tags = tags;
        this.releaseDate = releaseDate;
        this.format = format;
        this.length = length;
    }

    public Book(String title) {
        this.title = title;
    }

    public Book(String title, String author) {
        this.title = title;
        this.author = Map.of("N/A", author);
    }
    
    public Book() {
        
    }


    protected Book(Book b) {
        this.title = b.title;
        this.author = b.author;
        this.setIsbn(b.isbn10, b.isbn13);
        this.desc = b.desc;
        this.publisher = b.publisher;
        this.series = b.series;
        this.cover = b.cover;
        this.genre = b.genre;
        this.tags = b.tags;
        this.releaseDate = b.releaseDate;
        this.format = b.format;
        this.length = b.length;
    }

    //======================
    //      Attributes
    //======================

    protected String title;
    protected Map<String,String> author;
    
    protected String isbn10;
    protected String isbn13;
    protected String desc;
    protected String publisher;
    protected Map<String,Map<String,String>> series;
    protected Map<String,String> cover;
    
    protected List<String> genre;
    protected List<String> tags;
    protected LocalDate releaseDate;
    protected Format format;
    protected int length;
    
    //======================
    //      Getters
    //======================
    
    
    public String getTitle() {
        return title;
    }
    public Map<String,String> getAuthor() {
        return author;
    }
    public String getDesc() {
        return desc;
    }
    public String getPublisher() {
        return publisher;
    }
    public Map<String,Map<String,String>> getSeries() {
        return series;
    }
    public Map<String,String> getCovers() {
        return cover;
    }
    public String getCoverS() {
        if (cover == null) {
            return null;
        } else if (cover.containsKey("s")) {
            return cover.get("s");
        } else return cover.get(cover.keySet().iterator().next());
    } 
    public String getCoverM() {
        if (cover == null) {
            return null;
        } else if (cover.containsKey("s")) {
            return cover.get("m");
        } else return cover.get(cover.keySet().iterator().next());
    }
    public String getCoverL() {
        if (cover == null) {
            return null;
        } else if (cover.containsKey("s")) {
            return cover.get("l");
        } else return cover.get(cover.keySet().iterator().next());
    }
    public List<String> getGenre() {
        return genre;
    }
    public List<String> getTags() {
        return tags;
    }
    public LocalDate getReleaseDate() {
        return releaseDate;
    }
    public Format getFormat() {
        return format;
    }
    public String getIsbn10() {
        return isbn10;
    }
    public String getIsbn13() {
        return isbn13;
    }
    public int getLength() {
        return length;
    }
    public String getLengthString(){
        if (format != null) {
            return length+" "+format.getQuantifier();
        } else return length+" pages";
    }
    
    //======================
    //      Setters
    //======================
    
    public void setIsbn(String isbn10, String isbn13) {
        try {
            checkISBN(isbn10);
            this.isbn10 = new String(isbn10).replaceAll("-", "");
        } catch (InvalidISBNException e) {
            System.out.println(e.getMessage()+" ISBN-attribute remains unchanged.");
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            checkISBN(isbn13);
            this.isbn13 = new String(isbn13).replaceAll("-", "");
        } catch (InvalidISBNException e) {
            System.out.println(e.getMessage()+" ISBN-attribute remains unchanged.");
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    protected void setSeries(Map<String, Map<String, String>> series) {
        this.series = series;
    }

    public abstract void setCover(String cover);

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

        Book b1 = (Book)obj; // type casting object to the
                           // intended class type

        boolean coverSame;
        if (cover == null || b1.cover == null) {
            coverSame = this.getCovers() == b1.getCovers(); 
        } else {
            coverSame = this.getCovers().equals(b1.getCovers());
        }

        boolean seriesSame;
        if (series == null || b1.series == null) {
            seriesSame = this.getSeries() == b1.getSeries(); 
        } else {
            seriesSame = this.getSeries().equals(b1.getSeries());
        }

        boolean rdSame = this.getReleaseDate() == null ? b1.getReleaseDate() == null : this.getReleaseDate().equals(b1.getReleaseDate());
        boolean isbn10Same = this.getIsbn10() == null ? b1.getIsbn10() == null : this.getIsbn10().equals(b1.getIsbn10());
        boolean isbn13Same = this.getIsbn13() == null ? b1.getIsbn13() == null : this.getIsbn13().equals(b1.getIsbn13());
        boolean descSame = this.getDesc() == null ? b1.getDesc() == null : this.getDesc().equals(b1.getDesc());
        boolean pubSame = this.getPublisher() == null ? b1.getPublisher() == null : this.getPublisher().equals(b1.getPublisher());

        return this.getAuthor().equals(b1.getAuthor())
            && this.getTitle().equals(b1.getTitle())
            && rdSame
            && isbn10Same
            && isbn13Same
            && descSame
            && pubSame
            && coverSame
            && seriesSame;
    }

    public void print() {
        System.out.println("title: "+title);
        System.out.println("author: "+author);    
        System.out.println("isbn10: "+isbn10);
        System.out.println("isbn13: "+isbn13);
        System.out.println("desc: "+desc);
        System.out.println("publisher: "+publisher);
        System.out.println("series: "+series);
        System.out.println("cover: "+cover);
        System.out.println("genre: "+genre);
        System.out.println("tags: "+tags);
        System.out.println("releaseDate: "+releaseDate);
        System.out.println("format: "+format);
        System.out.println("length: "+length);
    }

}
