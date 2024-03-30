package it.unimib.tin.TIN.security;

import it.unimib.tin.TIN.model.Account;
import it.unimib.tin.TIN.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class CustomUserDetailService implements UserDetailsService {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private AccountRepository arepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> a;
        if(username.contains("@")) {
            a = arepo.findByEmail(username);
        } else {
            a = arepo.findByUsername(username);
        }

        if (a.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        return new CustomUserDetails(a.get());
    }

}
