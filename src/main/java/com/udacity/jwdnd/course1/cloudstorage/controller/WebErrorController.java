package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/error")
public class WebErrorController implements ErrorController
{
    @GetMapping()
    public String handleError(HttpServletRequest request, Model model, Authentication authentication)
    {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        model.addAttribute("status", status.toString());

        if(status.toString() == "500")
        {
            model.addAttribute("message", "Internal Server Error" );
        }
        else if (status.toString() == "404")
        {
            model.addAttribute("message", "Page not found!" );
        }

        if(authentication.getName().isEmpty())
        {
            model.addAttribute("noAuth");
        }

        return "error";
    }

    @Override
    public String getErrorPath()
    {
        return null;
    }
}
