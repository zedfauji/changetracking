package com.wizecommerce.cts.timer;

import com.wizecommerce.cts.utils.Hibernate;
import com.wizecommerce.cts.utils.JobEntry;
import com.wizecommerce.cts.utils.JobLog;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: gdudhwal
 * Date: 08/01/14
 * Time: 4:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class devjobListener implements JobListener {

    Hibernate hibernate = new Hibernate();
    JobLog jobLog = new JobLog();

    public JobEntry jobEntry;
    devjobListener(JobEntry jobEntry) {
        this.jobEntry = jobEntry;

    }
    Logger logger = Logger.getLogger("devjobListener");
    @Override
    public String getName() {
        return "Job ID :" + jobEntry.getJobId() + "Has been started";  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        logger.info( "#####Job ID : ### " + jobEntry.getJobId() + " ###Has been started");
        JobDataMap jobDataMap = context.getMergedJobDataMap();

        jobLog.setJobId(jobEntry.getJobId());
        jobLog.setCurrentStatus("Started");
        //jobLog.setStartDate(0);
        jobLog.setLastRunDate(0);
        jobLog.setLastStatus("OK");

        logger.info(  "JobID :" + jobLog.getJobId() + "  Start time " + jobLog.getStartDate() + "  Current Status" + jobLog.getCurrentStatus() );
        logger.info("Last Status : " + jobLog.getLastStatus() + " Last Run time : " + jobLog.getLastRunDate());
        //To change body of implemented methods use File | Settings | File Templates.

        hibernate.executeInsertQuery(jobLog);
        //hibernate.terminateSession();
        logger.info("Job Execution logged in DB");
    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
        //To change body of implemented methods use File | Settings | File Templates.
        jobLog.setCurrentStatus("Cancelled");
        jobLog.setLastStatus("Not completed");

    }

    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException e) {
        //To change body of implemented methods use File | Settings | File Templates.
        jobLog.setCurrentStatus("Completed");
        jobLog.setLastStatus("Completed");
        hibernate.executeInsertQuery(jobLog);
        hibernate.terminateSession();
        logger.info("Job Execution logged in DB");

    }
}
