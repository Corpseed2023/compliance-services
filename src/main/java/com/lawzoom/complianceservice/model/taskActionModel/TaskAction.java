//package com.lawzoom.complianceservice.model.taskActionModel;
//
//import com.lawzoom.complianceservice.model.complianceSubTaskModel.ComplianceSubTask;
//import com.lawzoom.complianceservice.model.complianceTaskModel.ComplianceTask;
//import jakarta.persistence.Entity;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.NotEmpty;
//import lombok.*;
//import org.hibernate.annotations.Comment;
//import jakarta.persistence.*;
//import java.util.Date;
//
//@AllArgsConstructor
//@NoArgsConstructor
//@Getter
//@Setter
//@Builder
//@Entity
//@Table(name = "task_action")
//public class TaskAction {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne(targetEntity = ComplianceTask.class)
//    @JoinColumn(name = "task_id")
//    private ComplianceTask complianceTask;
//
//    @ManyToOne(targetEntity = ComplianceSubTask.class)
//    @JoinColumn(name = "sub_task_id")
//    private ComplianceSubTask complianceSubTask;
//
//    @NotBlank
//    @NotEmpty
//    private String status;
//
//    @NotBlank
//    @NotEmpty
//    private String description;
//
//    @Column(name = "created_at")
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date createdAt;
//
//    @Column(name = "updated_at")
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date updatedAt;
//
//    @Column(length = 1,name="is_enable",columnDefinition = "tinyint(1) default 1")
//    @Comment(value = "1 : Active, 0 : Inactive")
//    private boolean isEnable;
//
//}
