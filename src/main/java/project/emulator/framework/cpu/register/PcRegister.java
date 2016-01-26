package project.emulator.framework.cpu.register;

import project.emulator.framework.Bootstrap;
import project.emulator.framework.api.debugger.DebuggerMessageSender;
import project.emulator.framework.api.debugger.IDebugger;

/**
 * Created by Dy.Zhao on 2016/1/22 0022.
 */
public class PcRegister extends DebuggerMessageSender implements IPcRegister {
    private int _value;

    private int _upperBound = Bootstrap.innerConfig.unitCount() * Bootstrap.innerConfig.cellPerUnit();

    public PcRegister(String _id) {
        super(_id);
    }

    public static IPcRegister createInstance(String id, IDebugger debugger) {
        PcRegister pcRegister = new PcRegister(id);
        pcRegister.attachDebugger(debugger);
        return pcRegister;
    }

    @Override
    public boolean put(int data) {
        if (data < this._upperBound) {
            this._value = data;
            System.out.println("Pc:" + data);
            this.onPcRegisterUpdate(data, this);
            return true;
        }
        return false;
    }

    @Override
    public int get() {
        return this._value;
    }
}
