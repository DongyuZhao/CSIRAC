package project.csirac.website.viewmodels.emulator.debugger;

/**
 * Created by Dy.Zhao on 2016/1/24 0024.
 */
public class ClockViewModel {
    private String sessionId;

    private float frequency;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public float getFrequency() {
        return frequency;
    }

    public void setFrequency(float frequency) {
        this.frequency = frequency;
    }
}
