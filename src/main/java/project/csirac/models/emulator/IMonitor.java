package project.csirac.models.emulator;

/**
 * Created by Dy.Zhao on 2016/1/4 0004.
 */
public interface IMonitor
{
    /**
     * Attach the Control Unit this monitor will monitor on.
     *
     * @param controlUnit
     *         the Control Unit this monitor will monitor on
     */
    void attachControlUnit(IControlUnit controlUnit);

    /**
     * Attach the Debugger this monitor will monitor on.
     *
     * @param debugger
     *         the Control Unit this debugger will monitor on
     */
    void attachDebugger(IDebugger debugger);

    /**
     * Attach the observer which is observing this monitor.
     *
     * @param observer
     *         the observer which is observing this monitor
     */
    void attachViewObserver(IViewObserver observer);

    /**
     * Attach the observer which is observing this monitor.
     *
     * @param observer
     *         the observer which is observing this monitor
     */
    void attachStatusObserver(IStatusObserver observer);

    /**
     * Attach the observer which is observing this monitor.
     *
     * @param observer
     *         the observer which is observing this monitor
     */
    void attachSettingObserver(ISettingObserver observer);


    //void Init(IDecoder decoder, IMemory memory, IComputeUnit computeUnit);

    /**
     * Upload program to the emulator
     *
     * @param sessionId
     *         the session id of the program will the program be assign to
     * @param program
     *         the program
     */
    void loadProgram(String sessionId, String[] program);

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

//    /**
//     * Peek the pc register of the monitor
//     *
//     * @param sessionId
//     *         the session id of the pc register should be peeked
//     */
//    int peekPcRegister(String sessionId);

    /**
     * Peek the instruction this simulator supported
     */
    String[] peekSupportedInstructions();

    /**
     * Set the frequency for the session
     *
     * @param sessionId
     *         the session id
     * @param frequency
     *         new frequency
     */
    void setClockFrequency(String sessionId, float frequency);

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
    void updateRegisterView(String sessionId, int address, String data);

    /**
     * Update the register of the session
     *
     * @param sessionId
     *         the session id
     * @param data
     *         new data
     */
    void updateRegisterView(String sessionId, String[] data);

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
     * Update the output of the session
     *
     * @param sessionId
     *         the session id
     * @param output
     *         the output
     */
    void updateOutputView(String sessionId, String[] output);

    /**
     * Update current setting views
     *
     * @param sessionId
     *         the session id
     * @param settings
     *         current settings
     */
    public void updateCurrentSettingView(String sessionId, Settings settings);

//    /**
//     * Get the output of the session
//     *
//     * @param sessionId
//     *         the session id
//     *
//     * @return the output
//     */
//    String[] getOutput(String sessionId);

//    /**
//     * Get the memory of the session
//     *
//     * @param sessionId
//     *         the session id
//     *
//     * @return the memory
//     */
//    String[] getMemory(String sessionId);
//
//    /**
//     * Get the register of the session
//     *
//     * @param sessionId
//     *         the session id
//     *
//     * @return the register
//     */
//    String[] getRegister(String sessionId);
//
//    /**
//     * Get current instruction of the session
//     *
//     * @param sessionId
//     *         the session id
//     *
//     * @return current instruction
//     */
//    String getCurrentInstruction(String sessionId);

    /**
     * Check if the observer is attached
     *
     * @param observer
     *         the observer
     *
     * @return if it is attached
     */
    boolean isViewObserverAttached(IViewObserver observer);

    /**
     * Check if the observer is attached
     *
     * @param observer
     *         the observer
     *
     * @return if it is attached
     */
    boolean isStatusObserverAttached(IStatusObserver observer);

    /**
     * Check if the observer is attached
     *
     * @param observer
     *         the observer
     *
     * @return if it is attached
     */
    boolean isSettingObserverAttached(ISettingObserver observer);
}
