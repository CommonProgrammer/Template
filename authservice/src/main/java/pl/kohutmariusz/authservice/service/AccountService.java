package pl.kohutmariusz.authservice.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.kohutmariusz.authservice.domain.Account;
import pl.kohutmariusz.authservice.enums.Role;
import pl.kohutmariusz.authservice.repository.AccountRepository;

@Service
public class AccountService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepo;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String s) {
        Optional<Account> account = accountRepo.findByUsername( s );
        if ( account.isPresent() ) {
            return account.get();
        } else {
            throw new UsernameNotFoundException(String.format("Username[%s] not found", s));
        }
    }

    public Account findAccountByUsername(String username) {
        Optional<Account> account = accountRepo.findByUsername( username );
        if ( account.isPresent() ) {
            return account.get();
        } else {
            throw new UsernameNotFoundException(String.format("Username[%s] not found", username));
        }
    }

    public Account registerUser(Account account) {
            account.setPassword(passwordEncoder.encode(account.getPassword()));
            account.grantAuthority(Role.ROLE_USER);
            return accountRepo.save( account );
    }

    @Transactional // To successfully remove the date @Transactional annotation must be added
    public void removeAuthenticatedAccount() throws UsernameNotFoundException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Account acct = findAccountByUsername(username);
        accountRepo.deleteAccountById(acct.getId());

    }
}