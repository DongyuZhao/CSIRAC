package project.emulator.framework.api.processor;


import project.emulator.framework.cpu.socket.IProcessor;
import project.emulator.framework.cpu.models.Command;

import javax.management.InstanceNotFoundException;

/**
 * Created by Dy.Zhao on 2016/1/23 0023.
 */
public interface IProcessUnit
{
    void  attachSocket(IProcessor _processorSocket);

    /**
     * Process the Command
     *
     * @param command the command
     * @return if the Pc Register has been changed by this Process Unit
     * @throws InstanceNotFoundException throw when the process socket is not find
     */
    boolean process(Command command) throws InstanceNotFoundException;
}
