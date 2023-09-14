package com.example.SecondJWT.Controller;

import com.example.SecondJWT.Entity.CustomerEntity;
import com.example.SecondJWT.Repository.CustomerRepository;
import com.example.SecondJWT.Service.CustomerService;
import com.example.SecondJWT.Service.JwtService;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/app")
public class ParentController {

    @Autowired
    private CustomerService service;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authManager;

//    @Autowired
//    public ParentController(CustomerService service, JwtService jwtService, AuthenticationManager authManager) {
//        this.service = service;
//        this.jwtService = jwtService;
//        this.authManager = authManager;
//    }

    @GetMapping("/home")
    @ResponseBody
    public String home(){
        return "Welcome Home";
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/all")
    @ResponseBody
    public List<CustomerEntity> allCustomers(){
        return service.showAll();
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @PostMapping("/new")
    @ResponseBody
    public String newCustomer(@RequestBody CustomerEntity entity){
        service.addCustomer(entity);
        return "Customer Added Successfully...!";
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping("/{username}")
    @ResponseBody
    public Object String (@PathVariable("username") String username){
        return service.searchCustomer(username);
    }


    @GetMapping("/token/{username}/{password}")
    @ResponseBody
    public String tokenGenerator(@PathVariable("username") String username,
                                 @PathVariable("password") String password){
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        if(authentication.isAuthenticated()){
            String tok = jwtService.generateToken(username);
            return "Generated JWT Token => " + tok;
        }
        else{
            throw new UsernameNotFoundException("User Not Found...!");
        }

    }

}
