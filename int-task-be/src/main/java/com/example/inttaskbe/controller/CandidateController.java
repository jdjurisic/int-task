package com.example.inttaskbe.controller;

import com.example.inttaskbe.dto.CandidateDto;
import com.example.inttaskbe.exception.EntityExistsException;
import com.example.inttaskbe.exception.InvalidEntityException;
import com.example.inttaskbe.service.CandidateService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("candidate")
public class CandidateController {
    private final CandidateService candidateService;

    public CandidateController(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    @GetMapping
    public List<CandidateDto> findAll() {
        return candidateService.findAll();
    }

    @GetMapping("filter")
    public ResponseEntity<Page<CandidateDto>> findAll(@RequestParam(defaultValue = "0") Integer pageNo,
                                                  @RequestParam(defaultValue = "5") Integer pageSize, @RequestParam(defaultValue = "name") String sortBy,
                                                  @RequestParam(defaultValue = "asc") String sortOrder) {
//		return ResponseEntity.status(HttpStatus.OK).body(cityService.findAll(pageNo, pageSize, sortBy));
        return new ResponseEntity<Page<CandidateDto>>(candidateService.findAll(pageNo, pageSize, sortBy, sortOrder), new HttpHeaders(),
                HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> findById(@PathVariable Long id) {
        Optional<CandidateDto> candidate = candidateService.findById(id);
        if (candidate.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(candidate.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid candidate id!");
    }

    @PostMapping()
    public ResponseEntity<Object> save(@Valid @RequestBody CandidateDto candidateDto) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(candidateService.save(candidateDto));
        } catch (EntityExistsException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @PutMapping("{id}")
    public @ResponseBody
    ResponseEntity<Object> update(@PathVariable Long id, @Valid @RequestBody CandidateDto candidateDto) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(candidateService.update(candidateDto));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            candidateService.deleteById(id);
            return ResponseEntity.ok("Deleted");
        } catch (InvalidEntityException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}
