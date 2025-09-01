package com.firdose.week4RestClient.week4Tutorials.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "department")
@ToString
public class DepartmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private Integer departmentCapacity;
    private LocalDate departmentCreated;
    private Boolean isActive;

//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "manager_id")
//    private EmployeeEntity manager;

//    @OneToMany(mappedBy = "workerDepartment",fetch = FetchType.LAZY)
//    private Set<EmployeeEntity> workers;
//
//    @ManyToMany(mappedBy = "freelanceDepartments")
//    private Set<EmployeeEntity> freelancers;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DepartmentEntity that = (DepartmentEntity) o;
        return id == that.id && Objects.equals(title, that.title) && Objects.equals(departmentCapacity, that.departmentCapacity) && Objects.equals(departmentCreated, that.departmentCreated) && Objects.equals(isActive, that.isActive);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, departmentCapacity, departmentCreated, isActive);
    }
}
