package project.csirac.models.emulator.register;

import project.csirac.models.emulator.Memory;

public class SRegister extends Memory {

	private boolean isJumpChanged;

	public SRegister() {
		super(1);
	}

	public boolean isChanged() {
		return isJumpChanged;
	}

	public void setChanged(boolean isChanged) {
		this.isJumpChanged = isChanged;
	}

	@Override
	public boolean saveData(String sessionId, int address, String data) {
		this.isJumpChanged = true;
		return super.saveData(sessionId, address, data);
	}
	
	public void resetChangeFlag() {
		this.isJumpChanged = false;
	}

	public int autoIncrement(String sessionId) {
		String loadData = this.loadData(sessionId, 0);
		int newS = Integer.parseInt(loadData) + 1;
		this.saveData(sessionId, 0, newS + "");
		this.resetChangeFlag();
		return newS;
	}

}
