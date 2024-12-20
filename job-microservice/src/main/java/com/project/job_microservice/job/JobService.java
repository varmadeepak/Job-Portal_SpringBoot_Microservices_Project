package com.project.job_microservice.job;

import com.project.job_microservice.job.dto.JobDTO;

import java.util.List;

public interface JobService {

    List<JobDTO> findAllJobs();

    void createJob(Job job);

    JobDTO findJob(Long id);

    boolean deleteJob(Long id);

    boolean updateJob(Job job,Long id);
}
