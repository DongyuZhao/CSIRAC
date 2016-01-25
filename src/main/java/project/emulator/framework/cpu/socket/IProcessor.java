package project.emulator.framework.cpu.socket;

import project.emulator.framework.api.processor.ProcessUnit;
import project.emulator.framework.cpu.register.IOpCodeRegister;
import project.emulator.framework.cpu.register.IPcRegister;
import project.emulator.framework.cpu.register.IRegister;
import project.emulator.framework.cpu.decoder.Command;
import project.emulator.framework.cpu.decoder.IDecoder;
import project.emulator.framework.memory.IMemory;

import java.util.List;

/**
 * Created by Dy.Zhao on 2016/1/21 0021.
 */
public interface IProcessor
{
    void compute();

    Command[] decode(int[] instruction);

    void registerProcessorUnit(ProcessUnit processUnit);

    void registerDecoder(IDecoder decoder);

    IDecoder decoder();

    IPcRegister pcRegister();

    IOpCodeRegister opCodeRegister();

    IRegister register();

    IMemory instructionMemory();

    IMemory dataMemory();

    List<ProcessUnit> instructionMulticastList();
}
