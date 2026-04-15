package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CustomerController {

    @Autowired
    private CustomerRepository repo;

    @GetMapping("/")
    public String listCustomers(Model model) {
        model.addAttribute("customers", repo.findAll());
        model.addAttribute("customer", new Customer());
        return "index";
    }


    @GetMapping("/search")
    public String search(@RequestParam("query") String query, Model model) {
        List<Customer> customers = repo.findByName(query);
        model.addAttribute("customers", customers);
        model.addAttribute("customer", new Customer());
        return "index";
    }
    @GetMapping("/edit/{id}")
    public String editCustomer(@PathVariable Long id, Model model) {
    Customer customer = repo.findById(id).orElse(new Customer());
    model.addAttribute("customer", customer);
    model.addAttribute("customers", repo.findAll());
    return "index";
    }
    @PostMapping("/save")
    public String saveCustomer(@ModelAttribute Customer customer) {
        System.out.println("ID coming from form: " + customer.getId());

    if (customer.getId() != null) {
        
        Customer existingCustomer = repo.findById(customer.getId()).orElse(null);

        if (existingCustomer != null) {
            existingCustomer.setName(customer.getName());
            existingCustomer.setEmail(customer.getEmail());
            existingCustomer.setPhone(customer.getPhone());

            repo.save(existingCustomer);
        }
    } else {
        
        repo.save(customer);
    }

    return "redirect:/";
    }
}