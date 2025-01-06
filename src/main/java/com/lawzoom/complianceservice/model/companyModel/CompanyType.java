package com.lawzoom.complianceservice.model.companyModel;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "companyType")
public class CompanyType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String companyTypeName;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(length = 1,name="is_enable",columnDefinition = "tinyint(1) default 1")
    @Comment(value = "1 : Active, 0 : Inactive")
    private boolean isEnable;

    @Column(name = "user_id")
    private Long userId;

    private boolean isDeleted=false;

    private LocalDate date;



}
