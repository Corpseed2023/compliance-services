package com.lawzoom.complianceservice.repository.team;


import com.lawzoom.complianceservice.model.teamMemberModel.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface TeamMemberRepository extends JpaRepository<TeamMember,Long> {


    List<TeamMember> findByMemberMail(String memberMail);

    @Query("SELECT t FROM TeamMember t WHERE t.superAdminId.id = :superAdminId AND t.subscription.id = :subscriptionId")
    List<TeamMember> findBySuperAdminIdAndSubscriptionId(@Param("superAdminId") Long superAdminId,
                                                                   @Param("subscriptionId") Long subscriptionId);


    @Query("Select t from TeamMember t where t.isEnable = true and t.isDeleted = false")
    Optional<TeamMember> findById(@Param("id") Long id);



}