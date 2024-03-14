package org.souzatech.tasks.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PersonDurationResponse implements Serializable {

    private String name;
    private String departmentName;
    private double taskDurations;

}