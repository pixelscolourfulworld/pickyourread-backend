package de.htw_berlin.pickyourread.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class CustBook extends Book implements UserEntity {

    //======================
    //    Constructors
    //======================

    public CustBook() {}

    public CustBook(String title) {
        super(title);
    }

    public CustBook(String title, String author) {
        super(title, author);
    }


    //======================
    //      Attributes
    //======================

    private Date dateCreated;
    private Date lastModified;
    private User user;
    
    //======================
    //      Getters
    //======================

    @Override
    public Date getDateInit() {
        return this.dateCreated;
    }

    @Override
    public Date getDateMod() {
        return lastModified;
    }

    @Override
    public User getUser() {
        return this.user;
    }

    //======================
    //      Setters
    //======================

    @Override
    public void updateDateMod() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateDateMod'");
    }

    @Override
    public void setUser() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setUser'");
    }

    @Override
    public void setCover(String cover) {
        this.cover = Map.of("Custom",cover);
    }
    
    protected void setAuthor(String authors) {
        Iterator<String> authorIterator = List.of(authors.split(",")).iterator();
        List<String> authorList = new ArrayList<String>();
        while(authorIterator.hasNext()) {
            authorList.add("N/A");
            authorList.add(authorIterator.next()); 
        }

        this.setAuthor((String[]) authorList.toArray());
    }

    protected void setAuthor(String... authors) {
        if(authors.length % 2 != 0) throw new IllegalArgumentException();

        List<String> authorList = List.of(authors);
        
        List<Entry<String,String>> entryList = new ArrayList<Entry<String,String>>();

        Iterator<String> a = authorList.iterator();

        while(a.hasNext()) {
            entryList.add(Map.entry(a.next(),a.next()));
        }

        Map<String,String> authorMap = Map.ofEntries();

        this.author = authorMap;
    }

}
