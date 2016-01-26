package project.csirac.core;

import project.csirac.core.decoder.DCommandDecoder;
import project.csirac.core.decoder.SCommandDecoder;
import project.csirac.core.processor.MoveProcessor;
import project.csirac.core.register.Register;
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
    //    @Override
//    public static EmulatorInstance createEmulator(String _id)
//    {
//
//    }
	// Register supported instructions. For further instruction written, developers can add mapping between 
	// code and instructions here.
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

    private static List<IDecodeUnit> registerDecoders() {
        List<IDecodeUnit> IDecodeUnits = new ArrayList<>();
        IDecodeUnits.add(new SCommandDecoder());
        IDecodeUnits.add(new DCommandDecoder());
        return IDecodeUnits;
    }

    private static List<IProcessUnit> registerProcessors() {
        List<IProcessUnit> IProcessUnits = new ArrayList<>();
        IProcessUnits.add(new MoveProcessor());
        return IProcessUnits;
    }

    public static void createEmulator(String id, IMonitor monitor, IDebugger debugger) {
        IRegister register = Register.create(id, debugger);

        List<IProcessUnit> IProcessUnits = registerProcessors();

        List<IDecodeUnit> IDecodeUnits = registerDecoders();

        createEmulator(id, IDecodeUnits, IProcessUnits, register, debugger, monitor);
        //return createEmulator(id, IDecodeUnits, IProcessUnits, register, debugger, monitor);
    }
}
