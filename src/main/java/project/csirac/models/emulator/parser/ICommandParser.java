package project.csirac.models.emulator.parser;

import project.csirac.models.emulator.model.Command;

public interface ICommandParser {

	Command parseProgram(String program);
}
