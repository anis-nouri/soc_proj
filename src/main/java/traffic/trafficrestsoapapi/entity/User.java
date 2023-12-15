package traffic.trafficrestsoapapi.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    private String username;
    private String password;
    private String nom;
    private String prenom;
    private String lieu ;
    private String position;


}