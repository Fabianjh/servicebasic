package sserviceproject.servicebasic.client;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String surname;
    private LocalDateTime createTime;
    private LocalDateTime lastUpdateTime;

    @PrePersist
    public void prePersist() {
        createTime = LocalDateTime.now();
        lastUpdateTime = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        lastUpdateTime = LocalDateTime.now();
    }
}
