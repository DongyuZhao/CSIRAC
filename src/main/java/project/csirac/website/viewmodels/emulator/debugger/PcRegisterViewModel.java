package project.csirac.website.viewmodels.emulator.debugger;

/**
 * Created by Dy.Zhao on 2016/1/24 0024.
 */
public class PcRegisterViewModel {
    private String sessionId;

    private int address;

    public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
        this.address = address;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
