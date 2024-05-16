# Spring-Batch-POC

## Introduction
This POC is related to the Spring Batch Implementation using Spring Boot 3+ version.
Spring batch is a very lightweight framework from Spring Framework family, and it’s designed and developed for scalable and robust batch applications.
Spring batch is a framework based on traditional approach of batch processing for the execution of jobs handling the huge volume of data.

## Advantages Of Spring Batch
* Used to process enormous volume of data in less time.
* Build robust and scalable application.
* Easy to integrate with other applications.

## Basic arcitecture of Spring batch
Spring Batch is based on traditional batch processing in which JobRepository is the important component used to interact with DB and save the data related to the execution status, context, etc. of all the batch processes.
The components include : 
### **1) Job :** 
It is the Basic component of spring batch. It includes different steps required to execute a task or job.
There are several terms in spring batch related to Job : 
* JobInstance - It is the unique identification of a job. It will be one for every job. It lifecycle ends with the termination of first successful execution of Job.
* JobParameters - These are the parameters required for a job to execute.
* JobExecution - It is the actual running instance of a job. A JobInstance can have multiple JobExecution. It track about the execution of job, and persists the state in the db with the help of JobRepository.

### **2) Steps : **
It is a unit which can run a small task in a Job. It can be 1 or more than that according to requirement. It is a sequential phase of the job execution. It consists of 
* One ItemReader - used to read the data from a particular source. It returns null if the input source is not found. It is th input of the batch.
* One ItemProcessor - used to do modifications according to the business in the data if required. After completion of the processor it sends the execution to the writer.
* One ItemWriter - used to write the modified (if done) data into another source. It is the output of the batch. 
  It has a **StepExecution** which is one for every step executed. It represents a single attempt to run a step.

### **3) Job Repository : **
It is used for interacting the application with db and store the metadata and states of the JobLauncher, Job and Step. Every component interacts with it for persistence and operations metadata. It starts with the launch of the Job. JobExecution is obtained from the JobRepository and during the run, the instances of StepExecution and JobExecution are persisted to the repository.
* Job Launcher - It is just a simple interface used to launch a job with the job parameters provided. 
The figure below shows the **Basic Architecture of the Spring Batch** : 

![image](https://github.com/AshayOfficial/Spring-batch/assets/116789599/403b112b-cdd5-45ac-bfae-6638e995bfa5)

