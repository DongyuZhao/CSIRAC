package project.csirac.models.emulator;

import java.util.Map;

/**
 * Created by Dy.Zhao on 2016/1/8 0008.
 */
public interface IViewObserver
{

    /**
     * Push the updated memory of the session to observer
     *
     * @param sessionId the session id
     * @param data the memory data
     */
    void pushUpdatedMemory(String sessionId, String[] data);

    /**
     * Push the updated register of the session to observer
     *
     * @param sessionId the session id
     * @param data the register data
     */
    void pushUpdatedRegister(String sessionId, Map<String, String> data);

    /**
     * Push current instruction of the session to observer
     *
     * @param sessionId the session id
     * @param instruction current instruction
     */
    void pushUpdatedInstruction(String sessionId, String instruction);

    /**
     * Push current pc reg of the session to observer
     *
     * @param sessionId the session id
     * @param pcRegister current pc reg
     */
    void pushPcRegister(String sessionId, String pcRegister);

    /**
     * Push output of the session to observer
     *
     * @param sessionId the session id
     * @param output output
     */
    void pushOutput(String sessionId, String[] output);
}
