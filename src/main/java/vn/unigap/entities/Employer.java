package vn.unigap.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name="employer")
public class Employer {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    public long id;
    @Column(name="email")
    public String email;
    @Column(name="name")
    public String name;
    @Column(name="province")
    public Integer province;
    @Column(name="description")
    public String description;
    @Column(name="created_at")
    public Date created_at;
    @Column(name="updated_at")
    public Date updated_at;

    // Default constructor
    public Employer() {
    }

    // Constructor with parameters
    public Employer(String email, String name, Integer province, String description, Date created_at, Date updated_at) {
        this.email = email;
        this.name = name;
        this.province = province;
        this.description = description;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }
}
