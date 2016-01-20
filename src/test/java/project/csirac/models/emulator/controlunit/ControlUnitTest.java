package project.csirac.models.emulator.controlunit;

import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.mockito.Mockito;

import project.csirac.models.emulator.IMemory;
import project.csirac.models.emulator.controlunit.operation.CommandOperation;
import project.csirac.models.emulator.controlunit.operation.dest.ADestCommandOperation;
import project.csirac.models.emulator.controlunit.operation.source.MSourceCommandOperation;
import project.csirac.models.emulator.model.Command;
import project.csirac.models.emulator.model.enums.DestinationGate;
import project.csirac.models.emulator.model.enums.RegisterCode;
import project.csirac.models.emulator.model.enums.SourceGate;
import project.csirac.models.emulator.parser.ICommandParser;
import project.csirac.models.emulator.register.SRegister;

public class ControlUnitTest {

	private static final String DUMMY_DATA = "dummyData";
	private static final String DUMMY_SESSION_ID = "dummySessionId";

	@Test
	public void nextInstructionWithDummyData() {
		// setup fixture
		Map<SourceGate, CommandOperation> sourceOperationSets = populateSourceGateCommandSet();
		Map<DestinationGate, CommandOperation> destOperationSets = populateDestGateCommandSet();
		SRegister sRegister = createSRegisterMock();
		IMemory aRegisterMock = mock(IMemory.class);
		Map<RegisterCode, IMemory> registers = createRegisterListWithARegister(aRegisterMock);
		IMemory memoryMock = createMemoryMock();
		ICommandParser commandParserMock = createCommandParserMock();
		ControlUnit controlUnit = new ControlUnit(sourceOperationSets, destOperationSets, sRegister, registers);
		controlUnit.attachMemory(memoryMock);
		controlUnit.attachCommandParser(commandParserMock);
		
		// exercise SUT
		controlUnit.nextInstruction(DUMMY_SESSION_ID);

		// verify
		assertThat(sRegister.isChanged(), Matchers.equalTo(false));
		verify(sRegister).autoIncrement(DUMMY_SESSION_ID);
		verify(aRegisterMock).saveData(DUMMY_SESSION_ID, 0, DUMMY_DATA);
	}

	private SRegister createSRegisterMock() {
		SRegister sRegister = mock(SRegister.class);
		when(sRegister.loadData(Mockito.anyString(), Mockito.anyInt())).thenReturn("0");
		return sRegister;
	}

	private IMemory createMemoryMock() {
		IMemory memoryMock = mock(IMemory.class);
		when(memoryMock.loadData(Mockito.anyString(), Mockito.anyInt())).thenReturn(DUMMY_DATA);
		return memoryMock;
	}

	private ICommandParser createCommandParserMock() {
		ICommandParser commandParserMock = mock(ICommandParser.class);
		Command command = populateCommandMA();
		when(commandParserMock.parseProgram(Mockito.anyString())).thenReturn(command);
		return commandParserMock;
	}

	private Map<RegisterCode, IMemory> createRegisterListWithARegister(IMemory aRegisterMock) {
		Map<RegisterCode, IMemory> registers = new HashMap<>();
		registers.put(RegisterCode.A, aRegisterMock);
		return registers;
	}

	private Map<DestinationGate, CommandOperation> populateDestGateCommandSet() {
		Map<DestinationGate, CommandOperation> destOperationSets = new HashMap<>();
		destOperationSets.put(DestinationGate.A, new ADestCommandOperation());
		return destOperationSets;
	}

	private Map<SourceGate, CommandOperation> populateSourceGateCommandSet() {
		Map<SourceGate, CommandOperation> sourceOperationSets = new HashMap<>();
		sourceOperationSets.put(SourceGate.M, new MSourceCommandOperation());
		return sourceOperationSets;
	}

	private Command populateCommandMA() {
		Command command = new Command();
		command.setAddress(0);
		command.setControlDesignation(false);
		command.setdAddress(0);
		command.setDestGate(DestinationGate.A);
		command.setSrcGate(SourceGate.M);
		return command;
	}

}
