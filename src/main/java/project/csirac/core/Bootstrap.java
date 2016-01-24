package project.csirac.core;

import project.csirac.core.decoder.DCommandDecoder;
import project.csirac.core.decoder.SCommandDecoder;
import project.csirac.core.processor.MoveProcess;
import project.csirac.core.register.Register;
import project.emulator.framework.EmulatorInstance;
import project.emulator.framework.api.debugger.IDebugger;
import project.emulator.framework.api.decoder.DecodeUnit;
import project.emulator.framework.api.monitor.IMonitor;
import project.emulator.framework.api.processor.ProcessUnit;
import project.emulator.framework.cpu.register.IRegister;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dy.Zhao on 2016/1/23 0023.
 */
public class Bootstrap extends project.emulator.framework.Bootstrap
{
//    @Override
//    public static EmulatorInstance createEmulator(String _id)
//    {
//
//    }
    public static void registerCsiracSymbolTable()
    {
        for (int i = 0; i < 32; i++)
        {
            registerSymbol(i + "", i);
        }

        registerSymbol("M", 0);
        registerSymbol("A", 4);
        registerSymbol("B", 11);
        registerSymbol("C", 14);
        registerSymbol("D", 17);
        registerSymbol("Temp", -1024);
    }

    public static void createEmulator(String id, IMonitor monitor, IDebugger debugger)
    {
        IRegister register = Register.create(id, debugger);

        List<ProcessUnit> processUnits = new ArrayList<>();
        processUnits.add(new MoveProcess());

        List<DecodeUnit> decodeUnits = new ArrayList<>();
        decodeUnits.add(new SCommandDecoder());
        decodeUnits.add(new DCommandDecoder());

        createEmulatorInstance(id, decodeUnits, processUnits, register, debugger, monitor);
        //return createEmulatorInstance(id, decodeUnits, processUnits, register, debugger, monitor);
    }
}
