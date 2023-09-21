package at.kaindorf.springintro.pojos;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Project: Exa_01_SpringIntro_4CHIF
 * Created by: SF
 * Date: 15.09.2023
 * Time: 08:15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Customer {
  @EqualsAndHashCode.Include
  private Long id;
  @JsonAlias({"first_name", "vorname"})
  private String firstName;
  @JsonAlias("last_name")
  private String lastName;
  private String email;
  @JsonIgnore
  private String gender;
  @JsonAlias("date_of_birth")
  @JsonFormat(pattern = "MM/dd/yyyy")
  private LocalDate dateOfBirth;

//  @Override
//  public boolean equals(Object o) {
//    if (this == o) return true;
//    if (!(o instanceof Customer customer)) return false;
//    return Objects.equals(getId(), customer.getId());
//  }
//
//  @Override
//  public int hashCode() {
//    return Objects.hash(getId());
//  }
}
