package project.csirac.models.emulator.controlunit.operation;

import java.util.Map;

import project.csirac.models.emulator.IComputeUnit;
import project.csirac.models.emulator.IMemory;
import project.csirac.models.emulator.model.Command;
import project.csirac.models.emulator.model.TempMemory;
import project.csirac.models.emulator.model.enums.RegisterCode;
import project.csirac.models.emulator.register.SRegister;

public interface CommandOperation {

	// TODO update this strategy interface with proper wrap up class when more
	// registers or components are involved
	void doOperation(Command command, IMemory memory, SRegister sRegister, Map<RegisterCode, IMemory> registers,
			IComputeUnit computeUnit, TempMemory tempMemory, String sessionId);
}
