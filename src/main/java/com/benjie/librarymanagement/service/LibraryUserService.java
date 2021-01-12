package com.benjie.librarymanagement.service;

/*
 * Created by OPARA BENJAMIN
 * On 1/4/2021 - 9:20 PM
 */

import com.benjie.librarymanagement.entity.LibraryUser;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.Map;

@Stateless
public class LibraryUserService {

    @Inject
    private UserQueryService userQueryService;
    @Inject
    private EntityManager entityManager;
    @Inject
    private SecurityUtil securityUtil;


    //return 1 if successful, 0 if user with email already exists and -1 for unknown error
    public int saveUser(LibraryUser libraryUser) {
        Long count = (Long) userQueryService.countUsersByEmail(libraryUser.getEmail()).get(0);

        LibraryUser user = userQueryService.findMemberByEmail(libraryUser.getEmail());

        if (libraryUser.getId() == null && count == 0) {
            Map<String, String> credMad = securityUtil.hashPassword(libraryUser.getPassword());

            libraryUser.setPassword(credMad.get(SecurityUtil.HASH_PASSWORD_KEY));
            libraryUser.setPasswordSalt(credMad.get(SecurityUtil.SALT_KEY));

            entityManager.persist(libraryUser);
            credMad.clear();
            return 1;
        } else if (libraryUser.getEmail().equalsIgnoreCase(user.getEmail()))
            return 0;
        return -1;
    }

    public String restrictUser(String email) {

        return null;
    }
}
