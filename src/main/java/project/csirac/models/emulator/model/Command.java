package project.csirac.models.emulator.model;

import project.csirac.models.emulator.model.enums.DestinationGate;
import project.csirac.models.emulator.model.enums.SourceGate;

public class Command {

	private int address;

	private int dAddress;

	private SourceGate srcGate;

	private DestinationGate destGate;
	
	private boolean isControlDesignation = false;
	
	public boolean isControlDesignation() {
		return isControlDesignation;
	}

	public void setControlDesignation(boolean isControlDesignation) {
		this.isControlDesignation = isControlDesignation;
	}

	public int getAddress() {
		return address;
	}

	public void setAddress(int address) {
		this.address = address;
	}

	public int getdAddress() {
		return dAddress;
	}

	public void setdAddress(int dAddress) {
		this.dAddress = dAddress;
	}

	public SourceGate getSrcGate() {
		return srcGate;
	}

	public void setSrcGate(SourceGate srcGate) {
		this.srcGate = srcGate;
	}

	public DestinationGate getDestGate() {
		return destGate;
	}

	public void setDestGate(DestinationGate destGate) {
		this.destGate = destGate;
	}

	// TODO add control command in the later development

}
