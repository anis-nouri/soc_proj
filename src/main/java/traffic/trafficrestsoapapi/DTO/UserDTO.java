package traffic.trafficrestsoapapi.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
        private String username;
        private String nom;
        private String prenom;
        private String lieu;
        private String position;
}
