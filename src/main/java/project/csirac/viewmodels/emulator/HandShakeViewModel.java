package project.csirac.viewmodels.emulator;

/**
 * Created by Dy.Zhao on 2016/1/7 0007.
 */
public class HandShakeViewModel
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
