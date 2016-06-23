package com.ironyard.services;

import com.ironyard.entities.Event;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by jeffryporter on 6/23/16.
 */
public interface EventsRepository extends CrudRepository<Event, Integer>
{

}
