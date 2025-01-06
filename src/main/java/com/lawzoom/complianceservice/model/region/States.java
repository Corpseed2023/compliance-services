package com.lawzoom.complianceservice.model.region;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "states")
public class States {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String stateName;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(length = 1,name="is_enable",columnDefinition = "tinyint(1) default 1")
    @Comment(value = "1 : Active, 0 : Inactive")
    private boolean isEnable;

    private LocalDate date;

    private boolean isDeleted=false;

    @ManyToOne
    @JoinColumn(name = "country_id",nullable = false)
    @JsonIgnore

    private Country country;

    @OneToMany(mappedBy = "states", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<City> cities =new ArrayList<>();

}
