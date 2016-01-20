package project.csirac.models.emulator.model;

import java.util.List;

public class Command {

	private List<String> data;

	private String instruction;
	
	public Command(List<String> data, String instruction) {
		this.data = data;
		this.instruction = instruction;
	}

	public List<String> getData() {
		return data;
	}

	public String getInstruction() {
		return instruction;
	}
}
