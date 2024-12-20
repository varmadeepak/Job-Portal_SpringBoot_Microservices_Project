package com.project.job_microservice.job;

import com.project.job_microservice.job.dto.JobDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/jobs")
public class JobController {
    private JobService jobService;

    JobController(JobService service){
        this.jobService = service;
    }

    @GetMapping
    public ResponseEntity<List<JobDTO>> getJobs() {
        return new ResponseEntity<>(jobService.findAllJobs(),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createJob(@RequestBody Job job) {
        jobService.createJob(job);
        return new ResponseEntity<>("Job Saved Successfully",HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobDTO> getJob(@PathVariable Long id){
        JobDTO job = jobService.findJob(id);
        if(job != null)
            return new ResponseEntity<>(job, HttpStatus.OK);
        return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJob(@PathVariable Long id){
        boolean deleted = jobService.deleteJob(id);
        if(deleted)
            return ResponseEntity.ok("Job Deleted Sucessfully");
        return new ResponseEntity<>("Job Not found",HttpStatus.NOT_FOUND);

    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateJob(@RequestBody Job job,@PathVariable Long id){
        boolean updated = jobService.updateJob(job,id);
        if(updated)
            return new ResponseEntity<>("Job Updated Sucessfully",HttpStatus.OK);
        return new ResponseEntity<>("Job Not found",HttpStatus.NOT_FOUND);
    }
}
