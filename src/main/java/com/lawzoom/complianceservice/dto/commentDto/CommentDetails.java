package com.lawzoom.complianceservice.dto.commentDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public  class CommentDetails {
    private Long id;
    private String commentText;
    private Long userId;
    private String userName;
    private Date createdAt;
}
