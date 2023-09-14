package com.example.SecondJWT.Config;

import com.example.SecondJWT.Entity.CustomerEntity;
import com.example.SecondJWT.Repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomerDetailsUDS implements UserDetailsService {

    @Autowired
    private CustomerRepository repository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<CustomerEntity> custEntity = repository.findByUsername(username);
        return custEntity.map(CustomerUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found...!" + username));
    }
}
