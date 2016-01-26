package project.emulator.framework.cpu.controlunit;

import project.emulator.framework.Bootstrap;
import project.emulator.framework.api.debugger.IDebugger;
import project.emulator.framework.api.monitor.CpuMonitorMessageSender;
import project.emulator.framework.api.monitor.IMonitor;
import project.emulator.framework.cpu.socket.IProcessor;
import project.emulator.framework.cpu.register.IOpCodeRegister;
import project.emulator.framework.cpu.register.IPcRegister;

/**
 * Created by Dy.Zhao on 2016/1/21 0021.
 */
public class ControlUnit extends CpuMonitorMessageSender implements IControlUnit {
    private IProcessor _processorSocket;

    private IPcRegister _pcRegister;

    private IOpCodeRegister _opCodeRegister;

    private float _frequency = 1;

    private boolean _paused = false;

    private boolean _started = false;

    private boolean _locked = false;

    private boolean _stopSingnal = false;

    public ControlUnit(String id, IProcessor processorSocket, IPcRegister pcRegister, IOpCodeRegister opCodeRegister) {
        super(id);
        this._processorSocket = processorSocket;
        this._pcRegister = pcRegister;
        this._opCodeRegister = opCodeRegister;
    }

    public static IControlUnit createInstance(String id, IProcessor processor, IPcRegister pcRegister,
                                              IOpCodeRegister opCodeRegister, IMonitor monitor, IDebugger debugger) {
        ControlUnit controlUnit = new ControlUnit(id, processor, pcRegister, opCodeRegister);
        controlUnit.attachMonitor(monitor);
        controlUnit.attachDebugger(debugger);
        return controlUnit;
    }


    private void reset() {
        this._started = false;
        this._paused = false;
        this._pcRegister.put(0);
        this._opCodeRegister.put(0);
    }

    public synchronized void run() {
        if (!this.isRunning()) {
            try {
                while (this._locked) {
                    wait();

                }
                this._locked = true;
                this._started = true;
                this._stopSingnal = false;
                this._paused = false;
                this._locked = false;
                notifyAll();
                this.onStart();
                while (true) {
                    if (this._stopSingnal) {
                        break;
                    }
                    while (this._paused) {
                        wait();
                    }
                    System.out.println("Run");
                    System.out.println(this._stopSingnal);
                    this.next();
                    Thread.sleep((int) (1000 / this.getClock()));
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    /*
     * start executing.
     */
    @Override
    public void start() {
        System.out.println("Click Run");
        Thread thread = new Thread(new ControlThread(this));
        thread.start();
    }

    void onStart() {
        this.onStart(this);
    }
    /*
     * Pause the process.
     */
    @Override
    public void pause() throws InterruptedException {
        System.out.println("Press Pause");
        if (this._started && !this._paused) {
            this._paused = true;
            this.onPause(this);
        }
    }
    /*
     * Continue executing.
     */
    @Override
    public void next() throws InterruptedException {
        this.next(false);
    }

    @Override
    public synchronized void next(boolean ignorePause) throws InterruptedException {
        if (this._started && (ignorePause || !this._paused)) {
            System.out.println("Next");
            int instructionPointer = this._pcRegister.get();
            if (instructionPointer < Bootstrap.innerConfig.unitCount() * Bootstrap.innerConfig.cellPerUnit() && instructionPointer >= 0) {
                this._processorSocket.compute();
                if (this._pcRegister.get() < 0) {
                    this.stop();
                }
            }
        }
    }
    /*
     * stop the executing.
     */

    @Override
    public synchronized void go() throws InterruptedException {
        if (this._started && this._paused) {
            System.out.println("Continue");
            this._paused = false;
            notifyAll();
            this.onContinue(this);
        }
    }

    public void forceStop() {
        try {
            this.pause();
            this.stop();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //this.stop();
    }

    @Override
    public synchronized void stop() throws InterruptedException {
        if (this._started) {
            System.out.println("Stop");
            this._stopSingnal = true;
            this._pcRegister.put(-1);
            notifyAll();
            reset();
            this.onStop(this);
        }
    }
    /*
     * Set up the frequency for executing the program.
     */
    @Override
    public float getClock() {
        return this._frequency;
    }

    @Override
    public void setClock(float frequency) {
        this._frequency = frequency;
        this._debugger.onFrequencyChange(this._frequency, this);
    }
    /*
     * revise the value of pcRegister and use it to restart the paused program.
     */
    @Override
    public synchronized int getPcRegister() {
        return this._pcRegister.get();
    }

    @Override
    public synchronized void setPcRegister(int address) throws InterruptedException {
        if (this._paused) {
            this._pcRegister.put(address);
            notifyAll();
        }
    }
    /*
     * Get the operation code.
     */
    @Override
    public synchronized int getOpCode() {
        return this._opCodeRegister.get();
    }

    @Override
    public synchronized void setOpCode(int code) throws InterruptedException {
        if (this._paused) {
            this._opCodeRegister.put(code);
            notifyAll();
        }
    }

    @Override
    public boolean isRunning() {
        return this._started;
    }

    @Override
    public boolean isPause() {
        return this._paused;
    }

    @Override
    public boolean isRequireStop() {
        return this._stopSingnal;
    }
}


