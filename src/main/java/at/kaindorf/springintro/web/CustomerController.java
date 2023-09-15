package at.kaindorf.springintro.web;

import at.kaindorf.springintro.mockdatabase.CustomerMockDatabase;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
public class CustomerController {
  private final CustomerMockDatabase customerMockDatabase;
}
