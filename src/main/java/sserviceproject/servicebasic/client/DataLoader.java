package sserviceproject.servicebasic.client;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import sserviceproject.servicebasic.repositories.ClientRepository;

@Component
public class DataLoader implements CommandLineRunner {

    private final ClientRepository clientRepository;

    public DataLoader(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (clientRepository.count() == 0) {
            Client c1 = new Client(null, "Juan", "Perez", null, null);
            Client c2 = new Client(null, "Ana", "Gomez", null, null);
            Client c3 = new Client(null, "Luis", "Ramirez", null, null);

            clientRepository.save(c1);
            clientRepository.save(c2);
            clientRepository.save(c3);
        }
    }
}
