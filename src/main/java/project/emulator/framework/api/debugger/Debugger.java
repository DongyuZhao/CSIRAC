package project.emulator.framework.api.debugger;

import project.emulator.framework.EmulatorInstance;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * Created by Dy.Zhao on 2016/1/23 0023.
 */
public class Debugger implements IDebugger {
    private Vector<IDebuggerObserver> _observerList = new Vector<>();

    private Map<String, EmulatorInstance> _emulatorInstance = new HashMap<>();

    @Override
    public void onPcRegisterUpdate(int message, DebuggerMessageSender messageSender) {
        for (IDebuggerObserver observer : this._observerList) {
            observer.onPcRegisterUpdate(message, messageSender);
        }
    }

    @Override
    public void onOpCodeUpdate(int message, DebuggerMessageSender messageSender) {
        for (IDebuggerObserver observer : this._observerList) {
            observer.onOpCodeUpdate(message, messageSender);
        }
    }

    @Override
    public void onRegisterUpdate(RegisterMessage message, DebuggerMessageSender messageSender) {
        for (IDebuggerObserver observer : this._observerList) {
            observer.onRegisterUpdate(message, messageSender);
        }
    }

    @Override
    public void onDataMemoryUpdate(MemoryMessage message, DebuggerMessageSender messageSender) {
        for (IDebuggerObserver observer : this._observerList) {
            observer.onDataMemoryUpdate(message, messageSender);
        }
    }

    @Override
    public void onProgramMemoryUpdate(MemoryMessage message, DebuggerMessageSender messageSender) {
        for (IDebuggerObserver observer : this._observerList) {
            observer.onProgramMemoryUpdate(message, messageSender);
        }
    }

    @Override
    public void onFrequencyChange(float message, DebuggerMessageSender messageSender) {
        for (IDebuggerObserver observer : this._observerList) {
            observer.onFrequencyChange(message, messageSender);
        }
    }

    @Override
    public void setClock(String id, float frequency) {
        if (this.sessionExists(id)) {
            this._emulatorInstance.get(id).setClock(frequency);
        }
    }

    @Override
    public void attachObserver(IDebuggerObserver observer) {
        if (!this.isObserverAttached(observer)) {
            observer.attachDebugger(this);
            this._observerList.add(observer);
        }
    }

    @Override
    public boolean isObserverAttached(IDebuggerObserver observer) {
        return this._observerList.contains(observer);
    }

    @Override
    public void addNewSession(String sessionId, EmulatorInstance emulatorInstance) {
        if (!this.sessionExists(sessionId)) {
            this._emulatorInstance.put(sessionId, emulatorInstance);
        }
    }

    @Override
    public boolean sessionExists(String sessionId) {
        return this._emulatorInstance.containsKey(sessionId);
    }

    @Override
    public void removeSession(String sessionId) {
        if (this.sessionExists(sessionId)) {
            this._emulatorInstance.remove(sessionId);
        }
    }

    @Override
    public void setPcRegister(String sessionsId, int address) {
        if (this.sessionExists(sessionsId)) {
            this._emulatorInstance.get(sessionsId).setPcRegister(address);
        }
    }
}
