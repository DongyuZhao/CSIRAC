package project.emulator.framework.cpu.controlunit;

/**
 * Created by Dy.Zhao on 2016/1/24 0024.
 */
public class ControlThread implements Runnable
{

    private ControlUnit _controlUnit;

    public ControlThread(ControlUnit controlUnit)
    {
        this._controlUnit = controlUnit;
    }

    @Override
    public synchronized void run()
    {
        this._controlUnit.run();
    }
}
