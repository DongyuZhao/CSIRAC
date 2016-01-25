package project.emulator.framework.api.monitor;

import project.emulator.framework.EmulatorInstance;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * Created by Dy.Zhao on 2016/1/23 0023.
 */
public class Monitor implements IMonitor
{
    private Vector<IMonitorObserver> _observerList = new Vector<>();

    private Map<String, EmulatorInstance> _emulatorInstances = new HashMap<>();

    @Override
    public void onDataLoaded(MemoryMonitorMessageSender messageSender)
    {
        for (IMonitorObserver observer : this._observerList)
        {
            observer.onDataLoaded(messageSender);
        }
    }

    @Override
    public void onProgramLoaded(MemoryMonitorMessageSender messageSender)
    {
        for (IMonitorObserver observer : this._observerList)
        {
            observer.onProgramLoaded(messageSender);
        }
    }

    @Override
    public void onStart(CpuMonitorMessageSender messageSender)
    {
        for (IMonitorObserver observer : this._observerList)
        {
            observer.onStart(messageSender);
        }
    }

    @Override
    public void onPause(CpuMonitorMessageSender messageSender)
    {
        for (IMonitorObserver observer : this._observerList)
        {
            observer.onPause(messageSender);
        }
    }

    @Override
    public void onContinue(CpuMonitorMessageSender messageSender)
    {
        for (IMonitorObserver observer : this._observerList)
        {
            observer.onContinue(messageSender);
        }
    }

    @Override
    public void onStop(CpuMonitorMessageSender messageSender)
    {
        for (IMonitorObserver observer : this._observerList)
        {
            observer.onStop(messageSender);
        }
    }

    @Override
    public void onOutput(String[] output, CpuMonitorMessageSender messageSender)
    {
        for (IMonitorObserver observer : this._observerList)
        {
            observer.onOutput(output, messageSender);
        }
    }

    @Override
    public void loadData(String id, String[] data)
    {
        this._emulatorInstances.get(id).loadData(data);
    }

    @Override
    public void loadProgram(String id, String[] program)
    {
        this._emulatorInstances.get(id).loadProgram(program);
    }

    @Override
    public void start(String id)
    {
        this._emulatorInstances.get(id).start();
    }

    @Override
    public void pause(String id)
    {
        try
        {
            if (this.sessionExists(id))
            {
                this._emulatorInstances.get(id).pause();
            }
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void next(String id)
    {
        try
        {
            if (this.sessionExists(id))
            {
                this._emulatorInstances.get(id).next();
            }
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void go(String id)
    {
        try
        {
            if (this.sessionExists(id))
            {
                this._emulatorInstances.get(id).go();
            }
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void stop(String id)
    {
        try
        {
            if (this.sessionExists(id))
            {
                this._emulatorInstances.get(id).stop();
            }
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void attachObserver(IMonitorObserver observer)
    {
        if (!this.isObserverAttached(observer))
        {
            observer.attachMonitor(this);
            this._observerList.add(observer);
        }
    }

    @Override
    public boolean isObserverAttached(IMonitorObserver observer)
    {
        return this._observerList.contains(observer);
    }

    @Override
    public void addNewSession(String sessionId, EmulatorInstance emulatorInstance)
    {
        if (!this.sessionExists(sessionId))
        {
            this._emulatorInstances.put(sessionId, emulatorInstance);
        }
    }

    @Override
    public boolean sessionExists(String sessionId)
    {
        return this._emulatorInstances.keySet().contains(sessionId);
    }

    @Override
    public void removeSession(String sessionId)
    {
        if (this.sessionExists(sessionId))
        {
            this._emulatorInstances.remove(sessionId);
        }
    }

    @Override
    public boolean isReady(String sessionId)
    {
        return this.sessionExists(sessionId) && this._emulatorInstances.get(sessionId).isReady();
    }
}
