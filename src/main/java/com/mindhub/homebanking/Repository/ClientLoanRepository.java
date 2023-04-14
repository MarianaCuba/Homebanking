
package com.mindhub.homebanking.Repository;


import com.mindhub.homebanking.Models.ClientLoan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ClientLoanRepository extends JpaRepository<ClientLoan, Long> {
//    List<Client> findByFirstName(String firstName);
}
