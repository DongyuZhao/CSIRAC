package project.csirac.website.viewmodels.emulator.control;

/**
 * Created by Dy.Zhao on 2016/1/24 0024.
 */
public class ControlViewModel
{
    private String sessionId;

    private String operation;

    public String getOperation()
    {
        return operation;
    }

    public void setOperation(String operation)
    {
        this.operation = operation;
    }

    public String getSessionId()
    {
        return sessionId;
    }

    public void setSessionId(String sessionId)
    {
        this.sessionId = sessionId;
    }
}
