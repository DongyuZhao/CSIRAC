package project.emulator.framework.api.debugger;

import project.emulator.framework.EmulatorInstance;
import project.emulator.framework.cpu.controlunit.IControlUnit;

/**
 * Created by Dy.Zhao on 2016/1/21 0021.
 */
public interface IDebugger
{
    void onPcRegisterUpdate(int message, DebuggerMessageSender messageSender);


    void onOpCodeUpdate(int message,DebuggerMessageSender messageSender);


    void onRegisterUpdate(RegisterMessage message, DebuggerMessageSender messageSender);


    void onDataMemoryUpdate(MemoryMessage message, DebuggerMessageSender messageSender);


    void onProgramMemoryUpdate(MemoryMessage message, DebuggerMessageSender messageSender);


    void onFrequencyChange(float message, DebuggerMessageSender messageSender);


    void setClock(String id, float frequency);


    void attachObserver(IDebuggerObserver observer);


    boolean isObserverAttached(IDebuggerObserver observer);


    void addNewSession(String sessionId, EmulatorInstance emulatorInstance);


    boolean sessionExists(String sessionId);


    void removeSession(String sessionId);


    void setPcRegister(String sessionId, int address);
}
