package project.csirac.models.emulator.controlunit;

import java.util.ArrayList;
import java.util.List;

import project.csirac.models.emulator.IComputeUnit;
import project.csirac.models.emulator.IDecoder;
import project.csirac.models.emulator.IExternalStorage;
import project.csirac.models.emulator.IMemory;
import project.csirac.models.emulator.model.Command;
import project.csirac.models.emulator.model.DataType;
import project.csirac.models.emulator.model.Instruction;
import project.csirac.models.emulator.parser.ICommandParser;

public class ControlUnit implements IControlUnit {

	private IComputeUnit computeUnit;
	private IMemory memory;
	private IDecoder decoder;
	private IExternalStorage externalStorage;
	private ICommandParser commandParser;

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
		String[] loadedPrograms = this.externalStorage.loadProgram(sessionId, 0, 15);
		for (String program : loadedPrograms) {
			Command parseProgram = this.commandParser.parseProgram(program);
			List<Integer> allData = new ArrayList<>();
			for (String data : parseProgram.getData()) {
				DataType decodeData = this.decoder.DecodeData(data);
				int valueOrAddress = Integer.parseInt(decodeData.getValue());
				if (decodeData.isInstantNumber()) {
					allData.add(valueOrAddress);
				} else {
					allData.add(Integer.parseInt(this.memory.loadData(sessionId, valueOrAddress)));
				}
			}
			Instruction decodeInstruction = this.decoder.DecodeInstruction(parseProgram.getInstruction());

			String result = this.computeUnit.computeResult(decodeInstruction, allData);
			
			// TODO: put value back to register
		}
	}

	@Override
	public void pauseExecuting(String sessionId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void nextInstruction(String sessionId) {
		// TODO Auto-generated method stub

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
