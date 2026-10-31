package de.htw_berlin.pickyourread.entities;

import java.util.AbstractList;
import java.util.Date;

public class Booklist extends AbstractList<BooklistEntry> implements UserEntity{

    private Date dateCreated;
    private Date dateModified;
    private String title;

    @Override
    public BooklistEntry get(int index) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int size() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public BooklistEntry set(int arg0, BooklistEntry arg1) {
        // TODO Auto-generated method stub
        return super.set(arg0, arg1);
    }

    @Override
    public boolean add(BooklistEntry arg0) {
        // TODO Auto-generated method stub
        return super.add(arg0);
    }
    
    @Override
    public BooklistEntry remove(int index) {
        // TODO Auto-generated method stub
        return super.remove(index);
    }

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


}
