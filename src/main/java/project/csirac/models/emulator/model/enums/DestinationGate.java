package project.csirac.models.emulator.model.enums;

public enum DestinationGate {

	A("A", 4);

	private String symbol;

	private int code;

	DestinationGate(String symbol, int code) {
		this.symbol = symbol;
		this.code = code;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

}
