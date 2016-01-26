package project.emulator.framework.memory;


import project.emulator.framework.Bootstrap;
import project.emulator.framework.api.debugger.IDebugger;
import project.emulator.framework.api.debugger.MemoryMessage;
import project.emulator.framework.api.monitor.IMonitor;
import project.emulator.framework.api.monitor.MemoryMonitorMessageSender;

/**
 * Created by Dy.Zhao on 2016/1/22 0022.
 */
public class Memory extends MemoryMonitorMessageSender implements IMemory {
    private int _unitCount = Bootstrap.innerConfig.unitCount();

    private int _cellPerUnit = Bootstrap.innerConfig.cellPerUnit();

    private int[][] _memoryContainer = new int[_unitCount * _cellPerUnit][Bootstrap.innerConfig.mainDataSectionCount()];

    private boolean _isInstructionMemory = false;

    private int _upperBound = this._unitCount * this._cellPerUnit;


    public Memory(String _id, boolean instruction) {
        super(_id);
        this._isInstructionMemory = instruction;
    }

    public static IMemory createInstance(String id, boolean isInstruction, IMonitor monitor, IDebugger debugger) {
        Memory memory = new Memory(id, isInstruction);
        memory.attachDebugger(debugger);
        memory.attachMonitor(monitor);
        return memory;
    }

    @Override
    public boolean put(int unitAddress, int cellAddress, int[] data) {
        if (addressValid(unitAddress, cellAddress)) {
            int[] trimmedData = Bootstrap.symbolTranslator.trimData(data);
            int actualAddress = this.getInternalAddress(unitAddress, cellAddress);
            if (actualAddress >= 0) {
                this._memoryContainer[actualAddress] = trimmedData;
                this.onMemoryUpdate(new MemoryMessage(unitAddress, cellAddress, trimmedData), this);
                return true;
            }
        }
        return false;
    }

    @Override
    public int[] get(int unitAddress, int cellAddress) {
        int internalAddress = this.getInternalAddress(unitAddress, cellAddress);
        if (internalAddress < 0) {
            int[] result = new int[Bootstrap.innerConfig.mainDataSectionCount()];
            for (int i = 0; i < result.length; i++) {
                result[i] = Bootstrap.innerConfig.finishSignal();
            }
            return result;
        }
        return this._memoryContainer[internalAddress];
    }


    @Override
    public int unitCount() {
        return this._unitCount;
    }

    @Override
    public int cellPerUnit() {
        return this._cellPerUnit;
    }

    @Override
    public boolean load(String[] input) {
        for (int i = 0; i < input.length; i++) {
            String line = input[i];
            int[] parsedResult = Bootstrap.symbolTranslator.translateInput(line);
            if (parsedResult != null && parsedResult.length == Bootstrap.innerConfig.mainDataSectionCount()) {
                this._memoryContainer[i] = parsedResult;
                this.onMemoryUpdate(new MemoryMessage(i / this._cellPerUnit, i % this._cellPerUnit, parsedResult), this);
            }
        }
        this._upperBound = input.length;
        if (!this._isInstructionMemory) {
            this._upperBound = this._unitCount * this._cellPerUnit - 1;
        }
        this.onMemoryLoaded(this);
        return true;
    }

    @Override
    public boolean isInstructionMemory() {
        return this._isInstructionMemory;
    }


    private boolean addressValid(int unitAddress, int cellAddress) {
        return (unitAddress < this._unitCount && unitAddress >= 0 && cellAddress < this._cellPerUnit && cellAddress >= 0);
    }

    private int getInternalAddress(int unitAddress, int cellAddress) {
        if (unitAddress < this._unitCount && unitAddress >= 0 && cellAddress < this._cellPerUnit && cellAddress >= 0) {
            int result = unitAddress * this._cellPerUnit + cellAddress;
            if (result < this._upperBound) {
                return result;
            }
        }
        return -1;
    }
}
