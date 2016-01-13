package project.csirac.models.emulator;

/**
 * Created by Dy.Zhao on 2016/1/14 0014.
 */
public interface IStatusObserver
{

    /**
     * Push the ready info of the session to observer
     *
     * @param sessionId the session id
     */
    void pushReady(String sessionId);

    /**
     * Push running status of the session to observer
     *
     * @param sessionId the session id
     */
    void pushRunning(String sessionId);

    /**
     * Push pause status of the session to observer
     *
     * @param sessionId the session id
     */
    void pushPause(String sessionId);

    /**
     * Push stop status of the session to observer
     *
     * @param sessionId the session id
     */
    void pushStop(String sessionId);

}
