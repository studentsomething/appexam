package no.westerdals.pgr301exam;

import java.util.Optional;

import com.codahale.metrics.MetricRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/customers")
public class CustomerController {

    private Logger logger = LoggerFactory.getLogger(CustomerController.class);
    private CustomerRepository customerRepository;

    @Autowired
    private MetricRegistry registry;

    public CustomerController(CustomerRepository customerRepository, MetricRegistry registry) {
        this.customerRepository = customerRepository;
        this.registry = registry;
    }

    /**
     * Displays the welcome page, that shows all users. Which suits perfectly to send in a mark with "Welcome" tag :)
     * @return Iterable<Customer>
     */
    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Customer> getAllCustomers(){

        logger.debug("Fetching all users");

        registry.meter("Welcome").mark();
        Iterable<Customer> customers = customerRepository.findAll();
        return customers;
    }

    /**
     * Displays one single user defined by the Id sent in
     * @return Optional<Customer>
     */
    @RequestMapping(value = "customers/{id}", method = RequestMethod.GET)
    public Optional<Customer> getCustomer(@PathVariable("id") Long id){

        logger.debug(String.format("Fetching user with userId %s", id));

        Optional<Customer> customer = customerRepository.findById(id);
        return customer;
    }

    /**
     * Deltes a given customer
     * @param id
     */
    @DeleteMapping(value = "customers/{id}")
    public void deleteCustomer(@PathVariable("id") Long id){

        logger.debug(String.format("Deleting user with userId %s", id));
        registry.counter("USERS").inc();

        // TODO: Do some metrics here
        Optional<Customer> customer = customerRepository.findById(id);

        if (customer.isPresent()) {
            customerRepository.delete(customer.get());
        }
    }

    /**
     * Creates a new customer
     * @param customer
     */
    @RequestMapping(method = RequestMethod.POST)
    public void createCustomer(@RequestBody Customer customer){

        logger.debug(String.format("Creating user with Firstname:  %s and LastName: ", customer.getFirstName(), customer.getLastName()));
        registry.counter("USERS").dec();
        customerRepository.save(customer);
    }
}
