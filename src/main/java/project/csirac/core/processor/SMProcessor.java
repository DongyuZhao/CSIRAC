package project.csirac.core.processor;

import project.csirac.core.CsiracBootstrap;
import project.emulator.framework.Bootstrap;
import project.emulator.framework.api.processor.IProcessUnit;
import project.emulator.framework.cpu.models.Command;
import project.emulator.framework.cpu.socket.IProcessor;

import javax.management.InstanceNotFoundException;

/**
 * Created by Dy.Zhao on 2016/2/2 0002.
 */
public class SMProcessor implements IProcessUnit {
    protected IProcessor _processSocket;

    @Override
    public void attachSocket(IProcessor _processorSocket) {
        this._processSocket = _processorSocket;
    }

    @Override
    public boolean process(Command command) throws InstanceNotFoundException {
        if (this._processSocket != null) {
            if (command != null) {
                if (command.opCode == CsiracBootstrap.getInnerConfig().finishSignal()) {
                    this._processSocket.pcRegister().put(CsiracBootstrap.getInnerConfig().finishSignal());
                    return true;
                }
                this._processSocket.opCodeRegister().put(command.opCode);
                if (command.commandType.equals("S")) {
                    if (command.opCode == Bootstrap.getSymbolTranslator().translateToCode("M")) {
                        int[] temp = this._processSocket.dataMemory().get(
                                command.source / Bootstrap.getInnerConfig().cellPerUnit(),
                                command.source % Bootstrap.getInnerConfig().cellPerUnit()
                        );
                        this._processSocket.register().put(command.target, temp);
                        return false;
                    }
                }
                return false;
            }
            this._processSocket.pcRegister().put(Bootstrap.getInnerConfig().finishSignal());
            return true;
        }
        throw new InstanceNotFoundException();
    }
}
