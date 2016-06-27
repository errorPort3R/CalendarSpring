package com.ironyard.controllers;

import com.ironyard.entities.Event;
import com.ironyard.entities.User;
import com.ironyard.services.EventsRepository;
import com.ironyard.services.UserRepository;
import com.ironyard.utils.PasswordStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

/**
 * Created by jeffryporter on 6/23/16.
 */

@Controller
public class CalendarController
{
    @Autowired
    UserRepository users;

    @Autowired
    EventsRepository events;

    @PostConstruct
    public void init() throws PasswordStorage.CannotPerformOperationException
    {
        if (users.count() == 0)
        {
            User user = new User("general", PasswordStorage.createHash("user"));
            users.save(user);
        }
    }


    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String home(HttpSession session, Model model)
    {
        String username = (String) session.getAttribute("username");
        model.addAttribute("events", events.findAll());
        model.addAttribute("username", username);
        model.addAttribute("now", LocalDateTime.now());
        return "home";
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login(HttpSession session, String username, String password) throws Exception
    {
        User user = users.findByName(username);
        if (user == null)
        {
            user = new User(username, PasswordStorage.createHash(password));
        }
        else if (!PasswordStorage.verifyPassword(password, user.getPassword()))
        {
            throw new Exception("Wrong Password!");
        }
        session.setAttribute("username", username);
        return "redirect:/";
    }

    @RequestMapping(path = "/logout", method = RequestMethod.POST)
    public String logout(HttpSession session)
    {
        session.invalidate();
        return "redirect:/";

    }
    @RequestMapping(path = "/create-event", method = RequestMethod.POST)
    public String createEvent(HttpSession session, String description, String time) throws Exception
    {
        String username = (String) session.getAttribute("username");
        User user = users.findByName(username);
        Event event = new Event(description, LocalDateTime.parse(time), user);
        events.save(event);
        return "redirect:/";
    }

}
