package project.csirac.models.emulator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

/**
 * Created by Dy.Zhao on 2016/1/4 0004.
 */
public class Monitor implements IMonitor
{
    private Vector<IMonitorObserver> _observerList = new Vector<>();

    public Monitor()
    {
    }

    @Override
    public void attachControlUnit(IControlUnit controlUnit)
    {

    }

    @Override
    public void attachDebugger(IDebugger debugger)
    {

    }

    @Override
    public void attachObserver(IMonitorObserver observer)
    {
        if (!this.isObserverAttached(observer))
        {
            this._observerList.add(observer);
        }
    }

    @Override
    public void continueExecuting(String sessionId)
    {

    }

    @Override
    public String[] getOutput(String sessionId)
    {
        String[] result = {};
        return generateFakeListResult(20).toArray(result);
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
    public String[] peekSupportedInstructions()
    {
        String[] result = {};
        return generateFakeListResult(20).toArray(result);
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
        //update code
        for (IMonitorObserver observer : this._observerList)
        {
            observer.updateInstructionView(sessionId, this.getCurrentInstruction(sessionId));
        }
    }

    @Override
    public void updateMemoryView(String sessionId, int address, String data)
    {
        //update code

        for (IMonitorObserver observer : this._observerList)
        {
            observer.updateMemoryView(sessionId, this.getMemory(sessionId));
        }
    }

    @Override
    public void updateRegisterView(String sessionId, int address, String data)
    {
        //update code
        for (IMonitorObserver observer : this._observerList)
        {
            observer.updateRegisterView(sessionId, this.getRegister(sessionId));
        }
    }

    @Override
    public String[] getMemory(String sessionId)
    {
        String[] result = {};
        return generateFakeListResult(20).toArray(result);
    }

    @Override
    public String[] getRegister(String sessionId)
    {
        String[] result = {};
        return generateFakeListResult(20).toArray(result);
    }

    @Override
    public String getCurrentInstruction(String sessionId)
    {
        return generateFakeResult(20);
    }

    @Override
    public boolean isObserverAttached(IMonitorObserver observer)
    {
        return this._observerList.contains(observer);
    }

    private String generateFakeResult(int length)
    {

        String result = "";
        for (int j = 0; j < length; j++)
        {
            int rand = (int) (Math.random() * 10);
            result += rand % 2;
        }
        return result;
    }

    private List<String> generateFakeListResult(int lengthPerItem)
    {
        List<String> result = new ArrayList<>();
        int length = (int) (Math.random() * 20 + 20);
        for (int i = 0; i < length; i++)
        {
            result.add(generateFakeResult(lengthPerItem));
        }
        return result;
    }
}
