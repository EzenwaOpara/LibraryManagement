package com.benjie.librarymanagement.entity;

/*
 * Created by OPARA BENJAMIN
 * On 12/13/2020 - 9:42 PM
 */

import javax.persistence.Entity;

@Entity
public class LibraryAdmin extends LibraryMember {

    private Boolean isAdmin;

    public Boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }
}
