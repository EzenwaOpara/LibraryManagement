package com.benjie.librarymanagement.service;

/*
 * Created by OPARA BENJAMIN
 * On 12/15/2020 - 10:54 PM
 */

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class CDIProducer {

    @Produces
    @PersistenceContext
    EntityManager entityManager;
}
