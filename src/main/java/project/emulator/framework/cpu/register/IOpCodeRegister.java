package project.emulator.framework.cpu.register;

/**
 * Created by Dy.Zhao on 2016/1/22 0022.
 */
public interface IOpCodeRegister {
    /**
     * save the data
     * @param data the data
     * @return if saved successful
     */
    boolean put(int data);

    int get();
}
