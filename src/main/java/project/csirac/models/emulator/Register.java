package project.csirac.models.emulator;

import org.apache.commons.lang3.ArrayUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Dy.Zhao on 2016/1/19 0019.
 */
public class Register implements IRegister
{
    private Map<String, Map<String, String>> _regContainer = new HashMap<>();

    private String[] _validAddressList = {"A","B","C","H","S","K","I","O"};

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
    public boolean saveData(String sessionId, String address, String data)
    {
        if (this._sessionExists(sessionId))
        {
            if (this._addressValid(address))
            {
                if (this._isDataCompatible(sessionId, address, data))
                {
                    this._regContainer.get(sessionId).put(address, data);
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
        //throw new NotImplementedException();
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
    public String loadData(String sessionId, String address)
    {
        if (this._sessionExists(sessionId))
        {
            if (this._addressValid(address))
            {
                return this._regContainer.get(sessionId).get(address);
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
     */
    @Override
    public void newSession(String sessionId)
    {
        this._regContainer.put(sessionId, new HashMap<>());
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

    private boolean _addressValid(String address)
    {
        return ArrayUtils.contains(this._validAddressList, address) || this._isDRegister(address);
    }

    private boolean _isDRegister(String address)
    {
        Pattern p = Pattern.compile("D([1-9]?|1[0-5])");
        Matcher m = p.matcher(address);
        return m.matches();
    }

    private boolean _isDataCompatible(String sessionId, String address, String data)
    {
        // TODO:: Add the logic to check the compatibility of the data and the register
        if (!address.equals("H"))
        {
            return data.length() == 20;
        }
        return data.length() == 10;
    }
}
