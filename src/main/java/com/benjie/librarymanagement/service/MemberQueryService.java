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

    public LibraryMember findMemberByEmail(String email) {
        List<LibraryMember> member = entityManager.createNamedQuery(LibraryMember.FIND_MEMBER_BY_EMAIL, LibraryMember.class)
                .setParameter("email", email)
                .getResultList();

        if (!member.isEmpty()) {
            return member.get(0);
        }
        return null;
    }

    public List countMembersByEmail(String email) {
        return entityManager.createNativeQuery("select count (id)  from LibraryMember where exists (select id from LibraryMember where email = ?)")
                .setParameter(1, email).getResultList();
    }
}
