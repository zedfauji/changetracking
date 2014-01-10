package com.wizecommerce.cts.timer;

import com.wizecommerce.cts.utils.JobEntry;
import com.wizecommerce.cts.zeus.Scrubber;
import com.wizecommerce.cts.zeus.Writer;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;

import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: gdudhwal
 * Date: 06/01/14
 * Time: 12:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class devjobBuilder {
    //this class will create jobs
    public static Logger logger = Logger.getLogger("devjobBuilder");
    public JobEntry jobEntry;

    public devjobBuilder(JobEntry jobEntry){
        this.jobEntry = jobEntry;
        logger.info("Initialized jobName = " + jobEntry.getJobName() + "classFactory = " + jobEntry.getClassFactory() + "params = " + jobEntry.getJobParams() + "with job id " + jobEntry.getJobId());
    }

    public JobDetail getJob() {
        //This method will actually generates and return job object and map job id with parameters
        JobDetail job = null;
        logger.info("Defining job with params 1. job name = " + jobEntry.getJobName() + "with job id "+ jobEntry.getJobId() + "with params " + jobEntry.getJobParams());

        if(this.jobEntry.getClassFactory().matches("(.*)Scrubber(.*)"))
        {
            //job = JobBuilder.newJob((Class<? extends Job>) Class.forName(classFactory)).withIdentity(String.valueOf(jobId),String.valueOf(jobName))
            job = JobBuilder.newJob(Scrubber.class).withIdentity(String.valueOf(jobEntry.getJobId()))
                    //.usingJobData(jobId,paRams)
                    .usingJobData("jobParams",jobEntry.getJobParams())
                    .build();
        }
        else
        {
            job = JobBuilder.newJob(Writer.class).withIdentity(String.valueOf(jobEntry.getJobId()))
                    .usingJobData(String.valueOf(jobEntry.getJobId()), jobEntry.getJobParams())
                    .build();
        }

        return job;
    }
}
