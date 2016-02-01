package project.csirac.core.processor;

import project.emulator.framework.Bootstrap;
import project.emulator.framework.api.processor.IProcessUnit;
import project.emulator.framework.cpu.models.Command;
import project.emulator.framework.cpu.socket.IProcessor;

import javax.management.InstanceNotFoundException;

/**
 * Created by Dy.Zhao on 2016/1/27 0027.
 */
public class AddProcessor implements IProcessUnit {

    protected IProcessor _processSocket;

    @Override
    public void attachSocket(IProcessor _processorSocket) {
        this._processSocket = _processorSocket;
    }


    @Override
    public boolean process(Command command) throws InstanceNotFoundException {
        if (this._processSocket != null) {
            if (command.opCode == Bootstrap.symbolTranslator.translateToCode("PA")
                    || command.opCode == Bootstrap.symbolTranslator.translateToCode("PB")
                    || command.opCode == Bootstrap.symbolTranslator.translateToCode("PC")) {
                int[] temp = this._processSocket.register().get(command.source);
                int[] addee = this._processSocket.register().get(command.target);
                int inner = DataTranslator.translateToNumber(temp) + DataTranslator.translateToNumber(addee);
                this._processSocket.register().put(command.target, DataTranslator.translateToData(inner));
                return false;
            }
            this._processSocket.pcRegister().put(-1);
            return true;
        }
        throw new InstanceNotFoundException();
    }
}
