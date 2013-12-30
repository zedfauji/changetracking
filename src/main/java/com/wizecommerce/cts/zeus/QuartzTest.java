package com.wizecommerce.cts.zeus;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
 
public class QuartzTest 
{
    public static void main( String[] args ) throws SchedulerException
    {
        JobDetail job = JobBuilder
                .newJob(Scrubber.class)
                .withIdentity("SimpleJob")
                .build();
         
        Trigger trigger = TriggerBuilder
                .newTrigger()
                .withIdentity("SimpleJob")
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(300).repeatForever())
                .build();
         
        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
        scheduler.start();
        scheduler.scheduleJob(job,trigger);
    }
}