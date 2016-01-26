package project.emulator.framework.cpu.register;

import project.emulator.framework.Bootstrap;
import project.emulator.framework.api.debugger.DebuggerMessageSender;
import project.emulator.framework.api.debugger.IDebugger;

/**
 * Created by Dy.Zhao on 2016/1/22 0022.
 */
public class OpCodeRegister extends DebuggerMessageSender implements IOpCodeRegister {
    private int _value;

    private int _upperBound = Bootstrap.innerConfig.opCodeUpperbound();

    public OpCodeRegister(String _id) {
        super(_id);
    }

    public static OpCodeRegister createInstance(String id, IDebugger debugger) {
        OpCodeRegister opCodeRegister = new OpCodeRegister(id);
        opCodeRegister.attachDebugger(debugger);
        return opCodeRegister;
    }

    @Override
    public boolean put(int data) {
        if (data >= 0 && data < _upperBound) {
            this._value = data;
            this.onOpCodeUpdate(data, this);
            return true;
        }
        return false;
    }

    @Override
    public int get() {
        return this._value;
    }
}
