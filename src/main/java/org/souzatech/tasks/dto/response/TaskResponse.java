package org.souzatech.tasks.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.souzatech.tasks.enums.TaskStatus;

import java.io.Serializable;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TaskResponse implements Serializable {

    private Long id;
    private String title;
    private String description;
    private LocalDate deadline;
    private double duration;
    private TaskStatus taskStatus;

}