package com.lawzoom.complianceservice.controller.reminderController;

import com.lawzoom.complianceservice.model.reminderModel.Reminder;
import com.lawzoom.complianceservice.response.ResponseEntity;
import com.lawzoom.complianceservice.service.reminderService.ReminderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/compliance/reminder")
public class ReminderController {

	@Autowired
	private ReminderService reminderService;

	@PostMapping("/save")
	public ResponseEntity saveReminder(@Valid @RequestBody Reminder reminder){
		return this.reminderService.saveReminder(reminder);
	}
	
	@PutMapping("/update")
	public ResponseEntity updateReminder(@Valid @RequestBody Reminder reminder){
		return this.reminderService.updateReminder(reminder);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity fetchReminder(@PathVariable("id") Long id){
		return this.reminderService.fetchReminder(id);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity deleteReminder(@PathVariable("id") Long id){
		return this.reminderService.deleteReminder(id);
	}
}
