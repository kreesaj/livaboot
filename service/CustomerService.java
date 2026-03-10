package com.accounting.service;

import com.accounting.model.Customer;
import com.accounting.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerService {
    
    private final CustomerRepository customerRepository;
    
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
    
    public List<Customer> getActiveCustomers() {
        return customerRepository.findByIsActiveTrue();
    }
    
    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));
    }
    
    public Customer getCustomerByCode(String code) {
        return customerRepository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("Customer not found with code: " + code));
    }
    
    public List<Customer> searchCustomers(String name) {
        return customerRepository.findByNameContainingIgnoreCase(name);
    }
    
    public Customer createCustomer(Customer customer) {
        if (customerRepository.findByCode(customer.getCode()).isPresent()) {
            throw new RuntimeException("Customer with code " + customer.getCode() + " already exists");
        }
        if (customer.getEmail() != null && customerRepository.findByEmail(customer.getEmail()).isPresent()) {
            throw new RuntimeException("Customer with email " + customer.getEmail() + " already exists");
        }
        return customerRepository.save(customer);
    }
    
    public Customer updateCustomer(Long id, Customer customer) {
        Customer existing = getCustomerById(id);
        existing.setName(customer.getName());
        existing.setEmail(customer.getEmail());
        existing.setPhone(customer.getPhone());
        existing.setAddress(customer.getAddress());
        existing.setCity(customer.getCity());
        existing.setState(customer.getState());
        existing.setZipCode(customer.getZipCode());
        existing.setCountry(customer.getCountry());
        existing.setCreditLimit(customer.getCreditLimit());
        existing.setTaxId(customer.getTaxId());
        existing.setIsActive(customer.getIsActive());
        return customerRepository.save(existing);
    }
    
    public Customer updateCustomerBalance(Long id, BigDecimal amount) {
        Customer customer = getCustomerById(id);
        customer.setBalance(customer.getBalance().add(amount));
        return customerRepository.save(customer);
    }
    
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
}
