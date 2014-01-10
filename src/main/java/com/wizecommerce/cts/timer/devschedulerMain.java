package com.wizecommerce.cts.timer;

import com.wizecommerce.cts.utils.Hibernate;
import com.wizecommerce.cts.utils.JobEntry;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Iterator;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: gdudhwal
 * Date: 06/01/14
 * Time: 12:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class devschedulerMain {

    public  int jobId = 0;

    devschedulerMain(int jobId){
        this.jobId = jobId;
    }

    // This class is the main class which gets initiated and calls main function

    public static void main(String args[]) throws SchedulerException {
        Logger logger = Logger.getLogger("devSchedulerMain");


        Scheduler scheduler = new StdSchedulerFactory().getScheduler();

        Hibernate hibernate = new Hibernate();
        Iterator<?> schedulerInfo = hibernate.executeSelectQuery("FROM JobEntry");
        while (schedulerInfo.hasNext())
        {
            JobEntry entry = (JobEntry) schedulerInfo.next();

            JobDetail job = new devjobBuilder(entry).getJob();
            logger.info("Job has been created for now  & now creating Trigger to fire the job ");

            //SimpleTrigger trigger;
            SimpleTrigger trigger = new devtriggerBuilder(entry).getTrigger();
            //trigger = getTrigger();

            logger.info("Trigger has been created now. ");
            logger.info("Starting job now ");
            logger.info("Adding Job listener now ");
            //scheduler.getListenerManager().addJobListener(new devjobListener(jobId));
            //scheduler.getListenerManager().addTriggerListener(new triggerListener());

            scheduler.getListenerManager().addJobListener(new devjobListener(entry));
            scheduler.scheduleJob(job,trigger);
            scheduler.start();

        }

    }

}
