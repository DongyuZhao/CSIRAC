package project.emulator.framework.api.debugger;

/**
 * Created by Dy.Zhao on 2016/1/24 0024.
 */
public interface IDebuggerObserver {
    void onPcRegisterUpdate(int message, DebuggerMessageSender messageSender);


    void onOpCodeUpdate(int message, DebuggerMessageSender messageSender);


    void onRegisterUpdate(RegisterMessage message, DebuggerMessageSender messageSender);


    void onDataMemoryUpdate(MemoryMessage message, DebuggerMessageSender messageSender);


    void onProgramMemoryUpdate(MemoryMessage message, DebuggerMessageSender messageSender);


    void onFrequencyChange(float message, DebuggerMessageSender messageSender);


    void attachDebugger(IDebugger debugger);
}
