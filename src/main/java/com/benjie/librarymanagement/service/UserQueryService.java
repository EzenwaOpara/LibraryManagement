package com.benjie.librarymanagement.service;

/*
 * Created by OPARA BENJAMIN
 * On 12/15/2020 - 12:28 PM
 */

import com.benjie.librarymanagement.entity.LibraryUser;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

public class UserQueryService {

    @Inject
    private EntityManager entityManager;

    public LibraryUser findMemberById(Long id) {
        return entityManager.createNamedQuery(LibraryUser.FIND_MEMBER_BY_ID, LibraryUser.class)
                .setParameter("id", id)
                .getResultList()
                .get(0);
    }

    public List<LibraryUser> findMemberByName(String firstName, String lastName) {
        return entityManager.createNamedQuery(LibraryUser.FIND_MEMBER_BY_NAME, LibraryUser.class)
                .setParameter("firstName", firstName)
                .setParameter("lastName", lastName)
                .getResultList();
    }

    public LibraryUser findMemberByEmail(String email) {
        List<LibraryUser> member = entityManager.createNamedQuery(LibraryUser.FIND_MEMBER_BY_EMAIL, LibraryUser.class)
                .setParameter("email", email)
                .getResultList();

        if (!member.isEmpty()) {
            return member.get(0);
        }
        return null;
    }

    public List countUsersByEmail(String email) {
        return entityManager.createNativeQuery("select count (id)  from LibraryUser where exists (select id from LibraryUser where email = ?)")
                .setParameter(1, email).getResultList();
    }
}
