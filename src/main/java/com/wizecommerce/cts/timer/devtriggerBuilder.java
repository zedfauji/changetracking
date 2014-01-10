package com.wizecommerce.cts.timer;

import com.wizecommerce.cts.utils.Hibernate;
import com.wizecommerce.cts.utils.JobEntry;
import org.quartz.SimpleTrigger;

import java.util.logging.Logger;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Created with IntelliJ IDEA.
 * User: gdudhwal
 * Date: 19/12/13
 * Time: 3:14 PM
 * To change this template use File | Settings | File Templates.
 */
class devtriggerBuilder {

    //This class will basically intialize trigger based upon repeatForever status
    public static Logger logger = Logger.getLogger("devtriggerBuilder");

    public static long jobId;
    public static int startTime;
    public static int jobInterval;
    public static int isRepeat;

    public JobEntry jobEntry;

        devtriggerBuilder(JobEntry jobEntry){

            this.jobEntry = jobEntry;

            Hibernate hibernate = new Hibernate();
            //Iterator<?> triggerinfoIterator = hibernate.executeSelectQuery("SELECT jobName,jobInterval,startDate,isRepeat FROM JobEntry WHERE jobId ='" + jobId + "'");

            startTime = jobEntry.getStartDate();
            jobInterval= jobEntry.getJobInterval();
            isRepeat = jobEntry.getIsRepeat();
        }

    public static SimpleTrigger getTrigger() {
        logger.info("Now creating trigger");
        if (isRepeat > 0)
        {
            logger.info("Job is repetitive , so creating a repeat enabled trigger");
            SimpleTrigger trigger = (SimpleTrigger) newTrigger()
                    .withIdentity("trigger1")
                    //.withSchedule(simpleSchedule().repeatForever().withIntervalInSeconds(jobInterval)).startAt(startTime).build();
                    .withSchedule(simpleSchedule().repeatForever().withIntervalInSeconds(jobInterval)).startNow().build();


            return trigger;
        }
        else
        {
            logger.info("Job isn't repeatitive , so create non repeatitive trigger");
            SimpleTrigger trigger = (SimpleTrigger) newTrigger()
                    .withIdentity("trigger1")
                    //.withSchedule(simpleSchedule().withIntervalInSeconds(jobInterval)).startAt(startTime).build();
                    .withSchedule(simpleSchedule().withIntervalInSeconds(jobInterval)).startNow().build();
            return trigger;
        }



    }

}
