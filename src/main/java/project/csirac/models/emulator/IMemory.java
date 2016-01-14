package project.csirac.models.emulator;

/**
 * Created by Dy.Zhao on 2016/1/4 0004.
 */
public interface IMemory
{

    /**
     * Attach the debugger to the memory
     *
     * @param debugger the debugger
     */
    void attachBug(IDebugger debugger);

    /**
     * Save the data to certain address of memory of the session
     *
     * @param sessionId the session Id
     * @param address the address
     * @param data the data
     */
    void saveData(String sessionId, int address, String data);


    /**
     * Load the data from certain address of memory from the session
     *
     * @param sessionId the session id
     * @param address the address
     *
     * @return the data
     */
    String loadData(String sessionId, int address);
}
