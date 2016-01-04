package project.csirac.models.emulator;

import java.util.List;

/**
 * Created by Dy.Zhao on 2016/1/4 0004.
 */
public class Monitor implements IMonitor
{
    @Override
    public void attachControlUnit(IControlUnit controlUnit)
    {

    }

    @Override
    public void attachDebugger(IDebugger debugger)
    {

    }

    @Override
    public void continueExecuting(String sessionId)
    {

    }

    @Override
    public List<String> getOutput(String sessionId)
    {
        return null;
    }

    @Override
    public void Init(IDecoder decoder, IMemory memory, IComputeUnit computeUnit)
    {

    }

    @Override
    public void loadProgramToMemory(String sessionId, String[] program)
    {

    }

    @Override
    public void nextInstruction(String sessionId)
    {

    }

    @Override
    public void pauseExecuting(String sessionId)
    {

    }

    @Override
    public int peekPcRegister(String sessionId)
    {
        return 0;
    }

    @Override
    public List<String> peekSupportedInstructions()
    {
        return null;
    }

    @Override
    public void setClockFrequency(String sessionId, float frequency)
    {

    }

    @Override
    public void startExecuting(String sessionId)
    {

    }

    @Override
    public void stopExecuting(String sessionId)
    {

    }

    @Override
    public void updateInstructionView(String sessionId, String instruction)
    {

    }

    @Override
    public void updateMemoryView(String sessionId, int address, String data)
    {

    }

    @Override
    public void updateRgisterView(String sessionId, int address, String data)
    {

    }

    @Override
    public List<String> getMemory(String sessionId)
    {
        return null;
    }

    @Override
    public List<String> getRegister(String sessionId)
    {
        return null;
    }

    @Override
    public String getCurrentInstruction(String sessionId)
    {
        return null;
    }
}
