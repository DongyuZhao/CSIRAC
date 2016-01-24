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
public class ControlUnit extends CpuMonitorMessageSender implements IControlUnit
{
    private Thread _workThread;

    private IProcessor _processorSocket;

    private IPcRegister _pcRegister;

    private IOpCodeRegister _opCodeRegister;

    private float _frequency = 1;

    private boolean _paused = false;

    private boolean _started = false;

    private boolean _locked = false;

    private boolean _stopSingnal = false;

    public ControlUnit(String id, IProcessor processorSocket, IPcRegister pcRegister, IOpCodeRegister opCodeRegister)
    {
        super(id);
        this._processorSocket = processorSocket;
        this._pcRegister = pcRegister;
        this._opCodeRegister = opCodeRegister;
        this._workThread = new Thread(new ControlThread(this));
    }

    public static IControlUnit createInstance(String id, IProcessor processor, IPcRegister pcRegister, IOpCodeRegister opCodeRegister, IMonitor monitor, IDebugger debugger)
    {
        ControlUnit controlUnit = new ControlUnit(id, processor, pcRegister, opCodeRegister);
        controlUnit.attachMonitor(monitor);
        controlUnit.attachDebugger(debugger);
        return  controlUnit;
    }

    private synchronized void lock() throws InterruptedException
    {
        while(_locked)
        {
            wait();
        }
        this._locked = true;
    }

    private synchronized void freeLock()
    {
        this._locked = false;
        notifyAll();
    }

    private void reset()
    {
        this._started = false;
        this._paused = false;
        this._pcRegister.put(0);
        this._opCodeRegister.put(0);
    }

    @Override
    public void start()
    {
        try
        {
            this.lock();
            if (!this._started)
            {
                this._started = true;
                this._paused = false;
                this._stopSingnal = false;
                this.onStart();
                freeLock();
                this._workThread.start();
                System.out.println("Start");
            }
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        //this._workThread.run();
    }

    void onStart()
    {
        this.onStart(this);
    }

    @Override
    public void pause() throws InterruptedException
    {
        lock();
        if (this._started && !this._paused)
        {
            this._paused = true;
            this.onPause(this);
            System.out.println("Pause");
        }
        freeLock();
    }

    @Override
    public void next() throws InterruptedException
    {
        this.next(false);
    }

    @Override
    public void next(boolean ignorePause) throws InterruptedException
    {
        if (this._started && (ignorePause || !this._paused))
        {
            System.out.println("Next");
            int instructionPointer = this._pcRegister.get();
            if (instructionPointer < Bootstrap.innerConfig.unitCount()*Bootstrap.innerConfig.cellPerUnit() && instructionPointer >= 0)
            {
                this._processorSocket.compute();
                if (this._pcRegister.get() < 0)
                {
                    this.stop();
                }
            }
        }
    }

    @Override
    public void go() throws InterruptedException
    {
        lock();
        if (this._started && this._paused)
        {
            System.out.println("Continue");
            this._paused = false;
            this.onContinue(this);
        }
        freeLock();
    }

    @Override
    public void stop() throws InterruptedException
    {
        lock();
        if (this._started)
        {
            System.out.println("Stop");
            this._pcRegister.put(-1);
            this._stopSingnal = true;
            reset();
            this.onStop(this);
        }
        freeLock();
    }

    @Override
    public float getClock()
    {
        return this._frequency;
    }

    @Override
    public void setClock(float frequency)
    {
        this._frequency = frequency;
        this._debugger.onFrequencyChange(this._frequency, this);
    }

    @Override
    public int getPcRegister()
    {
        return this._pcRegister.get();
    }

    @Override
    public void setPcRegister(int address) throws InterruptedException
    {
        lock();
        if (this._paused)
        {
            this._pcRegister.put(address);
        }
        freeLock();
    }

    @Override
    public int getOpCode()
    {
        return this._opCodeRegister.get();
    }

    @Override
    public void setOpCode(int code) throws InterruptedException
    {
        lock();
        if (this._paused)
        {
            this._opCodeRegister.put(code);
        }
        freeLock();
    }

    @Override
    public boolean isRunning()
    {
        return this._started;
    }

    @Override
    public boolean isPause()
    {
        return this._paused;
    }

    @Override
    public boolean isRequireStop()
    {
        return this._stopSingnal;
    }
}


