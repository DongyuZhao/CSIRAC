package project.csirac.core;

import project.csirac.core.config.Config;
import project.csirac.core.decoder.DCommandDecoder;
import project.csirac.core.decoder.SCommandDecoder;
import project.csirac.core.processor.MoveProcessor;
import project.csirac.core.register.Register;
import project.emulator.framework.Bootstrap;
import project.emulator.framework.api.debugger.IDebugger;
import project.emulator.framework.api.decoder.IDecodeUnit;
import project.emulator.framework.api.monitor.IMonitor;
import project.emulator.framework.api.processor.IProcessUnit;
import project.emulator.framework.cpu.register.IRegister;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dy.Zhao on 2016/1/23 0023.
 */
public class CsiracBootstrap extends project.emulator.framework.Bootstrap {

    public static void setCsiracConfig() {
        Bootstrap.setInnerConfig(new Config());
    }

    /**
     * Register the CSIRAC symbol into the Emulator Framework
     */
    public static void registerCsiracSymbolTable() {
        for (int i = 0; i < 32; i++) {
            registerSymbol(i + "", i);
        }

        registerSymbol("M", 0);
        registerSymbol("A", 4);
        registerSymbol("B", 11);
        registerSymbol("C", 14);
        registerSymbol("D", 17);
        registerSymbol("Temp", -1024);
    }

    /**
     * Generate the CSIRAC Decoder List the Emulator Framework needs to perform the decoding of CSIRAC instructions
     *
     * @return the CSIRAC Decoder List
     */
    private static List<IDecodeUnit> registerDecoders() {
        List<IDecodeUnit> IDecodeUnits = new ArrayList<>();
        IDecodeUnits.add(new SCommandDecoder());
        IDecodeUnits.add(new DCommandDecoder());

        // For those who wish to add new instructions de-comment and modify the following line.
        // IDecodeUnits.add(new IDecodeUnit());

        return IDecodeUnits;
    }

    /**
     * Generate the CSIRAC Processor List the Emulator Framework needs to perform the processing of CSIRAC instructions
     *
     * @return the CSIRAC Processor List
     */
    private static List<IProcessUnit> registerProcessors() {
        List<IProcessUnit> IProcessUnits = new ArrayList<>();
        IProcessUnits.add(new MoveProcessor());

        // For those who wish to add new instructions de-comment and modify the following line.
        // IProcessUnits.add(new IProcessUnit());

        return IProcessUnits;
    }

    /**
     * Create a CSIRAC emulator with the specified instance id.
     *
     * @param id the instance id.
     * @param monitor the monitor will monitor the emulator
     * @param debugger the debugger will debug the emulator
     */
    public static void createEmulator(String id, IMonitor monitor, IDebugger debugger) {
        IRegister register = Register.create(id, debugger);

        List<IProcessUnit> IProcessUnits = registerProcessors();

        List<IDecodeUnit> IDecodeUnits = registerDecoders();

        createEmulator(id, IDecodeUnits, IProcessUnits, register, debugger, monitor);
    }
}
