package com.lawzoom.complianceservice.model.mileStoneTask;

import com.lawzoom.complianceservice.model.complianceMileStoneModel.MileStone;
import com.lawzoom.complianceservice.model.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "task")
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(columnDefinition = "TEXT")
	private String description;

	@Column(name = "timeline_value")
	private int timelineValue;

	@Column(name = "timeline_type")
	private String timelineType;

	private String status;

	@Column(name = "created_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;

	@Column(name = "updated_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt;

	@Column(length = 1, name = "is_enable", columnDefinition = "tinyint(1) default 1")
	@Comment(value = "1 : Active, 0 : Inactive")
	private boolean isEnable;

	@Column(name = "start_date")
	@Temporal(TemporalType.DATE)
	private Date startDate;

	@Column(name = "due_date")
	@Temporal(TemporalType.DATE)
	private Date dueDate;

	@Column(name = "completed_date")
	@Temporal(TemporalType.DATE)
	private Date completedDate;

	@ManyToOne
	@JoinColumn(name = "reporter_id")
	private User reporterUserId;

	@ManyToOne
	@JoinColumn(name = "assignee_id")
	private User assigneeUserId;

	private String criticality;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "milestone_id", nullable = false)
	private MileStone milestone;
}
