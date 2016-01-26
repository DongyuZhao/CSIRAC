package project.emulator.framework.api.processor;


import project.emulator.framework.Bootstrap;
import project.emulator.framework.cpu.socket.IProcessor;
import project.emulator.framework.cpu.decoder.Command;

import javax.management.InstanceNotFoundException;

/**
 * Created by Dy.Zhao on 2016/1/23 0023.
 */
public interface IProcessUnit
{

    //private List<IProcessUnit> _postProcessorList = new ArrayList<>();

    void  attachSocket(IProcessor _processorSocket);

    boolean process(Command command) throws InstanceNotFoundException;
}
