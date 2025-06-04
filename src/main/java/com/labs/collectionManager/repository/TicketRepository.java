package com.labs.collectionManager.repository;

import com.labs.collectionManager.model.core.Ticket;
import org.springframework.data.repository.CrudRepository;


public interface TicketRepository extends CrudRepository<Ticket, String> {}
