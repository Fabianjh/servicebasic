package sserviceproject.servicebasic.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sserviceproject.servicebasic.client.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
