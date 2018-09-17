/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.springsecurity.controller;

import java.security.Principal;

import javax.servlet.http.HttpSession;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

/**
 *
 * @author henk
 */
@Controller
@SessionAttributes("currentUser")
public class HomeController {

    @GetMapping("/")
    public String root() {
        return "home";
    }
    
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }
    
    @GetMapping("/authorized_home")
    public String authorizedHome(Model model) {
    	model.addAttribute("currentUser", getCurrentUser());
        return "home";
    }

    // Login form with error
    @GetMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }
    
    @GetMapping("/403")
    public String accessDeniedError() {
    	return "/403";
    }
    
    // Voorbeeld hoe de huidig ingelogde user en rol kan worden opgevraagd
    @ModelAttribute("currentUser")
	public String getCurrentUser() {
		String currentUserName = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			String currentUsername = authentication.getName() + " " + authentication.getAuthorities();
			return currentUsername;
		}
		return currentUserName;
	}
}
