//package com.lawzoom.complianceservice.model;
//
//
//import lombok.Data;
//import org.hibernate.annotations.CreationTimestamp;
//import org.springframework.data.annotation.CreatedDate;
//
//import javax.persistence.*;
//import java.util.Date;
//
//@Entity
//@Data
//public class Ticket {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @CreatedDate
//    @CreationTimestamp
//    private Date creationDate;
//
//    @Column(nullable = false)
//    private String subject;
//
//    @Column(nullable = false)
//    private String description;
//
//    private boolean status = true;
//
//
//}