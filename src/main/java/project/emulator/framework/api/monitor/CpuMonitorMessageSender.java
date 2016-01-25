package project.emulator.framework.api.monitor;

import project.emulator.framework.api.debugger.DebuggerMessageSender;

/**
 * Created by Dy.Zhao on 2016/1/23 0023.
 */
public abstract class CpuMonitorMessageSender extends DebuggerMessageSender
{
    protected IMonitor _monitor;

    public CpuMonitorMessageSender(String _id)
    {
        super(_id);
    }

    public void attachMonitor(IMonitor monitor)
    {
        this._monitor = monitor;
    }

    protected void onStart(CpuMonitorMessageSender messageSender)
    {
        if (this._monitor != null)
        {
            this._monitor.onStart(messageSender);
        }
    }

    protected void onPause(CpuMonitorMessageSender messageSender)
    {
        if (this._monitor != null)
        {
            this._monitor.onPause(messageSender);
        }
    }

    protected void onContinue(CpuMonitorMessageSender messageSender)
    {
        if (this._monitor != null)
        {
            this._monitor.onContinue(messageSender);
        }
    }

    protected void onStop(CpuMonitorMessageSender messageSender)
    {
        if (this._monitor != null)
        {
            this._monitor.onStop(messageSender);
        }
    }

    protected void onOutput(String[] data, CpuMonitorMessageSender messageSender)
    {
        if (this._monitor != null)
        {
            this._monitor.onOutput(data, messageSender);
        }
    }
}
