package de.htw_berlin.pickyourread;

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
    //      Getters
    //======================

    @Override
    public Date getDateInit() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getDateInit'");
    }

    @Override
    public Date getDateMod() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getDateMod'");
    }

    @Override
    public User getUser() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUser'");
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
