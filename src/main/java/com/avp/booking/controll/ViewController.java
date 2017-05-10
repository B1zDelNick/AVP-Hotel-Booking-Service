package com.avp.booking.controll;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Slf4j
public class ViewController
{
    private static final String INDEX_VIEW = "views/index";

    @GetMapping(value = {"/home", "/login", "/register", "logout2"})
    public ModelAndView index(ModelAndView view)
    {
        view.setViewName(INDEX_VIEW);

        return view;
    }

    /*@GetMapping(value = {"/logout"})
    public ModelAndView logiut(ModelAndView view)
    {
        view.setViewName(INDEX_VIEW);

        return view;
    }*/

    @GetMapping("/")
    public String root()
    {
        return "redirect:/home";
    }
}
