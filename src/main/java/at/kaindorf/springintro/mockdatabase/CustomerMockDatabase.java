package at.kaindorf.springintro.mockdatabase;

import at.kaindorf.springintro.pojos.Customer;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

/**
 * Project: Exa_01_SpringIntro_4CHIF
 * Created by: SF
 * Date: 15.09.2023
 * Time: 08:33
 */
@Component
@Slf4j
@Getter
public class CustomerMockDatabase {

  private List<Customer> customers;

  @PostConstruct
  public void initMockDatabase() {
    log.info("loading mockdata");
    InputStream jsonCustomerStream = this.getClass().getResourceAsStream("/customers.json");
    ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    try {
      customers = objectMapper.readValue(jsonCustomerStream, new TypeReference<List<Customer>>() {
      });
//      Customer[] custAsArray = objectMapper.readValue(jsonCustomerStream, Customer[].class);
      log.info("json mockdata successfully loaded");
      log.info(customers.get(0).toString());
      String customersStr = objectMapper.writerWithDefaultPrettyPrinter()
          .writeValueAsString(customers.get(0));
      log.info(customersStr);
    } catch (IOException e) {
      log.error("failed loading json-mockdata");
      log.error(e.toString());
    }
  }

  public Optional<Customer> getCustomerById(Long id) {
    return customers.stream()
        .filter(customer -> customer.getId().equals(id))
        .findFirst();
  }

  public Optional<Customer> addCustomer(Customer customer) {
    if (customer.getId() == null) {
      Long maxId = customers.stream()
          .mapToLong(Customer::getId)
          .max()
          .getAsLong() + 1;
      customer.setId(maxId);
      customers.add(customer);
    } else {
      if (customers.contains(customer)) {
        return Optional.empty();
      } else {
        customers.add(customer);
      }
    }
    return Optional.of(customer);
  }

  public Optional<Customer> createOrSetCustomer(Long customerId, Customer newCustomer) {
    Optional<Customer> customerOptional = getCustomerById(customerId);
    newCustomer.setId(customerId);
    if (customerOptional.isPresent()) {
      customers.set(customers.indexOf(customerOptional.get()), newCustomer);
    } else {
      customers.add(newCustomer);
    }
    return Optional.of(newCustomer);
  }

  public Optional<Customer> updateCustomer(Long customerId, Customer updatedCustomer) {
    Optional<Customer> customerOptional = getCustomerById(customerId);
    if (customerOptional.isEmpty()) {
      return Optional.empty();
    }
    Customer customerInDB = customerOptional.get();
    customerInDB.setId(null);
    Field[] fields = Customer.class.getDeclaredFields();
    for (Field field : fields) {
      field.setAccessible(true);
      Object value = ReflectionUtils.getField(field, updatedCustomer);
      if (value != null) {
        ReflectionUtils.setField(field, customerInDB, value);
      }
    }
    return Optional.of(customerInDB);
  }
}

