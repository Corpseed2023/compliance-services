package com.lawzoom.complianceservice.dto.DepartmentDTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentResponse {
    private Long id;
    private String departmentName;
    private boolean isEnable;
    private Date createdAt;
    private Date updatedAt;
    private List<String> designations;
}
