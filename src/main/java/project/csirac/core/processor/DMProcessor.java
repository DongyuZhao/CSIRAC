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
public class DMProcessor implements IProcessUnit{
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
                if (command.commandType.equals("D")) {
                    if (command.opCode == Bootstrap.getSymbolTranslator().translateToCode("M")) {
                        int[] temp = this._processSocket.register().get(command.source);
                        this._processSocket.dataMemory().put(
                                command.target / Bootstrap.getInnerConfig().cellPerUnit(),
                                command.target % Bootstrap.getInnerConfig().cellPerUnit(), temp
                        );
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
