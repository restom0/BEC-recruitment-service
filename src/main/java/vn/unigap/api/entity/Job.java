package vn.unigap.api.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import vn.unigap.common.ProvincesDeserializer;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "jobs")
public class Job {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employer_id")
    private Employer employer;

    @Column(name = "title")
    private String title;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "description")
    private String description;

    @Column(name = "salary")
    private double salary;

    @Column(name = "fields")
    private String fields;

    @Column(name = "provinces")
    private String provinces;

    @Column(name = "expired_at")
    private LocalDate expiredAt;

    @Column(name = "created_at")
    @CreationTimestamp
    private Instant createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Instant updatedAt;

    @ManyToMany
    @JoinTable(
            name = "jobs_provinces",
            joinColumns = @JoinColumn(name = "job_id"),
            inverseJoinColumns = @JoinColumn(name = "province_id")
    )
    @JsonDeserialize(using = ProvincesDeserializer.class)
    private Set<Province> provincesEntity;


    public void setFieldsFromList(List<Integer> fieldIds) {
        this.fields = fieldIds.stream()
                .map(fieldId -> fieldId + "-")
                .collect(Collectors.joining());
    }

    public void setProvincesFromList(List<Integer> provinceIds) {
        this.provinces = provinceIds.stream()
                .map(provinceId -> provinceId + "-")
                .collect(Collectors.joining());
    }
}