package project.csirac.website.viewmodels.emulator.monitor;

/**
 * Created by Dy.Zhao on 2016/1/24 0024.
 */
public class DataViewModel {

    private String sessionId;

    private String[] data;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String[] getData() {
        return data;
    }

    public void setProgram(String[] program) {
        this.data = program;
    }
}
