package org.souzatech.tasks.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.souzatech.tasks.enums.TaskStatus;

import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Task {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String title;

    private String description;

    private LocalDate deadline;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    private double duration;

    @Enumerated(EnumType.STRING)
    private TaskStatus taskStatus;

    private Instant createAt;

    private Instant updateAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="person_id")
    @JsonIgnore
    private Person person;

    @PrePersist
    public void prePersist(){
        createAt = Instant.now();
        taskStatus = TaskStatus.OPEN;
    }

    @PreUpdate
    public void preUpdate(){
        updateAt = Instant.now();
    }

}
