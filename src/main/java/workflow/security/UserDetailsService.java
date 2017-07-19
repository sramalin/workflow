package workflow.security;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import workflow.domain.User;
import workflow.domain.Authority;
import workflow.repository.UserRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Component("userDetailsService")
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(UserDetailsService.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String login) {

        log.debug("Authenticating {}", login);
        String lowercaseLogin = login.toLowerCase();

        List<User> userFromDatabase;
        userFromDatabase = userRepository.findByusername(lowercaseLogin);


        if (userFromDatabase == null) {
            throw new UsernameNotFoundException("User " + lowercaseLogin + " was not found in the database");
        }


            Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            for (Authority authority : userFromDatabase.get(0).getAuthorities()) {
                GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(authority.getName());
                grantedAuthorities.add(grantedAuthority);
            }

            return new org.springframework.security.core.userdetails.User(userFromDatabase.get(0).getUsername(), userFromDatabase.get(0).getPassword(), grantedAuthorities);

        }

    }

