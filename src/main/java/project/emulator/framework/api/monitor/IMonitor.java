package project.emulator.framework.api.monitor;


import project.emulator.framework.EmulatorInstance;

/**
 * Created by Dy.Zhao on 2016/1/21 0021.
 */
public interface IMonitor
{
    void onDataLoaded(MemoryMonitorMessageSender messageSender);

    void onProgramLoaded(MemoryMonitorMessageSender messageSender);

    void onStart(CpuMonitorMessageSender messageSender);

    void onPause(CpuMonitorMessageSender messageSender);

    void onContinue(CpuMonitorMessageSender messageSender);

    void onStop(CpuMonitorMessageSender messageSender);

    void onOutput(String[] output, CpuMonitorMessageSender messageSender);

    void loadData(String id, String[] data);

    void loadProgram(String id, String[] program);

    void start(String id);

    void pause(String id);

    void next(String id);

    void go(String id);

    void stop(String id);

    void attachObserver(IMonitorObserver observer);

    boolean isObserverAttached(IMonitorObserver observer);

    void addNewSession(String sessionId, EmulatorInstance emulatorInstance);

    boolean sessionExists(String sessionId);

    void removeSession(String sessionId);

    boolean isReady(String sessionId);

    boolean isRunning(String sessionId);

    boolean isPause(String sessionId);
}
