package com.lawzoom.complianceservice.controller.documentController;

import com.lawzoom.complianceservice.dto.documentDto.DocumentRequest;
import com.lawzoom.complianceservice.response.ResponseEntity;
import com.lawzoom.complianceservice.services.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.Optional;

@RestController
@RequestMapping("/compliance/task/document")
public class TaskDocumentController {

	@Autowired
	private DocumentService documentService;

	@GetMapping()
	public ResponseEntity fetchAllDocument(@PathVariable("taskId") Long taskId){
		return this.documentService.fetchAllTaskDocument(taskId);
	}

	@PostMapping(value = "/save", consumes = {"multipart/form-data"})
	public ResponseEntity<String> saveDocument(
			@RequestPart(name = "file", required = false) MultipartFile file,
			@RequestParam Long taskId,@ModelAttribute DocumentRequest documentRequest
		) {
		documentService.saveTaskDocument(taskId, file,documentRequest);
		return new ResponseEntity().ok("Document saved successfully.");
	}


	@PutMapping("/update")
	public ResponseEntity updateDocument(
			@RequestParam("file") Optional<MultipartFile> file,@PathVariable("taskId") Long taskId){
		return this.documentService.updateTaskDocument(file,taskId);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity fetchDocument(@PathVariable("id") Long id,@PathVariable("taskId") Long taskId){
		return this.documentService.fetchTaskDocument(id,taskId);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity deleteDocument(@PathVariable("id") Long id,@PathVariable("taskId") Long taskId){
		return this.documentService.deleteTaskDocument(id,taskId);
	}
}
