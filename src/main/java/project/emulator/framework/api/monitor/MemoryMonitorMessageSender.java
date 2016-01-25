package project.emulator.framework.api.monitor;

import project.emulator.framework.api.debugger.DebuggerMessageSender;
import project.emulator.framework.memory.IMemory;

/**
 * Created by Dy.Zhao on 2016/1/24 0024.
 */
public class MemoryMonitorMessageSender extends DebuggerMessageSender
{
    protected IMonitor _monitor;

    public MemoryMonitorMessageSender(String _id)
    {
        super(_id);
    }

    public void attachMonitor(IMonitor monitor)
    {
        this._monitor = monitor;
    }

    protected void onMemoryLoaded(MemoryMonitorMessageSender messageSender)
    {
        if (this._monitor != null)
        {
            if (((IMemory) messageSender).isInstructionMemory())
            {
                this._monitor.onProgramLoaded(messageSender);
            }
            else
            {
                this._monitor.onDataLoaded(messageSender);
            }
        }
    }
}
