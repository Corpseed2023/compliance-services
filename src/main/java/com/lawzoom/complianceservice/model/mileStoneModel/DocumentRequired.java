package com.lawzoom.complianceservice.model.mileStoneModel;

import com.lawzoom.complianceservice.model.complianceModel.Compliance;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "document_required")
public class DocumentRequired {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "compliance_id", nullable = false)
    private Compliance compliance;

}
