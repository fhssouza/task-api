package org.souzatech.tasks.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PersonResponse implements Serializable {

    private Long id;
    private String name;
    private String departmentName;

}