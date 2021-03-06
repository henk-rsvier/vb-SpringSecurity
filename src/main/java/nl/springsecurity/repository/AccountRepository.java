/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.springsecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import nl.springsecurity.domain.Account;

/**
 *
 * @author henk
 */

public interface AccountRepository extends JpaRepository<Account, Long> {
    
    Account findByUsername(String username);
}
