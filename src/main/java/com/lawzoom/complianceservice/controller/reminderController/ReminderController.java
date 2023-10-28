package com.lawzoom.complianceservice.controller.reminderController;

import com.lawzoom.complianceservice.model.reminderModel.Reminder;
import com.lawzoom.complianceservice.response.ResponseEntity;
import com.lawzoom.complianceservice.services.reminderService.ReminderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/compliance/reminder")
public class ReminderController {

	@Autowired
	private ReminderService reminderService;

	@PostMapping("/setReminder")
	public ResponseEntity saveReminder(@Valid @RequestBody Reminder reminder){
		return this.reminderService.saveReminder(reminder);
	}
	
	@PutMapping("/updateReminder")
	public ResponseEntity updateReminder(@Valid @RequestBody Reminder reminder){
		return this.reminderService.updateReminder(reminder);
	}
	
	@GetMapping("/seeTheReminder")
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
