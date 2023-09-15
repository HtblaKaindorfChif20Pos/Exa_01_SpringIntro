package at.kaindorf.springintro.mockdatabase;

import at.kaindorf.springintro.pojos.Customer;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Project: Exa_01_SpringIntro_4CHIF
 * Created by: SF
 * Date: 15.09.2023
 * Time: 08:33
 */
@Component
@Slf4j
public class CustomerMockDatabase {

  private List<Customer> customers;

  @PostConstruct
  public void initMockDatabase() {
    log.info("loading mockdata");
    InputStream jsonCustomerStream = this.getClass().getResourceAsStream("/customers.json");
    ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    try {
      customers = objectMapper.readValue(jsonCustomerStream, new TypeReference<List<Customer>>() {});
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

}
