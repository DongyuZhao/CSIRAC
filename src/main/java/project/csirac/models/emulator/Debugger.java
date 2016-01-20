package project.csirac.models.emulator;

import java.util.Map;

/**
 * Created by Dy.Zhao on 2016/1/14 0014.
 */
public class Debugger implements IDebugger
{
    /**
     * Attach the monitor to the debugger
     *
     * @param monitor
     *         the monitor
     */
    @Override
    public void attachMonitor(IMonitor monitor)
    {

    }

    /**
     * Start executing the program assigned to the session
     *
     * @param sessionId
     *         the session id
     */
    @Override
    public void startExecuting(String sessionId)
    {

    }

    /**
     * Pause the executing of the program
     *
     * @param sessionId
     *         the session id of the program will be pause executing
     */
    @Override
    public void pauseExecuting(String sessionId)
    {

    }

    /**
     * Executing the next instruction of the program
     *
     * @param sessionId
     *         the session id of the program will be continue executing
     */
    @Override
    public void nextInstruction(String sessionId)
    {

    }

    /**
     * Continue Executing the program
     *
     * @param sessionId
     *         the session id of the program will be continue executing
     */
    @Override
    public void continueExecuting(String sessionId)
    {

    }

    /**
     * Stop executing the program assigned to the session
     *
     * @param sessionId
     *         the session id
     */
    @Override
    public void stopExecuting(String sessionId)
    {

    }

    /**
     * Update the current instruction of the session
     *
     * @param sessionId
     *         the session id
     * @param instruction
     */
    @Override
    public void updateInstructionView(String sessionId, String instruction)
    {

    }

    /**
     * Update the memory at specified address of the session
     *
     * @param sessionId
     *         the session id
     * @param address
     *         the address
     * @param data
     */
    @Override
    public void updateMemoryView(String sessionId, int address, String data)
    {

    }

    /**
     * Update the memory of the session
     *
     * @param sessionId
     *         the session id
     *         the address
     * @param data
     */
    @Override
    public void updateMemoryView(String sessionId, String[] data)
    {

    }

    /**
     * Update the register at specified address of the session
     *
     * @param sessionId
     *         the session id
     * @param address
     *         the address
     * @param data
     */
    @Override
    public void updateRegisterView(String sessionId, String address, String data)
    {

    }

    /**
     * Update the register of the session
     *
     * @param sessionId
     *         the session id
     * @param data
     */
    @Override
    public void updateRegisterView(String sessionId, Map<String, String> data)
    {

    }

    /**
     * Update the pc reg of the session
     *
     * @param sessionId
     *         the session id
     * @param pcReg
     */
    @Override
    public void updatePcRegView(String sessionId, int pcReg)
    {

    }

    /**
     * Update current setting views
     *
     * @param sessionId
     *         the session id
     * @param settings
     */
    @Override
    public void updateCurrentSettingView(String sessionId, Settings settings)
    {

    }
}
