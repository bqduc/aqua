/**
 * 
 */
package net.paramount.async;

import javax.inject.Inject;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import net.paramount.faces.DashboardServiceManager;

/**
 * @author ducbq
 *
 */
/*@Component
public class SynchronizeDataJob implements Job {
	@Inject 
	private DashboardServiceManager dashboardServiceManager;

	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		dashboardServiceManager.syncDashboardData();
	}

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		System.out.println(arg0.getFireTime() + "|<=>|" + arg0.getNextFireTime());
		//dashboardServiceManager.syncDashboardData();
	}
}
*/