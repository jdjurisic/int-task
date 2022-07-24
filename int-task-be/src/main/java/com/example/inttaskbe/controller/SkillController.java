package com.example.inttaskbe.controller;

import com.example.inttaskbe.dao.SkillDao;
import com.example.inttaskbe.dto.SkillDto;
import com.example.inttaskbe.exception.EntityExistsException;
import com.example.inttaskbe.exception.InvalidEntityException;
import com.example.inttaskbe.service.SkillService;
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
@RequestMapping("skill")
public class SkillController {

    private final SkillService skillService;

    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    @GetMapping
    public List<SkillDto> findAll() {
        return skillService.findAll();
    }

    @GetMapping("filter")
    public ResponseEntity<Page<SkillDto>> findAll(@RequestParam(defaultValue = "0") Integer pageNo,
                                                  @RequestParam(defaultValue = "5") Integer pageSize, @RequestParam(defaultValue = "name") String sortBy,
                                                  @RequestParam(defaultValue = "asc") String sortOrder,
                                                  @RequestParam(required = false, defaultValue = "") String nameFilter) {
        return new ResponseEntity<Page<SkillDto>>(skillService.findAll(pageNo, pageSize, sortBy, sortOrder, nameFilter), new HttpHeaders(),
                HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> findById(@PathVariable Long id) {
        Optional<SkillDto> skill = skillService.findById(id);
        if (skill.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(skill.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid skill id!");
    }

    @PostMapping()
    public ResponseEntity<Object> save(@Valid @RequestBody SkillDto skillDto) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(skillService.save(skillDto));
        } catch (EntityExistsException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @PutMapping("{id}")
    public @ResponseBody
    ResponseEntity<Object> update(@PathVariable Long id, @Valid @RequestBody SkillDto skillDto) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(skillService.update(skillDto));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            skillService.deleteById(id);
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
