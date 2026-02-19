/*
 * Created by Osman Balci on 2025.9.22
 * Copyright © 2025 Osman Balci. All rights reserved.
 */
package edu.vt.FacadeBeans;

import edu.vt.EntityBeans.Movie;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

// @Stateless annotation implies that the conversational state with the client shall not be maintained.
@Stateless
public class MovieFacade extends AbstractFacade<Movie> {
    /*
    ---------------------------------------------------------------------------------------------
    The EntityManager is an API that enables database CRUD (Create Read Update Delete) operations
    and complex database searches. An EntityManager instance is created to manage entities
    that are defined by a persistence unit. The @PersistenceContext annotation below associates
    the entityManager instance with the persistence unitName identified below.
    ---------------------------------------------------------------------------------------------
     */
    @PersistenceContext(unitName = "Movies-TerrazasPU")
    private EntityManager entityManager;

    // Obtain the object reference of the EntityManager instance in charge of
    // managing the entities in the persistence context identified above.
    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    /*
    This constructor method invokes its parent AbstractFacade's constructor method,
    which in turn initializes its entity class type T and entityClass instance variable.
     */
    public MovieFacade() {
        super(Movie.class);
    }

}
