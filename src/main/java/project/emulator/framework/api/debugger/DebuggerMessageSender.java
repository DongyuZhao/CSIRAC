package project.emulator.framework.api.debugger;

import project.emulator.framework.memory.IMemory;

/**
 * Created by Dy.Zhao on 2016/1/23 0023.
 */
public abstract class DebuggerMessageSender
{
    protected String _id;

    protected IDebugger _debugger;

    public DebuggerMessageSender(String _id)
    {
        this._id = _id;
    }

    public String getMessageSenderId()
    {
        return this._id;
    }

    public void attachDebugger(IDebugger debugger)
    {
        this._debugger = debugger;
    }

    protected void onPcRegisterUpdate(int message, DebuggerMessageSender messageSender)
    {
        if (this._debugger != null)
        {
            this._debugger.onPcRegisterUpdate(message, messageSender);
        }
    }

    protected void onOpCodeUpdate(int message, DebuggerMessageSender messageSender)
    {
        if (this._debugger != null)
        {
            this._debugger.onOpCodeUpdate(message, messageSender);
        }
    }

    protected void onRegisterUpdate(RegisterMessage message, DebuggerMessageSender messageSender)
    {
        if (this._debugger != null)
        {
            this._debugger.onRegisterUpdate(message, messageSender);
        }
    }

    protected void onMemoryUpdate(MemoryMessage message, DebuggerMessageSender messageSender)
    {

        if (this._debugger != null)
        {
            if (((IMemory) messageSender).isInstructionMemory())
            {
                this._debugger.onProgramMemoryUpdate(message, messageSender);
            }
            else
            {
                this._debugger.onDataMemoryUpdate(message, messageSender);
            }
        }
    }

    protected void onFrequencyChange(float message, DebuggerMessageSender messageSender)
    {
        if (this._debugger != null)
        {
            this._debugger.onFrequencyChange(message, messageSender);
        }
    }
}
