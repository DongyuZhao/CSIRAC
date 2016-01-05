package project.csirac.viewmodels.emulator;


/**
 * Created by Dy.Zhao on 2016/1/4 0004.
 */
public class ProgramViewModel
{
    private String userSessionId;

    private String[] program;

    public String getUserSessionId()
    {
        return userSessionId;
    }

    public void setUserSessionId(String userSessionId)
    {
        this.userSessionId = userSessionId;
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
