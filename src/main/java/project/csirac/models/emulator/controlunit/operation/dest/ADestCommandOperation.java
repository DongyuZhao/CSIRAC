package project.csirac.models.emulator.controlunit.operation.dest;

import java.util.Map;

import project.csirac.models.emulator.IComputeUnit;
import project.csirac.models.emulator.IMemory;
import project.csirac.models.emulator.controlunit.operation.CommandOperation;
import project.csirac.models.emulator.model.Command;
import project.csirac.models.emulator.model.TempMemory;
import project.csirac.models.emulator.model.enums.RegisterCode;
import project.csirac.models.emulator.register.SRegister;

public class ADestCommandOperation implements CommandOperation{

	@Override
	public void doOperation(Command command, IMemory memory, SRegister sRegister, Map<RegisterCode, IMemory> registers,
			IComputeUnit computeUnit, TempMemory tempMemory, String sessionId) {
		String tempInput = tempMemory.getContent();
		IMemory aRegister = registers.get(RegisterCode.A);
		aRegister.saveData(sessionId, 0, tempInput);
	}


}
