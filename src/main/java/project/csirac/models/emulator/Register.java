package project.csirac.models.emulator;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dy.Zhao on 2016/1/19 0019.
 */
public class Register implements IMemory
{
    private Map<String, String[]> _regContainer = new HashMap<>();

    private int _memoryUpperBound;

    public Register(int capacity)
    {
        this._memoryUpperBound = capacity;
    }

    private IDebugger _debugger;
    /**
     * Attach the debugger to the memory
     *
     * @param debugger
     *         the debugger
     */
    @Override
    public void attachDebugger(IDebugger debugger)
    {
        this._debugger = debugger;
    }

    /**
     * Save the data to certain address of memory of the session
     *
     * @param sessionId
     *         the session Id
     * @param address
     *         the address
     * @param data
     *         the data
     *
     * @return if the save is successful
     */
    @Override
    public boolean saveData(String sessionId, int address, String data)
    {
        if (this._sessionExists(sessionId))
        {
            if (this._addressValid(address))
            {
                if (this._isDataCompatible(sessionId, address, data))
                {
                    this._regContainer.get(sessionId)[address] = data;
                    this._debugger.updateRegisterView(sessionId, this._regContainer.get(sessionId));
                    return true;
                }
                else
                {
                    return false;
                }
            }
            throw new IllegalArgumentException("Address Illegal");
        }
        else
        {
            throw new IllegalArgumentException("Session Not Exist");
        }
    }

    /**
     * Load the data from certain address of memory from the session
     *
     * @param sessionId
     *         the session id
     * @param address
     *         the address
     *
     * @return the data
     */
    @Override
    public String loadData(String sessionId, int address)
    {
        if (this._sessionExists(sessionId))
        {
            if (this._addressValid(address))
            {
                return this._regContainer.get(sessionId)[address];
            }
            throw new IllegalArgumentException("Address Illegal");
        }
        else
        {
            throw new IllegalArgumentException("Session Not Exist");
        }
    }

    /**
     * Create a new session
     *
     * @param sessionId
     *         the session id
     * @param program
     */
    @Override
    public void newSession(String sessionId, String[] program)
    {
        this._regContainer.put(sessionId, new String[this._memoryUpperBound]);
    }

    /**
     * Remove the session
     *
     * @param sessionId
     *         the session id
     */
    @Override
    public void removeSession(String sessionId)
    {
        if (this._sessionExists(sessionId))
        {
            this._regContainer.remove(sessionId);
            //this._programPool.remove(sessionId);
        }
        else
        {
            throw new IllegalArgumentException("Session Not Exist");
        }
    }

    private boolean _sessionExists(String sessionId)
    {
        return this._regContainer.containsKey(sessionId);
    }

    private boolean _addressValid(int address)
    {
        return address >= 0 && address < this._memoryUpperBound;
    }

    private boolean _isDataCompatible(String sessionId, int address, String data)
    {
        // TODO:: Add the logic to check the compatibility of the data and the register
        return true;
    }
}
