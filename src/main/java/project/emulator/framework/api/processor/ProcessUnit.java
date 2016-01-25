package project.emulator.framework.api.processor;


import project.emulator.framework.Bootstrap;
import project.emulator.framework.cpu.socket.IProcessor;
import project.emulator.framework.cpu.decoder.Command;

/**
 * Created by Dy.Zhao on 2016/1/23 0023.
 */
public interface ProcessUnit
{

    //private List<ProcessUnit> _postProcessorList = new ArrayList<>();

    void  attachSocket(IProcessor _processorSocket);

    public boolean process(Command command);
}
