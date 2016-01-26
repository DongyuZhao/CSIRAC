package project.emulator.framework.cpu.controlunit;

/**
 * Created by Dy.Zhao on 2016/1/21 0021.
 */
public interface IControlUnit {
    void start();

    void pause() throws InterruptedException;

    void next() throws InterruptedException;

    void next(boolean ignorePause) throws InterruptedException;

    void go() throws InterruptedException;

    void forceStop();

    void stop() throws InterruptedException;

    float getClock();

    void setClock(float frequency);

    int getPcRegister();

    void setPcRegister(int address) throws InterruptedException;

    int getOpCode();

    void setOpCode(int code) throws InterruptedException;

    boolean isRunning();

    boolean isPause();

    boolean isRequireStop();
}
