package at.kaindorf.springintro.web;

import at.kaindorf.springintro.mockdatabase.CustomerMockDatabase;
import at.kaindorf.springintro.pojos.Customer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Comparator;
import java.util.List;
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
  public ResponseEntity<Iterable<Customer>> getAllCustomers(
      @RequestParam(name = "pageNo", required = false, defaultValue = "0") Integer pageNo
  ) {
    int pageSize = 5;
    int startIndex = pageNo * pageSize;
    int endIndex = startIndex + pageSize;
    List<Customer> customerList = customerMockDatabase.getCustomers();

    customerList.sort(Comparator.comparing(Customer::getLastName)
        .thenComparing(Customer::getFirstName)
        .reversed());

    if (endIndex >= customerList.size()) {
      return ResponseEntity.badRequest().build();
    }
    return ResponseEntity.ok(customerList.subList(startIndex,endIndex));
  }

  @GetMapping("/{id}")
  public ResponseEntity<Customer> getCustomer(@PathVariable("id") Long customerId) {
    Optional<Customer> customerOptional = customerMockDatabase.getCustomerById(customerId);
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
      URI location = ServletUriComponentsBuilder
          .fromCurrentRequest()
          .path("/{id}")
          .buildAndExpand(customerOptional.get().getId())
          .toUri();
      return ResponseEntity.created(location).body(customerOptional.get());
//      return ResponseEntity.status(HttpStatus.CREATED).body(customerOptional.get());
    }
    return ResponseEntity.status((HttpStatus.CONFLICT)).build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<Customer> createOrSetCustomer(@PathVariable Long id, @RequestBody Customer customer) {
    Optional<Customer> customerOptional = customerMockDatabase.createOrSetCustomer(id, customer);
    return ResponseEntity.status(HttpStatus.ACCEPTED).body(customerOptional.get());
  }

  @PatchMapping("/{id}")
  public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestBody Customer customer) {
    Optional<Customer> customerOptional = customerMockDatabase.updateCustomer(id, customer);
    if (customerOptional.isPresent()) {
      return ResponseEntity.status(HttpStatus.ACCEPTED).body(customerOptional.get());
    } else {
      return ResponseEntity.notFound().build();
    }
  }

}
