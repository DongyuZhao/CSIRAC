package project.csirac.models.emulator;

/**
 * Created by Dy.Zhao on 2016/1/19 0019.
 */
public interface IRegister
{
    /**
     * Attach the debugger to the memory
     *
     * @param debugger
     *         the debugger
     */
    void attachDebugger(IDebugger debugger);

    /**
     * Save the data to certain address of memory of the session
     *
     * @param sessionId
     *         the session Id
     * @param address
     *         the address
     * @param data
     *         the data
     *
     * @return if the save is successful
     */
    boolean saveData(String sessionId, String address, String data);


    /**
     * Load the data from certain address of memory from the session
     *
     * @param sessionId
     *         the session id
     * @param address
     *         the address
     *
     * @return the data
     */
    String loadData(String sessionId, String address);

    /**
     * Create a new session
     *
     * @param sessionId
     *         the session id
     */
    void newSession(String sessionId);

    /**
     * Remove the session
     *
     * @param sessionId
     *         the session id
     */
    void removeSession(String sessionId);
}
