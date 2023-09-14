package com.example.SecondJWT.Service;

import com.example.SecondJWT.Entity.CustomerEntity;
import com.example.SecondJWT.Repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService{
    @Autowired
    private CustomerRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public List<CustomerEntity> showAll() {
        return repository.findAll();
    }

    @Override
    public CustomerEntity addCustomer(CustomerEntity entity) {
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        return repository.save(entity);
    }

    @Override
    public Optional<CustomerEntity> searchCustomer(String username) {
        Optional<CustomerEntity> customerEntity = repository.findByUsername(username);
        return customerEntity;
    }
}
