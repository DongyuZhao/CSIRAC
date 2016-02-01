package project.csirac.core.processor;

import project.emulator.framework.Bootstrap;
import project.emulator.framework.api.processor.IProcessUnit;
import project.emulator.framework.cpu.models.Command;
import project.emulator.framework.cpu.socket.IProcessor;

import javax.management.InstanceNotFoundException;

/**
 * Created by Dy.Zhao on 2016/1/23 0023.
 */
public class MoveProcessor implements IProcessUnit {

    protected IProcessor _processSocket;


    public void attachSocket(IProcessor _processorSocket) {
        this._processSocket = _processorSocket;
    }

    public MoveProcessor() {
        //super(_processSocket);
    }

    // return if the pc register changed by instruction.
    // execute the instruction and if the value of pcregister changed, return true.
    @Override
    public boolean process(Command command) throws InstanceNotFoundException {
        if (this._processSocket != null) {
            if (command != null) {
                this._processSocket.opCodeRegister().put(command.opCode);
                if (command.commandType.equals("S")) {
                    if (command.opCode == Bootstrap.symbolTranslator.translateToCode("M")) {
                        int[] temp = this._processSocket.dataMemory().get(
                                command.source / Bootstrap.getInnerConfig().cellPerUnit(),
                                command.source % Bootstrap.getInnerConfig().cellPerUnit()
                        );
                        this._processSocket.register().put(command.target, temp);
                        return false;
                    } else if (command.opCode == Bootstrap.symbolTranslator.translateToCode("A")
                            || command.opCode == Bootstrap.symbolTranslator.translateToCode("B")
                            || command.opCode == Bootstrap.symbolTranslator.translateToCode("C")) {
                        int[] temp = this._processSocket.register().get(command.source);
                        this._processSocket.register().put(command.target, temp);
                        return false;
                    }
                }
                if (command.commandType.equals("D")) {
                    if (command.opCode == Bootstrap.symbolTranslator.translateToCode("M")) {
                        int[] temp = this._processSocket.register().get(command.source);
                        this._processSocket.dataMemory().put(
                                command.target / Bootstrap.getInnerConfig().cellPerUnit(),
                                command.target % Bootstrap.getInnerConfig().cellPerUnit(), temp
                        );
                        return false;
                    } else if (command.opCode == Bootstrap.symbolTranslator.translateToCode("A")
                            || command.opCode == Bootstrap.symbolTranslator.translateToCode("B")
                            || command.opCode == Bootstrap.symbolTranslator.translateToCode("C")) {
                        int[] temp = this._processSocket.register().get(command.source);
                        this._processSocket.register().put(command.target, temp);
                        return false;
                    }
                }
            }
            this._processSocket.pcRegister().put(Bootstrap.getInnerConfig().finishSignal());
            return true;
        }
        throw new InstanceNotFoundException();
    }
}
