package com.benjie.librarymanagement.entity;

/*
 * Created by OPARA BENJAMIN
 * On 12/13/2020 - 8:17 PM
 */

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
public class LibraryUser extends LibraryMember {

    private Boolean banned;

    @PrePersist
    private void init() {
        setBanned(false);
    }

    public Boolean isBanned() {
        return banned;
    }

    public void setBanned(Boolean banned) {
        this.banned = banned;
    }
}
