package com.example.SecondJWT.Service;

import com.example.SecondJWT.Entity.CustomerEntity;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    public List<CustomerEntity> showAll();

    public CustomerEntity addCustomer(CustomerEntity entity);

    public Optional<CustomerEntity> searchCustomer(String username);
}
