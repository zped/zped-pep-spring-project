package com.example.service;

import com.example.entity.Account;
import com.example.exception.ClientErrorException;
import com.example.exception.UsernameCollisionException;
import com.example.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {

    private AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    /* Does account meet criteria for storage in db
            - Username length 0 - 255
            - Password length 4 - 255
            - Username not in db
     */
    private boolean accountCredentialsValidFormat(Account account){
        if (account.getUsername().length() > 0 &&
                account.getUsername().length() < 256 &&
                account.getPassword().length() > 3 &&
                account.getPassword().length() < 256) return true;
        return false;
    }

    // does account exist in db with that username
    private boolean accountExistsInDatabase(Account account) {
        if(accountRepository.existsByUsername(account.getUsername())) return true;
        return false;
    }

    // if no collisions and valid username and password, add account to db
    public Account registerNewAccount(Account account) throws UsernameCollisionException, ClientErrorException {
        if (accountExistsInDatabase(account)) {
            throw new UsernameCollisionException();
        }
        if (accountCredentialsValidFormat(account)) {
            account = accountRepository.save(account);
            return account;

        }
        throw new ClientErrorException();
    }

    // get account from db, if one exists matching user supplied username and password
    public Optional<Account> login(Account account) {
        return accountRepository.findByUsernameAndPassword(account.getUsername(), account.getPassword());
    }
}
