package com.benjie.librarymanagement.service;

/*
 * Created by OPARA BENJAMIN
 * On 1/4/2021 - 9:20 PM
 */

import com.benjie.librarymanagement.entity.LibraryAdmin;
import com.benjie.librarymanagement.entity.LibraryMember;
import com.benjie.librarymanagement.entity.LibraryUser;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.Map;

@Stateless
public class LibraryMemberService {

    @Inject
    private MemberQueryService memberQueryService;
    @Inject
    private EntityManager entityManager;
    @Inject
    private SecurityUtil securityUtil;


    public LibraryMember saveMember(LibraryMember libraryMember) {
        Long count = (Long) memberQueryService.countMembersByEmail(libraryMember.getEmail()).get(0);

        if (libraryMember.getId() == null && count == 0) {
            Map<String, String> credMad = securityUtil.hashPassword(libraryMember.getPassword());

            libraryMember.setPassword(credMad.get(SecurityUtil.HASH_PASSWORD_KEY));
            libraryMember.setPasswordSalt(credMad.get(SecurityUtil.SALT_KEY));

            entityManager.persist(libraryMember);
            credMad.clear();
        }
        return libraryMember;
    }

    public String restrictUser(String email) {

        return null;
    }

    public boolean authenticateUser(String email, String password) {

        return false;
    }
}
