package at.kaindorf.springintro.web;

import at.kaindorf.springintro.mockdatabase.CustomerMockDatabase;
import at.kaindorf.springintro.pojos.Customer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Project: Exa_01_SpringIntro_4CHIF
 * Created by: SF
 * Date: 15.09.2023
 * Time: 09:30
 */
@RestController
@RequestMapping("/customers")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Slf4j
public class CustomerController {
  private final CustomerMockDatabase customerMockDatabase;

  /**
   * GET request to all customers
   *
   * @return HTTP-Response with all customers in response body and HTTP-status 200
   */
  @GetMapping("/all")
  public ResponseEntity<Iterable<Customer>> getAllCustomers() {
    return ResponseEntity.ok(customerMockDatabase.getCustomers());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Customer> getCustomer(@PathVariable("id") Long customerId) {
    Optional<Customer> customerOptional = customerMockDatabase.getCustumerById(customerId);
    return ResponseEntity.of(customerOptional);
    //    if (customerOptional.isPresent()) {
//      return ResponseEntity.ok(customerOptional.get());
//    }
//    return ResponseEntity.notFound().build();

  }

  @PostMapping
  public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer) {
    Optional<Customer> customerOptional = customerMockDatabase.addCustomer(customer);
    if (customerOptional.isPresent()) {
      return ResponseEntity.status(HttpStatus.CREATED).body(customerOptional.get());
    }
    return ResponseEntity.status((HttpStatus.CONFLICT)).build();
  }

}
