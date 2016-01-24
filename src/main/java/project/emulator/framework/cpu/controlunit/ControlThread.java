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
    public void run()
    {
        while (true)
        {
            try
            {
                if (this._controlUnit.isRequireStop())
                {
                    break;
                }
                while (!this._controlUnit.isRunning())
                {
                    System.out.println("Wait Run");
                    wait();
                }
                while (this._controlUnit.isPause())
                {
                    System.out.println("Pause");
                    wait();
                }
                System.out.println("Thread Run");
                this._controlUnit.next();
                Thread.sleep((int) (1000 / this._controlUnit.getClock()));
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }
}
