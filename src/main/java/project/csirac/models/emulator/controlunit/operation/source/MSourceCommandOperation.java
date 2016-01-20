package project.csirac.models.emulator.controlunit.operation.source;

import java.util.Map;

import project.csirac.models.emulator.IComputeUnit;
import project.csirac.models.emulator.IMemory;
import project.csirac.models.emulator.controlunit.operation.CommandOperation;
import project.csirac.models.emulator.model.Command;
import project.csirac.models.emulator.model.TempMemory;
import project.csirac.models.emulator.model.enums.RegisterCode;
import project.csirac.models.emulator.register.SRegister;

public class MSourceCommandOperation implements CommandOperation {

	@Override
	public void doOperation(Command command, IMemory memory, SRegister sRegister, Map<RegisterCode, IMemory> registers,
			IComputeUnit computeUnit, TempMemory tempMemory, String sessionId) {
		String data = memory.loadData(sessionId, command.getAddress());
		tempMemory.setContent(data);
	}

}
