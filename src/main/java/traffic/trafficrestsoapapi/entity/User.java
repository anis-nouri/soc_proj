package traffic.trafficrestsoapapi.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {
    @Id
    private String username;
    private String password;
    private String nom;
    private String prenom;
    private String lieu ;
    private String position;
}