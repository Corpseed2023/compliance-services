package com.lawzoom.complianceservice.controller.reminderController;

import com.lawzoom.complianceservice.dto.reminderDto.ReminderRequest;
import com.lawzoom.complianceservice.model.complianceModel.Compliance;
import com.lawzoom.complianceservice.model.complianceTaskModel.ComplianceTask;
import com.lawzoom.complianceservice.model.reminderModel.Reminder;
import com.lawzoom.complianceservice.repository.ComplianceRepo;
import com.lawzoom.complianceservice.repository.ComplianceTaskRepository;
import com.lawzoom.complianceservice.response.ResponseEntity;
import com.lawzoom.complianceservice.services.reminderService.ReminderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/compliance/reminder")
public class ReminderController {

	@Autowired
	private ReminderService reminderService;

	@Autowired
	private ComplianceTaskRepository complianceTaskRepository;

	@Autowired
	private ComplianceRepo complianceRepository;

	@PostMapping("/save")
	public ResponseEntity<String> saveReminder(@RequestBody ReminderRequest requestDTO) {
		Optional<Compliance> complianceData = complianceRepository.findById(requestDTO.getComplianceId());

		if (complianceData.isPresent()) {
			Optional<ComplianceTask> complianceTaskOptional = complianceTaskRepository.findById(requestDTO.getTaskId());

			if (complianceTaskOptional.isPresent()) {
				ResponseEntity<String> savedReminder = reminderService.saveReminder(requestDTO);

				return savedReminder.ok("Reminder saved successfully");
			} else {
				return new ResponseEntity().ok("Task with ID " + requestDTO.getTaskId() + " not found");
			}
		} else {
			return  new ResponseEntity().ok("Compliance with ID " + requestDTO.getComplianceId() + " not found");
		}
	}


	@PutMapping("/updateReminder")
	public ResponseEntity updateReminder(@Valid @RequestBody Reminder reminder){
		return this.reminderService.updateReminder(reminder);
	}
	
	@GetMapping("/fetchTheReminder")
	public ResponseEntity fetchReminder(@RequestParam("id") Long id){
		return this.reminderService.fetchReminder(id);
	}
	
//	@DeleteMapping("/{id}")
	@DeleteMapping("/removeReminder")

	public ResponseEntity deleteReminder(@RequestParam("id") Long id)
	{
		return this.reminderService.deleteReminder(id);
	}
}
