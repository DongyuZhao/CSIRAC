package project.csirac.viewmodels.emulator;


/**
 * Created by Dy.Zhao on 2016/1/4 0004.
 */
public class ProgramViewModel
{
    private String sessionId;

    private String[] program;

    public String getSessionId()
    {
        return sessionId;
    }

    public void setSessionId(String sessionId)
    {
        this.sessionId = sessionId;
    }

    public String[] getProgram()
    {
        return program;
    }

    public void setProgram(String[] program)
    {
        this.program = program;
    }
}
