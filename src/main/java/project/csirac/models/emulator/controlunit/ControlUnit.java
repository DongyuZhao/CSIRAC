package project.csirac.models.emulator.controlunit;

import java.util.Map;

import project.csirac.models.emulator.IComputeUnit;
import project.csirac.models.emulator.IDecoder;
import project.csirac.models.emulator.IExternalStorage;
import project.csirac.models.emulator.IMemory;
import project.csirac.models.emulator.controlunit.operation.CommandOperation;
import project.csirac.models.emulator.model.Command;
import project.csirac.models.emulator.model.TempMemory;
import project.csirac.models.emulator.model.enums.DestinationGate;
import project.csirac.models.emulator.model.enums.RegisterCode;
import project.csirac.models.emulator.model.enums.SourceGate;
import project.csirac.models.emulator.parser.ICommandParser;
import project.csirac.models.emulator.register.SRegister;

public class ControlUnit implements IControlUnit {

	private IComputeUnit computeUnit;
	private IMemory memory;
	private IDecoder decoder;
	private IExternalStorage externalStorage;
	private ICommandParser commandParser;
	private SRegister sRegister;
	private Map<SourceGate, CommandOperation> sourceOperationSets;
	private Map<DestinationGate, CommandOperation> destOperationSets;

	private Map<RegisterCode, IMemory> registers;

	public ControlUnit(Map<SourceGate, CommandOperation> sourceOperationSets,
			Map<DestinationGate, CommandOperation> destOperationSets, SRegister sRegister,
			Map<RegisterCode, IMemory> registers) {
		this.sourceOperationSets = sourceOperationSets;
		this.destOperationSets = destOperationSets;
		this.sRegister = sRegister;
		this.registers = registers;
	}

	@Override
	public void attachComputeUnit(IComputeUnit computeUnit) {
		this.computeUnit = computeUnit;
	}

	@Override
	public void attachMemory(IMemory memory) {
		this.memory = memory;
	}

	@Override
	public void attachDecoder(IDecoder decoder) {
		this.decoder = decoder;
	}

	@Override
	public void attachExternalStorage(IExternalStorage externalStorage) {
		this.externalStorage = externalStorage;
	}

	@Override
	public void attachCommandParser(ICommandParser commandParser) {
		this.commandParser = commandParser;
	}

	@Override
	public void loadProgramToMemory(String sessionId, String[] program) {
		this.externalStorage.saveProgram(sessionId, program);
	}

	@Override
	public void startExecuting(String sessionId) {
		// String[] loadedPrograms = this.externalStorage.loadProgram(sessionId,
		// 0, 15);
		// for (String program : loadedPrograms) {
		// Command parseProgram = this.commandParser.parseProgram(program);
		// List<Integer> allData = new ArrayList<>();
		// for (String data : parseProgram.getData()) {
		// DataType decodeData = this.decoder.DecodeData(data);
		// int valueOrAddress = Integer.parseInt(decodeData.getValue());
		// if (decodeData.isInstantNumber()) {
		// allData.add(valueOrAddress);
		// } else {
		// allData.add(Integer.parseInt(this.memory.loadData(sessionId,
		// valueOrAddress)));
		// }
		// }
		// Instruction decodeInstruction =
		// this.decoder.DecodeInstruction(parseProgram.getInstruction());
		//
		// String result = this.computeUnit.computeResult(decodeInstruction,
		// allData);
		//
		// // TODO: put value back to register
		// }
	}

	@Override
	public void pauseExecuting(String sessionId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void nextInstruction(String sessionId) {
		// TODO confirm sRegister class cause there should be only 1 cell here,
		// thus 0 is not needed
		String instructionNumber = this.sRegister.loadData(sessionId, 0);
		int address = Integer.parseInt(instructionNumber);
		String instruction = this.memory.loadData(sessionId, address);
		Command parseProgram = this.commandParser.parseProgram(instruction);
		if (parseProgram.isControlDesignation()) {
			// TODO implement control designation logic
		} else {
			TempMemory temp = new TempMemory();
			CommandOperation srcOperation = this.sourceOperationSets.get(parseProgram.getSrcGate());
			srcOperation.doOperation(parseProgram, memory, sRegister, registers, computeUnit, temp, sessionId);

			CommandOperation destOperation = this.destOperationSets.get(parseProgram.getDestGate());
			destOperation.doOperation(parseProgram, memory, sRegister, registers, computeUnit, temp, sessionId);

			updateSRegisterFlag(sessionId);
		}

	}

	private void updateSRegisterFlag(String sessionId) {
		if (sRegister.isChanged()) {
			sRegister.resetChangeFlag();
		} else {
			sRegister.autoIncrement(sessionId);
		}
	}

	@Override
	public void continueExecuting(String sessionId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void stopExecuting(String sessionId) {
		// TODO Auto-generated method stub

	}

	@Override
	public String[] peekSupportedInstructions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setClockFrequency(String sessionId, float frequency) {
		// TODO Auto-generated method stub

	}

}
