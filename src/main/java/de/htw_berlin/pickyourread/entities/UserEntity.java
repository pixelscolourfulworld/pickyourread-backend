package de.htw_berlin.pickyourread.entities;

import java.util.Date;

public interface UserEntity {

    Date getDateInit();
    Date getDateMod();
    User getUser();

    void updateDateMod();
    void setUser();

}
