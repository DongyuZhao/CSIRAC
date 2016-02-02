package project.csirac.core.register;

import project.csirac.core.CsiracBootstrap;
import project.emulator.framework.api.debugger.DebuggerMessageSender;
import project.emulator.framework.api.debugger.IDebugger;
import project.emulator.framework.api.debugger.RegisterMessage;
import project.emulator.framework.cpu.register.IRegister;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dy.Zhao on 2016/1/22 0022.
 */
public class Register extends DebuggerMessageSender implements IRegister
{
    private Map<String, int[]> _regContainer = new HashMap<>();

    private int[][] _dRegister = new int[16][4];

    public Register(String _id)
    {
        super(_id);
        this._regContainer.put("A", new int[4]);
        this._regContainer.put("B", new int[4]);
        this._regContainer.put("C", new int[4]);
        this._regContainer.put("H", new int[4]);
        this._regContainer.put("Temp", new int[4]);
    }

    public static IRegister create(String _id, IDebugger debugger)
    {
        Register register = new Register(_id);
        register.attachDebugger(debugger);
        return register;
    }

    @Override
    public boolean put(int address, int[] data)
    {
        if (address >= 32 && address < 48) // set D0 to 32...
        {
            if (data.length == 4)
            {
                int dAddress = address - 32;
                this._dRegister[dAddress] = data;
                this.onRegisterUpdate(new RegisterMessage("D" + dAddress, data), this);
                return true;
            }
        }
        String regName = CsiracBootstrap.getSymbolTranslator().translateToSymbol(address);
        if (regName != null)
        {
            int[] trimmedData = CsiracBootstrap.getSymbolTranslator().trimData(data);
            if (data.length == 4)
            {
                this._regContainer.put(regName, trimmedData);
                this.onRegisterUpdate(new RegisterMessage(regName, trimmedData), this);
                return true;
            }
        }
        return false;
    }

    @Override
    public int[] get(int address)
    {
        if (address >= 32 && address < 48) // set D0 to 32...
        {
            int dAddress = address - 32;
            return this._dRegister[dAddress];
        }
        String regName = CsiracBootstrap.getSymbolTranslator().translateToSymbol(address);
        if (regName != null)
        {
            return this._regContainer.get(regName);
        }
        int[] newResult = new int[4];
        for (int i = 0; i < 4; i++)
        {
            newResult[i] = CsiracBootstrap.getInnerConfig().finishSignal();
        }
        return newResult;
    }
}
