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


    public LibraryUser saveUser(LibraryUser libraryUser) {
        Long count = (Long) userQueryService.countUsersByEmail(libraryUser.getEmail()).get(0);

        if (libraryUser.getId() == null && count == 0) {
            Map<String, String> credMad = securityUtil.hashPassword(libraryUser.getPassword());

            libraryUser.setPassword(credMad.get(SecurityUtil.HASH_PASSWORD_KEY));
            libraryUser.setPasswordSalt(credMad.get(SecurityUtil.SALT_KEY));

            entityManager.persist(libraryUser);
            credMad.clear();
        }
        return libraryUser;
    }

    public String restrictUser(String email) {

        return null;
    }
}
