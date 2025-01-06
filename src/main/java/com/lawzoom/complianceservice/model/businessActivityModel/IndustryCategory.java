package com.lawzoom.complianceservice.model.businessActivityModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

import jakarta.persistence.*;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@NoArgsConstructor
@Table(name = "industry_category")
@Getter
@Setter
@AllArgsConstructor
public class IndustryCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String industryName;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(length = 1, name = "is_enable", columnDefinition = "tinyint(1) default 1")
    @Comment(value = "1 : Active, 0 : Inactive")
    private boolean isEnable;

    @OneToMany(mappedBy = "industryCategory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<IndustrySubCategory> industrySubCategories = new ArrayList<>();

    private boolean isDeleted=false;

    private LocalDate date;

}
