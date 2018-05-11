package com.peng.quartz.simpleScheduler.demo3.startAtEndAt;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HelloScheduler {
    public static void main(String[] args) throws SchedulerException {
        //创建一个JobDetail实例，将该实例与HelloJob class绑定
        JobDetail jobDetail = JobBuilder.newJob(HelloJob.class).withIdentity("myJob", "group1")
                .usingJobData("message","hello JobDetail").usingJobData("floatValue",3.14F).build();

        System.out.println("jobDetail's name:"+jobDetail.getKey().getName());
        System.out.println("jobDetail's group:"+jobDetail.getKey().getGroup());
        System.out.println("jobDetail's JobClass:"+jobDetail.getJobClass().getName());

        //开始三秒后执行
        Date startDate = new Date();
        startDate.setTime(startDate.getTime()+3000);
        //开始六秒后停止执行
        Date endDate = new Date();
        endDate.setTime(endDate.getTime()+6000);
        //创建一个Trigger触发器实例，定义该Job立即执行，并且每个两分钟重复执行一次，执行三次
        //两个groups不是一个
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("mytrigger", "group1")
                .usingJobData("message","hello Trigger").usingJobData("doubuleValue",2.22D)
                //.startNow()
                .startAt(startDate)
                .endAt(endDate)
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(2).withRepeatCount(3)).build();
        //创建Schedule调度器实例
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        scheduler.start();
        //scheduler调度器绑定jobdetail和trigger 触发器
        scheduler.scheduleJob(jobDetail,trigger);
        //打印当前时间
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("Start exec time is : "+sdf.format(date));

    }
}
