package client.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    private final SpringRestClient client = SpringRestClient.getInstance();

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

        return client.getUserByLogin(login);
    }
}
