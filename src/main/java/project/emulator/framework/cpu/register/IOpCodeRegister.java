package project.emulator.framework.cpu.register;

/**
 * Created by Dy.Zhao on 2016/1/22 0022.
 */
public interface IOpCodeRegister
{
    boolean put(int data);

    int get();
}
