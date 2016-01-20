package project.csirac.models.emulator;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dy.Zhao on 2016/1/14 0014.
 */
public class Memory implements IMemory
{
    //private Map<String, String[]> _programPool = new HashMap<>();

    private Map<String, String[]> _memoryContainer = new HashMap<>();

    private int _memoryUpperBound;

    public Memory(int capacity)
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
     */
    @Override
    public boolean saveData(String sessionId, int address, String data)
    {
        if (this._sessionExists(sessionId))
        {
            if (this._addressValid(address))
            {
                this._memoryContainer.get(sessionId)[address] = data;
                this._debugger.updateMemoryView(sessionId, this._memoryContainer.get(sessionId));
                return true;
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
                return this._memoryContainer.get(sessionId)[address];
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
     *         the program
     */
    @Override
    public void newSession(String sessionId, String[] program)
    {
        //this._programPool.put(sessionId, program);
        this._memoryContainer.put(sessionId, new String[this._memoryUpperBound]);
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
            this._memoryContainer.remove(sessionId);
            //this._programPool.remove(sessionId);
        }
        else
        {
            throw new IllegalArgumentException("Session Not Exist");
        }
    }

    private boolean _sessionExists(String sessionId)
    {
        return this._memoryContainer.containsKey(sessionId);
    }

    private boolean _addressValid(int address)
    {
        return address >= 0 && address < this._memoryUpperBound;
    }
}
