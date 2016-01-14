package project.csirac.models.emulator;

/**
 * Created by Dy.Zhao on 2016/1/14 0014.
 */
public interface IExternalStorage
{

    /**
     * Save the program of the session
     *
     * @param sessionId
     *         the session id
     * @param program
     *         the program
     */
    void saveProgram(String sessionId, String[] program);

    /**
     * Load the program of the session
     *
     * @param sessionId
     *         the session id;
     * @param start
     *         the start address of the segment
     * @param length
     *         the length of the segment
     *
     * @return the program;
     */
    String[] loadProgram(String sessionId, int start, int length);
}
