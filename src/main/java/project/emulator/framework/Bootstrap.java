package project.emulator.framework;

import project.emulator.framework.api.config.IConfig;
import project.emulator.framework.api.config.symbol.ISymbolTranslator;
import project.emulator.framework.api.config.symbol.SymbolTranslator;
import project.emulator.framework.api.debugger.IDebugger;
import project.emulator.framework.api.decoder.IDecodeUnit;
import project.emulator.framework.api.monitor.IMonitor;
import project.emulator.framework.api.processor.IProcessUnit;
import project.emulator.framework.cpu.controlunit.ControlUnit;
import project.emulator.framework.cpu.controlunit.IControlUnit;
import project.emulator.framework.cpu.register.*;
import project.emulator.framework.cpu.socket.IProcessor;
import project.emulator.framework.cpu.socket.Processor;
import project.emulator.framework.cpu.decoder.Decoder;
import project.emulator.framework.cpu.decoder.IDecoder;
import project.emulator.framework.memory.IMemory;
import project.emulator.framework.memory.Memory;

import java.util.List;
import java.util.Map;

/**
 * Created by Dy.Zhao on 2016/1/23 0023.
 */
public class Bootstrap {
    private static ISymbolTranslator symbolTranslator = new SymbolTranslator();

    private static IConfig _innerConfig;

    /**
     * get the configuration the emulator is currently using
     *
     * @return the configuration
     */
    public static IConfig getInnerConfig() {
        return _innerConfig;
    }

    /**
     * set the inner configuration of the whole emulator
     *
     * @param config the configuration
     */
    public static void setInnerConfig(IConfig config) {
        Bootstrap._innerConfig = config;
    }

    /**
     * get the symbol translator
     *
     * @return the symbol translator
     */
    public static ISymbolTranslator getSymbolTranslator() {
        return symbolTranslator;
    }

    /**
     * set the symbol translator
     *
     * @param symbolTranslator the symbol translator
     */
    public static void setSymbolTranslator(ISymbolTranslator symbolTranslator) {
        Bootstrap.symbolTranslator = symbolTranslator;
    }

    /**
     * register a new map of symbol and code
     *
     * @param symbol the symbol
     * @param code the code
     */
    public static void registerSymbol(String symbol, int code) {
        symbolTranslator.registerSymbol(symbol, code);
    }

    /**
     * Create a new emulator instance with specified id, decode units, process units, register, debugger and monitor
     *
     * @param id the id
     * @param decodeUnits the decode units
     * @param processUnits the processor units
     * @param register the register
     * @param debugger the debugger
     * @param monitor the monitor
     */
    public static void createEmulator(String id, Map<String,List<IDecodeUnit>> decodeUnits, List<IProcessUnit> processUnits, IRegister register, IDebugger debugger, IMonitor monitor) {
        IDecoder decoder = Decoder.createInstance(decodeUnits);

        IPcRegister pcRegister = PcRegister.createInstance(id, debugger);

        IOpCodeRegister opCodeRegister = OpCodeRegister.createInstance(id, debugger);

        IMemory dataMemory = Memory.createInstance(id, false, monitor, debugger);

        IMemory instructionMemory = Memory.createInstance(id, true, monitor, debugger);

        IProcessor processor = new Processor(decoder, pcRegister, opCodeRegister, register, instructionMemory, dataMemory, processUnits);

        IControlUnit controlUnit = ControlUnit.createInstance(id, processor, pcRegister, opCodeRegister, monitor, debugger);

        EmulatorInstance emulatorInstance = new EmulatorInstance(controlUnit, instructionMemory, dataMemory);

        monitor.addNewSession(id, emulatorInstance);

        debugger.addNewSession(id, emulatorInstance);
    }
}
