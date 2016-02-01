package project.emulator.framework.cpu.socket;

import project.emulator.framework.api.processor.IProcessUnit;
import project.emulator.framework.cpu.register.IOpCodeRegister;
import project.emulator.framework.cpu.register.IPcRegister;
import project.emulator.framework.cpu.register.IRegister;
import project.emulator.framework.cpu.models.Command;
import project.emulator.framework.cpu.decoder.IDecoder;
import project.emulator.framework.memory.IMemory;

/**
 * Created by Dy.Zhao on 2016/1/21 0021.
 */
public interface IProcessor {
    /**
     * execute the program
     */
    void compute();

    /**
     * decode the instruction to find all Commands it contains
     *
     * @param instruction the instruction
     * @return Commands it contains in the priority order
     */
    Command[] decode(int[] instruction);

    /**
     * register new process unit
     *
     * @param processUnit the new process unit
     */
    void registerProcessorUnit(IProcessUnit processUnit);

    /**
     * register new decoder
     *
     * @param decoder the new decoder
     */
    void registerDecoder(IDecoder decoder);

    IDecoder decoder();

    IPcRegister pcRegister();

    IOpCodeRegister opCodeRegister();

    IRegister register();

    IMemory instructionMemory();

    IMemory dataMemory();


}
