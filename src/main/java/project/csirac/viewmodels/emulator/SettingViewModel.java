package project.csirac.viewmodels.emulator;

/**
 * Created by Dy.Zhao on 2016/1/14 0014.
 */
public class SettingViewModel
{
    private String sessionId;

    private float frequency;

    public String getSessionId()
    {
        return sessionId;
    }

    public void setSessionId(String sessionId)
    {
        this.sessionId = sessionId;
    }

    public float getFrequency()
    {
        return frequency;
    }

    public void setFrequency(float frequency)
    {
        this.frequency = frequency;
    }
}
