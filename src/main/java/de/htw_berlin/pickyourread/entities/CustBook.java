package de.htw_berlin.pickyourread.entities;

import java.util.Date;
import java.util.List;

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
        this.cover = List.of(cover);
    }

}
