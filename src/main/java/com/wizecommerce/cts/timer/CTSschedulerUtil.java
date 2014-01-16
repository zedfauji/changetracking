package com.wizecommerce.cts.timer;

import com.wizecommerce.cts.utils.QuartzRuntimeException;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.impl.StdSchedulerFactory;
import org.w3c.dom.Element;

import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: gdudhwal
 * Date: 17/01/14
 * Time: 2:29 AM
 * To change this template use File | Settings | File Templates.
 */
public class CTSschedulerUtil {

    public Logger logger = Logger.getLogger("CTSschedulerUtil");

    private Scheduler scheduler;

    public CTSschedulerUtil(Element jobInfo,Element jobParams){

        try{
            scheduler = StdSchedulerFactory.getDefaultScheduler();
        logger.info("Recieved message to create a new job jobXML - " + jobInfo.getAttribute("job_name"));
        JobDetail job = new CTSJobBuilder(jobInfo, jobParams).getJob();
        logger.info("Job has been created for now  & now creating Trigger to fire the job ");

        //SimpleTrigger trigger;
        logger.info("Creating Trigger ");
        SimpleTrigger trigger = new CTSTriggerBuilder(jobInfo).getTrigger();

        logger.info("Adding Job listener now ");
        scheduler.getListenerManager().addJobListener(new CTSJobListener(jobInfo));
        scheduler.scheduleJob(job,trigger);


        }

        catch (Exception e) {
            throw new QuartzRuntimeException("Failed to create scheduler safely ",e );
        }
    }

    /**
    public CTSschedulerUtil(JobDetail job, SimpleTrigger trigger) {

        try{
            scheduler = StdSchedulerFactory.getDefaultScheduler();
            logger.info("Adding Job listener now ");
            scheduler.getListenerManager().addJobListener(new CTSJobListener(jobInfo));
            scheduler.scheduleJob(job,trigger);
        }

        catch (Exception e) {
            throw new QuartzRuntimeException("Failed to create scheduler safely ",e );
        }

    }  **/

    public CTSschedulerUtil(Scheduler scheduler){
        this.scheduler = scheduler;
    }

    // this will start the scheduler and wait for given time, wait for all jobs to complete and then shutdown scheduler after certain time.
    public void startAndShutdown(long waitTimeInMillis) {
        start();
        if (waitTimeInMillis > 0) {
            synchronized (this) {
                try {
                    this.wait(waitTimeInMillis);
                } catch (InterruptedException e) {
                    throw new QuartzRuntimeException("Failed to wait after scheduler started.", e);
                }
            }
        }
        // true => Wait for job to complete before shutdown.
        shutdown(true);
    }


    public void startAndWait() {
        startAndWait(0);
    }

    // This will delayed start the server and run as a server
    public void startAndWait(int startDelayInSeconds) {
        if (startDelayInSeconds <= 0) {
            start();
        } else {
            startDelayed(startDelayInSeconds);
        }
        synchronized (this) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                throw new QuartzRuntimeException("Failed to wait after scheduler started.", e);
            }
        }
    }

    public void start() {
        try {
            scheduler.start();
        } catch (SchedulerException e) {
            throw new QuartzRuntimeException(e);
        }
    }

    public void startDelayed(int seconds) {
        try {
            scheduler.startDelayed(seconds);
        } catch (SchedulerException e) {
            throw new QuartzRuntimeException(e);
        }
    }

    public void shutdown(boolean waitForJobToComplete) {
        try {
            scheduler.shutdown(waitForJobToComplete);
        } catch (SchedulerException e) {
            throw new QuartzRuntimeException(e);
        }
    }
}
