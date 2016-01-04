package project.csirac.models.emulator;

import java.util.List;

/**
 * Created by Dy.Zhao on 2016/1/4 0004.
 */
public interface IMonitor
{
    void attachControlUnit(IControlUnit controlUnit);
    void attachDebugger(IDebugger debugger);
    void continueExecuting(String sessionId);
    List<String> getOutput(String sessionId);
    void Init(IDecoder decoder, IMemory memory, IComputeUnit computeUnit);
    void loadProgramToMemory(String sessionId, String[] program);
    void nextInstruction(String sessionId);
    void pauseExecuting(String sessionId);
    int peekPcRegister(String sessionId);
    List<String> peekSupportedInstructions();
    void setClockFrequency(String sessionId, float frequency);
    void startExecuting(String sessionId);
    void stopExecuting(String sessionId);
    void updateInstructionView(String sessionId, String instruction);
    void updateMemoryView(String sessionId, int address, String data);
    void updateRgisterView(String sessionId, int address, String data);
    List<String> getMemory(String sessionId);
    List<String> getRegister(String sessionId);
    String getCurrentInstruction(String sessionId);
}
