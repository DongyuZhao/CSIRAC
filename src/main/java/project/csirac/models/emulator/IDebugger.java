package project.csirac.models.emulator;

import java.util.Map;

/**
 * Created by Dy.Zhao on 2016/1/4 0004.
 */
public interface IDebugger
{

    /**
     * Attach the monitor to the debugger
     *
     * @param monitor the monitor
     */
    void attachMonitor(IMonitor monitor);

    /**
     * Start executing the program assigned to the session
     *
     * @param sessionId
     *         the session id
     */
    void startExecuting(String sessionId);

    /**
     * Pause the executing of the program
     *
     * @param sessionId
     *         the session id of the program will be pause executing
     */
    void pauseExecuting(String sessionId);

    /**
     * Executing the next instruction of the program
     *
     * @param sessionId
     *         the session id of the program will be continue executing
     */
    void nextInstruction(String sessionId);

    /**
     * Continue Executing the program
     *
     * @param sessionId
     *         the session id of the program will be continue executing
     */
    void continueExecuting(String sessionId);

    /**
     * Stop executing the program assigned to the session
     *
     * @param sessionId
     *         the session id
     */
    void stopExecuting(String sessionId);

    /**
     * Update the current instruction of the session
     *
     * @param sessionId
     *         the session id
     * @param instruction
     *         current instruction
     */
    void updateInstructionView(String sessionId, String instruction);

    /**
     * Update the memory at specified address of the session
     *
     * @param sessionId
     *         the session id
     * @param address
     *         the address
     * @param data
     *         new data
     */
    void updateMemoryView(String sessionId, int address, String data);

    /**
     * Update the memory of the session
     *
     * @param sessionId
     *         the session id
     *         the address
     * @param data
     *         new data
     */
    void updateMemoryView(String sessionId, String[] data);

    /**
     * Update the register at specified address of the session
     *
     * @param sessionId
     *         the session id
     * @param address
     *         the address
     * @param data
     *         new data
     */
    void updateRegisterView(String sessionId, String address, String data);

    /**
     * Update the register of the session
     *
     * @param sessionId
     *         the session id
     * @param data
     *         new data
     */
    void updateRegisterView(String sessionId, Map<String,String> data);

    /**
     * Update the pc reg of the session
     *
     * @param sessionId
     *         the session id
     * @param pcReg
     *         the pc reg
     */
    void updatePcRegView(String sessionId, int pcReg);

    /**
     * Update current setting views
     *
     * @param sessionId
     *         the session id
     * @param settings
     *         current settings
     */
    public void updateCurrentSettingView(String sessionId, Settings settings);

    /**
     * Get the output of the session
     *
     * @param sessionId
     *         the session id
     *
     * @return the output
     */
}
