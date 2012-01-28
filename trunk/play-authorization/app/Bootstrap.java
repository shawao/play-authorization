import controllers.Application;
import play.jobs.Job;
import play.jobs.OnApplicationStart;

/**
 * Desc:
 * --------
 * User: zou bo
 * Date: 12-1-18 下午4:53
 */
@OnApplicationStart
public class Bootstrap extends Job{
    @Override
    public void doJob() throws Exception {
        play.Logger.info("...Bootstrap started");

        Application.initialize();
    }
}
