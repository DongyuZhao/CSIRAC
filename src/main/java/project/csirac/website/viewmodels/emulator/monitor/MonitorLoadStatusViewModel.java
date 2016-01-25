package project.csirac.website.viewmodels.emulator.monitor;

/**
 * Created by Dy.Zhao on 2016/1/24 0024.
 */
public class MonitorLoadStatusViewModel
{
    private boolean dataUploaded;

    private boolean programUploaded;

    public boolean isDataUploaded()
    {
        return dataUploaded;
    }

    public void setDataUploaded(boolean dataUploaded)
    {
        this.dataUploaded = dataUploaded;
    }

    public boolean isProgramUploaded()
    {
        return programUploaded;
    }

    public void setProgramUploaded(boolean memoryUploaded)
    {
        this.programUploaded = memoryUploaded;
    }
}
