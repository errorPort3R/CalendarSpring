package com.ironyard.entities;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by jeffryporter on 6/23/16.
 */
@Entity
@Table(name = "events")


public class Event
{
    @Id
    @GeneratedValue
    int id;

    @Column(nullable = false)
    String description;

    @Column(nullable = false)
    LocalDateTime time;

    @ManyToOne
    User user;

    public Event()
    {
    }

    public Event(String description, LocalDateTime time, User user)
    {
        this.description = description;
        this.time = time;
        this.user = user;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public LocalDateTime getTime()
    {
        return time;
    }

    public void setTime(LocalDateTime time)
    {
        this.time = time;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }
}
