package com.example.inttaskbe.controller;

import com.example.inttaskbe.dto.CandidateDto;
import com.example.inttaskbe.dto.SkillDto;
import com.example.inttaskbe.entity.Candidate;
import com.example.inttaskbe.entity.Skill;
import com.example.inttaskbe.exception.EntityExistsException;
import com.example.inttaskbe.exception.InvalidEntityException;
import com.example.inttaskbe.mapper.SkillMapper;
import com.example.inttaskbe.service.CandidateService;
import com.example.inttaskbe.service.SkillService;
import org.mapstruct.factory.Mappers;
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
    private final SkillService skillService;

    private final SkillMapper skillMapper = Mappers.getMapper(SkillMapper.class);

    public CandidateController(CandidateService candidateService, SkillService skillService) {
        this.candidateService = candidateService;
        this.skillService = skillService;
    }

    @GetMapping
    public List<CandidateDto> findAll() {
        return candidateService.findAll();
    }

    @GetMapping("filter")
    public ResponseEntity<Page<CandidateDto>> findAll(@RequestParam(defaultValue = "0") Integer pageNo,
                                                  @RequestParam(defaultValue = "5") Integer pageSize, @RequestParam(defaultValue = "fullName") String sortBy,
                                                  @RequestParam(defaultValue = "asc") String sortOrder, @RequestParam(required = false, defaultValue = "") String nameFilter) {
        return new ResponseEntity<Page<CandidateDto>>(candidateService.findAll(pageNo, pageSize, sortBy, sortOrder, nameFilter), new HttpHeaders(),
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
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (InvalidEntityException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    // skill operations
    @PutMapping("{id}/skill/{skillId}")
    public @ResponseBody
    ResponseEntity<Object> addSkill(@PathVariable Long id, @PathVariable Long skillId) {
        Optional<CandidateDto> candidate = candidateService.findById(id);
        Optional<SkillDto> skill = skillService.findById(skillId);

        // if there are no such candidate or skill
        if(!(candidate.isPresent() && skill.isPresent())) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Non existing candidate or skill.");

        candidate.get().getSkills().add(skillMapper.toEntity(skill.get()));
        try {
            return ResponseEntity.status(HttpStatus.OK).body(candidateService.update(candidate.get()));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @DeleteMapping("{id}/skill/{skillId}")
    public @ResponseBody
    ResponseEntity<Object> removeSkill(@PathVariable Long id, @PathVariable Long skillId) {
        Optional<CandidateDto> candidate = candidateService.findById(id);
        Optional<SkillDto> skill = skillService.findById(skillId);

        // if there are no such candidate or skill
        if(!(candidate.isPresent() && skill.isPresent())) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Non existing candidate or skill.");

        if(candidate.get().getSkills().remove(skillMapper.toEntity(skill.get()))) return ResponseEntity.status(HttpStatus.OK).body(candidateService.update(candidate.get()));
        else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Somethings wrong.");
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

    @GetMapping("testing")
    public ResponseEntity<Page<CandidateDto>> findAllWithSkill(@RequestParam(defaultValue = "0") Integer pageNo,
                                                      @RequestParam(defaultValue = "5") Integer pageSize, @RequestParam(defaultValue = "fullName") String sortBy,
                                                      @RequestParam(defaultValue = "asc") String sortOrder, @RequestParam(required = false, defaultValue = "") String skillFilter) {
        return new ResponseEntity<Page<CandidateDto>>(candidateService.findAllBySkill(pageNo, pageSize, sortBy, sortOrder, skillFilter), new HttpHeaders(),
                HttpStatus.OK);
    }
}
