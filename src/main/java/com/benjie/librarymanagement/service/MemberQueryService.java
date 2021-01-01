package com.benjie.librarymanagement.service;

/*
 * Created by OPARA BENJAMIN
 * On 12/15/2020 - 12:28 PM
 */

import com.benjie.librarymanagement.entity.LibraryMember;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

public class MemberQueryService {

    @Inject
    private EntityManager entityManager;

    public LibraryMember findMemberById(Long id) {
        return entityManager.createNamedQuery(LibraryMember.FIND_MEMBER_BY_ID, LibraryMember.class)
                .setParameter("id", id)
                .getResultList()
                .get(0);
    }

    public List<LibraryMember> findMemberByName(String firstName, String lastName) {
        return entityManager.createNamedQuery(LibraryMember.FIND_MEMBER_BY_NAME, LibraryMember.class)
                .setParameter("firstName", firstName)
                .setParameter("lastName", lastName)
                .getResultList();
    }
}
