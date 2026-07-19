package vn.unigap.api.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "job_province")
public class Province {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "slug")
    private String slug;

    @OneToMany(mappedBy = "province")
    private List<Seeker> seekers;

    @ManyToMany(mappedBy = "provincesEntity")
    private Set<Job> jobs;

    @ManyToMany(mappedBy = "provinceSet")
    private Set<Resume> resumes;

}
