package com.lawzoom.complianceservice.dto.complianceTaskDto;


import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MilestoneRequestForFetch {

    private Long businessUnitId;

    private Long subscriberId;

    private Long complianceId;

    private Long userId;
}

