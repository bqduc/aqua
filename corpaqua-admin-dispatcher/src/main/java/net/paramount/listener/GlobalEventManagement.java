/**
 * 
 */
package net.paramount.listener;

import javax.inject.Inject;

import org.quartz.CronScheduleBuilder;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import net.paramount.dmx.postconstruct.GlobalDataServiceHelper;
import net.paramount.dmx.repository.GlobaRepositoryManagement;
import net.paramount.framework.component.ComponentBase;
import net.paramount.framework.concurrent.ExecutorServiceHelper;

/**
 * @author ducbq
 *
 */
@Component
public class GlobalEventManagement extends ComponentBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -829018786197754366L;

	@Inject
	private Scheduler scheduler;
	
	@Inject
	private ExecutorServiceHelper executorServiceHelper;

	@Inject
	private GlobaRepositoryManagement globaRepositoryManagement;

	@Inject
	private GlobalDataServiceHelper globalDataServiceHelper;

	@EventListener(ApplicationReadyEvent.class)
	public void onApplicationReadyEventListener() {
		globaRepositoryManagement.initiateMasterData();
		try {
			onInit();
			//setupSchedulers();
		} catch (InterruptedException e) {
			log.error(e);
		}
		log.info("Leave onApplicationReadyEventListener");
	}

	protected void onInit() throws InterruptedException {
		globalDataServiceHelper.initiateGlobalData();
		//GlobalConstants.KEY_CONTEXT_CLASS
		/*ExecutionContext executionContext = ExecutionContext.builder()
				
				.build();
		executorServiceHelper.startThread(executionContext);*/
	}

	/*private void setupSchedulers() {
		JobDetail jobDetail = buildJobDetail();
		Date startedAt = DateTimeUtility.getSystemDateTime();
		startedAt = DateTimeUtility.add(startedAt, Calendar.MINUTE, 3);
		Trigger jobTrigger = buildJobTrigger(jobDetail, startedAt);
		try {
			scheduler.scheduleJob(jobDetail, fireAfterTwoMinFrom7_46To7_58());
		} catch (SchedulerException e) {
			log.error(e);
		}
	}*/
	

  /*private JobDetail buildJobDetail() {
    JobDataMap jobDataMap = new JobDataMap();

    jobDataMap.put("email", scheduleEmailRequest.getEmail());
    jobDataMap.put("subject", scheduleEmailRequest.getSubject());
    jobDataMap.put("body", scheduleEmailRequest.getBody());
    Class<? extends Job> jobClass = SynchronizeDataJob.class;
    JobDetail jobDetail = JobBuilder.newJob(jobClass)
            //.withIdentity(UUID.randomUUID().toString(), "email-jobs")
            //.withDescription("Send Email Job")
            //.usingJobData(jobDataMap)
            //.storeDurably()
            .build();

    return jobDetail;
  }

  private Trigger buildJobTrigger(JobDetail jobDetail, Date startAt) {
  	Trigger fetchedTrigger = TriggerBuilder.newTrigger()
            .forJob(jobDetail)
            //.withIdentity(jobDetail.getKey().getName(), "email-triggers")
            .withDescription("Send Email Trigger")
            //.startAt(startAt)
            //.withSchedule(CronScheduleBuilder.cronSchedule("0 0/5 * * * ?"))
            //.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(2))
            .build();
  	
  	return 	fetchedTrigger;
  }    
  */
  //Fire every 2 minutes starting at 7:46am and ending at 7:58am, every day
  public Trigger fireAfterTwoMinFrom7_46To7_58() {
      Trigger trigger = TriggerBuilder.newTrigger()
              .withIdentity("fireAfterTwoMinFrom7_46To7_58", GROUP_NAME)
              .withSchedule(cronSchedule("Fire only on certain days of month",
                      "0 0/5 * * * ?"))      
              .build();
      return trigger;
  }

  private static CronScheduleBuilder cronSchedule(String desc, String cronExpression) {
      System.out.println(desc + "->(" + cronExpression + ")");
      return CronScheduleBuilder.cronSchedule(cronExpression);
  }
  
  private String GROUP_NAME = "demo";
}