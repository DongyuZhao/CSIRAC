package project.csirac.models.emulator;

/**
 * Created by Dy.Zhao on 2016/1/8 0008.
 */
public interface IMonitorObserver
{
    void updateMemoryView(String sessionId, String[] data);
    void updateRegisterView(String sessionId, String[] data);
    void updateInstructionView(String sessionId, String instruction);
}
