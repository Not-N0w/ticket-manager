package com.labs.ticketManager.repository;

import com.labs.ticketManager.model.core.Ticket;
import org.springframework.data.repository.CrudRepository;


public interface TicketRepository extends CrudRepository<Ticket, String> {}
