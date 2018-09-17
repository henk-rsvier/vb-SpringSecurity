/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.springsecurity.controller;

import nl.springsecurity.domain.Account;
import nl.springsecurity.domain.AccountType;
import nl.springsecurity.repository.AccountRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 *
 * @author henk
 */
@Controller
@RequestMapping(path = "accounts")
@SessionAttributes("currentUser")
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    HomeController homeController;
    
    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping
    public String showAllAccounts(Model model) {
        model.addAttribute("accountList", accountRepository.findAll());
        model.addAttribute("currentUser", homeController.getCurrentUser());
        return "account/accounts";
    }
    
    @GetMapping(path="/add")
    public String showaddAccountForm(Model model) {
        List<AccountType> accountTypeList = new ArrayList<>(Arrays.asList(AccountType.values()));
        model.addAttribute(new Account());        
        model.addAttribute(accountTypeList);
        return "account/addAccountForm";
    }
    
    @PostMapping(path="/add")
    public String addAccount(@Valid Account account, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            List<AccountType> accountTypeList = new ArrayList<>(Arrays.asList(AccountType.values()));
            model.addAttribute(accountTypeList);
            return "account/addAccountForm";
        }
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        try {
            accountRepository.save(account);
            return("redirect:/accounts");
        } catch(DataIntegrityViolationException e) {
            return("account/err_duplicate_account");
        }
    }
    

}
