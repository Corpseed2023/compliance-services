package com.lawzoom.complianceservice.utility;

import com.lawzoom.complianceservice.dto.complianceDto.ComplianceRequest;
import com.lawzoom.complianceservice.dto.complianceDto.ComplianceResponse;
import com.lawzoom.complianceservice.dto.complianceGuideDto.ComplianceGuideRequest;
import com.lawzoom.complianceservice.dto.complianceGuideDto.ComplianceGuideResponse;
import com.lawzoom.complianceservice.dto.complianceSubTaskDto.ComplianceSubTaskRequest;
import com.lawzoom.complianceservice.dto.complianceSubTaskDto.ComplianceSubTaskResponse;
import com.lawzoom.complianceservice.dto.complianceTaskDto.ComplianceTaskRequest;
import com.lawzoom.complianceservice.dto.complianceTaskDto.ComplianceTaskResponse;
import com.lawzoom.complianceservice.dto.compliancemap.ComplianceMasterResponse;
import com.lawzoom.complianceservice.dto.documentDto.DocumentRequest;
import com.lawzoom.complianceservice.dto.documentDto.DocumentResponse;
import com.lawzoom.complianceservice.dto.legalGuideDto.LegalGuideRequest;
import com.lawzoom.complianceservice.dto.legalGuideDto.LegalGuideResponse;
import com.lawzoom.complianceservice.dto.reminderResponse.ReminderResponse;
import com.lawzoom.complianceservice.dto.renewalReminderResponse.RenewalReminderResponse;
import com.lawzoom.complianceservice.dto.taskActionDto.TaskActionRequest;
import com.lawzoom.complianceservice.dto.taskActionDto.TaskActionResponse;
import com.lawzoom.complianceservice.model.complianceGuide.ComplianceGuide;
import com.lawzoom.complianceservice.model.complianceModel.Compliance;
import com.lawzoom.complianceservice.model.complianceSubTaskModel.ComplianceSubTask;
import com.lawzoom.complianceservice.model.complianceTaskModel.ComplianceTask;
import com.lawzoom.complianceservice.model.documentModel.Document;
import com.lawzoom.complianceservice.model.legalGuideModel.LegalGuide;
import com.lawzoom.complianceservice.model.reminderModel.Reminder;
import com.lawzoom.complianceservice.model.renewalModel.RenewalReminder;
import com.lawzoom.complianceservice.model.taskActionModel.TaskAction;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResponseMapper {

    /*public ComplianceCategoryResponse mapToComplianceCategoryResponse(ComplianceCategory cc) {
        if(cc==null)return null;
        return ComplianceCategoryResponse.builder().id(cc.getId()).title(cc.getTitle())
                .categorySlug(cc.getSlug()).isEnable(cc.isEnable())
                .createdAt(cc.getCreatedAt()).build();
    }

    public ComplianceCategory mapToSaveComplianceCategory(ComplianceCategoryRequest ccRequest,int superUserId) {
        if(ccRequest==null)return null;
        return ComplianceCategory.builder().title(ccRequest.getTitle())
                .slug(ccRequest.getCategorySlug()).isEnable(true)
                .createdAt(CommonUtil.getDate()).build();
    }

    public ComplianceCategory mapToUpdateComplianceCategory(ComplianceCategoryRequest ccRequest, int superUserId) {
        if(ccRequest==null)return null;
        return ComplianceCategory.builder().id(ccRequest.getId()).title(ccRequest.getTitle())
                .slug(ccRequest.getCategorySlug()).isEnable(ccRequest.isEnable())
                .createdAt(ccRequest.getCreatedAt()).build();
    }*/

    public ComplianceResponse mapToComplianceResponse(Compliance compliance) {
        if(compliance==null)return null;
        return ComplianceResponse.builder().id(compliance.getId()).title(compliance.getTitle())
                .description(compliance.getDescription()).approvalState(compliance.getApprovalState())
                .applicableZone(compliance.getApplicableZone()).priority(compliance.getPriority())
                .isEnable(compliance.isEnable()).createdAt(compliance.getCreatedAt()).updatedAt(compliance.getUpdatedAt())
                .startDate(compliance.getStartDate()).dueDate(compliance.getDueDate()).duration(compliance.getDuration())
                .workStatus(compliance.getWorkStatus()).completedDate(compliance.getCompletedDate()).build();
    }

    public Compliance mapToSaveCompliance(ComplianceRequest cr, Long companyId,Long businessUnitId) {
        if(cr==null)return null;
        return Compliance.builder().title(cr.getTitle()).description(cr.getDescription())
                .approvalState(cr.getApprovalState()).applicableZone(cr.getApplicableZone())
                .isEnable(true).createdAt(CommonUtil.getDate()).updatedAt(CommonUtil.getDate())
                .workStatus(0).priority(cr.getPriority()).startDate(cr.getStartDate())
                .dueDate(cr.getDueDate()).completedDate(cr.getCompletedDate())
                .companyId(companyId).duration(cr.getDuration()).businessUnitId(businessUnitId).build();
    }

    public Compliance mapToUpdateCompliance(ComplianceRequest cr, Long companyId,Long businessUnitId) {
        if(cr==null)return null;
        return Compliance.builder().id(cr.getId()).title(cr.getTitle()).description(cr.getDescription())
                .approvalState(cr.getApprovalState()).applicableZone(cr.getApplicableZone()).priority(cr.getPriority())
                .isEnable(cr.isEnable()).createdAt(cr.getCreatedAt()).updatedAt(CommonUtil.getDate()).workStatus(cr.getWorkStatus())
                .startDate(cr.getStartDate()).dueDate(cr.getDueDate()).completedDate(cr.getCompletedDate())
                .companyId(companyId).duration(cr.getDuration()).businessUnitId(businessUnitId).build();
    }

    public ComplianceTaskResponse mapToComplianceTaskResponse(ComplianceTask t) {
        if(t==null)return null;
        return ComplianceTaskResponse.builder().id(t.getId()).taskName(t.getTaskName())
                .timelineValue(t.getTimelineValue()).timelineType(t.getTimelineType())
                .status(t.getStatus()).approvalState(t.getApprovalState()).applicableZone(t.getApplicableZone())
                .isEnable(t.isEnable()).createdAt(t.getCreatedAt()).updatedAt(t.getUpdatedAt()).startDate(t.getStartDate())
                .dueDate(t.getDueDate()).completedDate(t.getCompletedDate())
                .assigneeUserId(t.getAssigneeUserId()).reporterUserId(t.getReporterUserId())
                .description(t.getDescription()).criticality(t.getCriticality()).build();
    }

    public ComplianceSubTaskResponse mapToComplianceSubTask(ComplianceSubTask t) {
        if(t==null)return null;
        return ComplianceSubTaskResponse.builder().id(t.getId()).subTaskName(t.getSubTaskName())
                .timelineValue(t.getTimelineValue()).timelineType(t.getTimelineType())
                .status(t.getStatus()).approvalState(t.getApprovalState()).applicableZone(t.getApplicableZone())
                .isEnable(t.isEnable()).createdAt(t.getCreatedAt()).startDate(t.getStartDate())
                .dueDate(t.getDueDate()).completedDate(t.getCompletedDate())
                .assigneeUserId(t.getAssigneeUserId()).reporterUserId(t.getReporterUserId())
                .criticality(t.getCriticality()).updatedAt(t.getUpdatedAt()).build();
    }

    public ReminderResponse mapToReminderResponse(Reminder reminder) {
        if(reminder==null)return null;
        return ReminderResponse.builder().id(reminder.getId()).reminderDate(reminder.getReminderDate())
                .notificationTimelineValue(reminder.getNotificationTimelineValue())
                .notificationTimelineType(reminder.getNotificationTimelineType())
                .repeatTimelineValue(reminder.getRepeatTimelineValue())
                .repeatTimelineType(reminder.getRepeatTimelineType())
                .notificationTimelineType(reminder.getNotificationTimelineType())
                .repeatOnDay(reminder.getRepeatOnDay()).reminderEndDate(reminder.getReminderEndDate())
                .createdAt(reminder.getCreatedAt()).updatedAt(reminder.getUpdatedAt()).build();
    }

    public RenewalReminderResponse mapToRenewalReminderResponse(RenewalReminder rr) {
        if(rr==null)return null;
        return RenewalReminderResponse.builder().id(rr.getId()).certificateIssueDate(rr.getCertificateIssueDate())
                .certificateIssueForValue(rr.getCertificateIssueForValue()).certificateIssueForType(rr.getCertificateIssueForType())
                .notificationTimelineValue(rr.getNotificationTimelineValue()).notificationTimelineType(rr.getNotificationTimelineType())
                .createdAt(rr.getCreatedAt()).updatedAt(rr.getUpdatedAt()).build();
    }

    public ComplianceTask mapToSaveComplianceTask(ComplianceTaskRequest task,Compliance compliance) {
        if(task==null)return null;
        return ComplianceTask.builder().taskName(task.getTaskName()).timelineValue(task.getTimelineValue())
                .timelineType(task.getTimelineType()).status(task.getStatus()).approvalState(task.getApprovalState())
                .applicableZone(task.getApplicableZone()).isEnable(true).createdAt(CommonUtil.getDate())
                .startDate(task.getStartDate()).dueDate(task.getDueDate()).completedDate(task.getCompletedDate())
                .reporterUserId(task.getReporterUserId()).assigneeUserId(task.getAssigneeUserId())
                .criticality(task.getCriticality()).description(task.getDescription())
                .updatedAt(CommonUtil.getDate()).compliance(compliance).build();
    }

    public ComplianceTask mapToUpdateComplianceTask(ComplianceTaskRequest task,Compliance compliance) {
        if(task==null)return null;
        return ComplianceTask.builder().id(task.getId()).taskName(task.getTaskName()).timelineValue(task.getTimelineValue())
                .timelineType(task.getTimelineType()).status(task.getStatus()).approvalState(task.getApprovalState())
                .applicableZone(task.getApplicableZone()).isEnable(task.isEnable()).createdAt(task.getCreatedAt())
                .startDate(task.getStartDate()).dueDate(task.getDueDate()).completedDate(task.getCompletedDate())
                .reporterUserId(task.getReporterUserId()).assigneeUserId(task.getAssigneeUserId())
                .criticality(task.getCriticality()).updatedAt(CommonUtil.getDate())
                .description(task.getDescription()).compliance(compliance).build();
    }

    public ComplianceSubTask mapToSaveComplianceSubTask(ComplianceSubTaskRequest subTask,ComplianceTask complianceTask) {
        if(subTask==null)return null;
        return ComplianceSubTask.builder().subTaskName(subTask.getSubTaskName())
                .timelineValue(subTask.getTimelineValue()).timelineType(subTask.getTimelineType())
                .status(subTask.getStatus()).approvalState(subTask.getApprovalState())
                .applicableZone(subTask.getApplicableZone()).isEnable(true)
                .createdAt(CommonUtil.getDate()).startDate(subTask.getStartDate())
                .dueDate(subTask.getDueDate()).completedDate(subTask.getCompletedDate())
                .reporterUserId(subTask.getReporterUserId()).assigneeUserId(subTask.getAssigneeUserId())
                .criticality(subTask.getCriticality()).updatedAt(CommonUtil.getDate())
                .description(subTask.getDescription()).complianceTask(complianceTask).build();

    }

    public ComplianceSubTask mapToUpdateComplianceSubTask(ComplianceSubTaskRequest subTask, ComplianceTask complianceTask) {
        if(subTask==null)return null;
        return ComplianceSubTask.builder().id(subTask.getId()).subTaskName(subTask.getSubTaskName())
                .timelineValue(subTask.getTimelineValue()).timelineType(subTask.getTimelineType())
                .status(subTask.getStatus()).approvalState(subTask.getApprovalState())
                .applicableZone(subTask.getApplicableZone()).isEnable(subTask.isEnable())
                .createdAt(subTask.getCreatedAt()).startDate(subTask.getStartDate())
                .dueDate(subTask.getDueDate()).completedDate(subTask.getCompletedDate())
                .reporterUserId(subTask.getReporterUserId()).assigneeUserId(subTask.getAssigneeUserId())
                .criticality(subTask.getCriticality()).updatedAt(CommonUtil.getDate())
                .description(subTask.getDescription()).complianceTask(complianceTask).build();
    }

    public ComplianceSubTaskResponse mapToComplianceSubTaskResponse(ComplianceSubTask subTask) {
        if(subTask==null)return null;
        return ComplianceSubTaskResponse.builder().id(subTask.getId()).subTaskName(subTask.getSubTaskName())
                .timelineValue(subTask.getTimelineValue()).timelineType(subTask.getTimelineType())
                .status(subTask.getStatus()).approvalState(subTask.getApprovalState())
                .applicableZone(subTask.getApplicableZone()).isEnable(subTask.isEnable())
                .createdAt(subTask.getCreatedAt()).startDate(subTask.getStartDate()).dueDate(subTask.getDueDate())
                .assigneeUserId(subTask.getAssigneeUserId()).reporterUserId(subTask.getReporterUserId())
                .description(subTask.getDescription()).completedDate(subTask.getCompletedDate())
                .updatedAt(subTask.getUpdatedAt()).build();
    }

    public Document mapToSaveDocument(DocumentRequest d,Compliance compliance,
                                      ComplianceTask complianceTask,
                                      ComplianceSubTask complianceSubTask) {
        if(d==null)return null;
        return Document.builder().documentName(d.getDocumentName()).issueDate(d.getIssueDate())
                .referenceNumber(d.getReferenceNumber()).remarks(d.getRemarks())
                .compliance(compliance).complianceTask(complianceTask).complianceSubTask(complianceSubTask)
                .createdAt(CommonUtil.getDate()).updatedAt(CommonUtil.getDate()).build();
    }

    public Document mapToUpdateDocument(DocumentRequest d,Compliance compliance,
                                        ComplianceTask complianceTask,
                                        ComplianceSubTask complianceSubTask) {
        if(d==null)return null;
        return Document.builder().id(d.getId()).documentName(d.getDocumentName()).issueDate(d.getIssueDate())
                .referenceNumber(d.getReferenceNumber()).remarks(d.getRemarks())
                .compliance(compliance).complianceTask(complianceTask).complianceSubTask(complianceSubTask)
                .createdAt(d.getCreatedAt()).updatedAt(CommonUtil.getDate()).build();
    }

    public DocumentResponse mapToDocumentResponse(Document d) {
        if(d==null)return null;
        return DocumentResponse.builder().id(d.getId()).documentName(d.getDocumentName())
                .fileName(d.getFileName()).issueDate(d.getIssueDate())
                .referenceNumber(d.getReferenceNumber()).remarks(d.getRemarks())
                .createdAt(d.getCreatedAt()).updatedAt(d.getUpdatedAt())
                .uploadDate(d.getUploadDate()).build();
    }

    public ComplianceGuideResponse mapToGuideResponse(ComplianceGuide guide) {
        if(guide==null)return null;
        return ComplianceGuideResponse.builder().id(guide.getId()).jurisdiction(guide.getJurisdiction())
                .description(guide.getDescription()).isEnable(guide.isEnable())
                .createdAt(guide.getCreatedAt()).updatedAt(guide.getUpdatedAt()).build();
    }

    public ComplianceGuide mapToSaveComplianceGuide(ComplianceGuideRequest guide) {
        if(guide==null)return null;
        return ComplianceGuide.builder().jurisdiction(guide.getJurisdiction())
                .description(guide.getDescription()).isEnable(true)
                .createdAt(CommonUtil.getDate()).updatedAt(CommonUtil.getDate()).build();
    }

    public ComplianceGuide mapToUpdateComplianceGuide(ComplianceGuideRequest guide) {
        if(guide==null)return null;
        return ComplianceGuide.builder().id(guide.getId()).jurisdiction(guide.getJurisdiction())
                .description(guide.getDescription()).isEnable(guide.isEnable())
                .createdAt(guide.getCreatedAt()).updatedAt(CommonUtil.getDate()).build();
    }

    public LegalGuideResponse mapToLegalGuideResponse(LegalGuide l) {
        if(l==null)return null;
        return LegalGuideResponse.builder().id(l.getId()).title(l.getTitle())
                .referenceSlug(l.getReferenceSlug()).description(l.getDescription())
                .department(l.getDepartment()).authority(l.getAuthority())
                .publishDate(l.getPublishDate()).applicableDate(l.getApplicableDate())
                .isEnable(l.isEnable()).createdAt(l.getCreatedAt()).updatedAt(l.getUpdatedAt())
                .build();

    }

    public LegalGuide mapToSaveLegalGuide(LegalGuideRequest l, ComplianceGuide complianceGuide) {
        if(l==null)return null;
        return LegalGuide.builder().complianceGuide(complianceGuide).title(l.getTitle())
                .referenceSlug(l.getReferenceSlug()).description(l.getDescription())
                .department(l.getDepartment()).authority(l.getAuthority())
                .publishDate(l.getPublishDate()).applicableDate(l.getApplicableDate())
                .isEnable(true).createdAt(CommonUtil.getDate()).updatedAt(CommonUtil.getDate()).build();
    }

    public LegalGuide mapToUpdateLegalGuide(LegalGuideRequest l, ComplianceGuide complianceGuide) {
        if(l==null)return null;
        return LegalGuide.builder().id(l.getId()).complianceGuide(complianceGuide).title(l.getTitle())
                .referenceSlug(l.getReferenceSlug()).description(l.getDescription())
                .department(l.getDepartment()).authority(l.getAuthority())
                .publishDate(l.getPublishDate()).applicableDate(l.getApplicableDate())
                .isEnable(l.isEnable()).createdAt(l.getCreatedAt()).updatedAt(CommonUtil.getDate()).build();
    }

    public TaskActionResponse mapToTaskActionResponse(TaskAction t) {
        if(t==null)return null;
        return TaskActionResponse.builder().id(t.getId()).status(t.getStatus())
                .description(t.getDescription()).createdAt(t.getCreatedAt())
                .updatedAt(t.getUpdatedAt()).build();
    }

    public TaskAction mapToSaveTaskAction(TaskActionRequest taskActionRequest) {
        if(taskActionRequest==null)return null;
        return TaskAction.builder().status(taskActionRequest.getStatus())
                .description(taskActionRequest.getDescription())
                .createdAt(CommonUtil.getDate()).updatedAt(CommonUtil.getDate()).build();
    }

    public TaskAction mapToUpdateTaskAction(TaskActionRequest taskActionRequest) {
        return TaskAction.builder().id(taskActionRequest.getId())
                .status(taskActionRequest.getStatus())
                .description(taskActionRequest.getDescription())
                .createdAt(taskActionRequest.getCreatedAt()).updatedAt(CommonUtil.getDate())
                .build();
    }

    public Compliance mapToSaveComplianceInternal(ComplianceMasterResponse cm,
                                                   Long companyId,Long businessUnitId) {
        if(cm==null)return null;
        Compliance compliance=Compliance.builder().id(0L).title(cm.getTitle())
                .description(cm.getDescription()).createdAt(CommonUtil.getDate())
                .updatedAt(CommonUtil.getDate()).isEnable(true).duration(cm.getDuration())
                .workStatus(0).categoryId(cm.getCategoryId()).companyId(companyId)
                .businessUnitId(businessUnitId).priority(cm.getPriority())
                .build();

        List<ComplianceTask> complianceTasks=cm.getComplianceTaskResponseList().stream()
                .map(t->mapToSaveComplianceTaskInternal(t,compliance)).collect(Collectors.toList());

        compliance.setComplianceTasks(complianceTasks);

        return compliance;
    }

    private ComplianceTask mapToSaveComplianceTaskInternal(
            com.lawzoom.complianceservice.dto.compliancemap.ComplianceTaskResponse t,
            Compliance compliance) {
        if(t==null)return null;

        ComplianceTask complianceTask = ComplianceTask.builder().taskName(t.getTaskName()).timelineType(t.getTimelineType())
                .timelineValue(t.getTimelineValue()).status("Not Started")
                .createdAt(CommonUtil.getDate()).updatedAt(CommonUtil.getDate()).isEnable(true)
                .criticality(t.getCriticality()).compliance(compliance).build();

        List<ComplianceSubTask> complianceSubTasks = t.getComplianceSubTaskResponseList().stream()
                .map(s -> mapToSaveComplianceSubTaskInternal(s, complianceTask))
                .collect(Collectors.toList());

        complianceTask.setComplianceSubTasks(complianceSubTasks);

        return complianceTask;
    }

    private ComplianceSubTask mapToSaveComplianceSubTaskInternal(
            com.lawzoom.complianceservice.dto.compliancemap.ComplianceSubTaskResponse s,
            ComplianceTask complianceTask) {
        if(s==null)return null;

        return ComplianceSubTask.builder().subTaskName(s.getSubTaskName())
                .timelineValue(s.getTimelineValue()).timelineType(s.getTimelineType())
                .status("Not Started").createdAt(CommonUtil.getDate()).updatedAt(CommonUtil.getDate())
                .isEnable(true).criticality(s.getCriticality()).complianceTask(complianceTask).build();
    }
}
