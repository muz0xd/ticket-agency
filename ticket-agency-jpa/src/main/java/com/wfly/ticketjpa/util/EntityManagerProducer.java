package com.wfly.ticketjpa.util;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
//import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

public class EntityManagerProducer {
	@Produces
	@PersistenceContext(unitName = "psqlUnit")
	private EntityManager em;
}
