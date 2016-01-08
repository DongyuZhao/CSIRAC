package project.csirac.models.emulator;

import java.util.List;

/**
 * Created by Dy.Zhao on 2016/1/4 0004.
 */
public interface IMonitor
{
    void attachControlUnit(IControlUnit controlUnit);
    void attachDebugger(IDebugger debugger);
    void attachObserver(IMonitorObserver observer);
    void continueExecuting(String sessionId);
    void Init(IDecoder decoder, IMemory memory, IComputeUnit computeUnit);
    void loadProgramToMemory(String sessionId, String[] program);
    void nextInstruction(String sessionId);
    void pauseExecuting(String sessionId);
    int peekPcRegister(String sessionId);
    String[] peekSupportedInstructions();
    void setClockFrequency(String sessionId, float frequency);
    void startExecuting(String sessionId);
    void stopExecuting(String sessionId);
    void updateInstructionView(String sessionId, String instruction);
    void updateMemoryView(String sessionId, int address, String data);
    void updateRegisterView(String sessionId, int address, String data);
    String[] getOutput(String sessionId);
    String[] getMemory(String sessionId);
    String[] getRegister(String sessionId);
    String getCurrentInstruction(String sessionId);
}
