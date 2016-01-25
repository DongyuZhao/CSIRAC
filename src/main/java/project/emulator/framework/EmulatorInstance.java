package project.emulator.framework;

import project.emulator.framework.cpu.controlunit.IControlUnit;
import project.emulator.framework.memory.IMemory;

/**
 * Created by Dy.Zhao on 2016/1/23 0023.
 */
public class EmulatorInstance
{
    private IControlUnit _controlUnit;

    private IMemory _instructionMemory;

    private IMemory _dataMemory;

    private boolean _dataUploaded;
    private boolean _programUploaded;

    public EmulatorInstance(IControlUnit controlUnit, IMemory _instructionMemory, IMemory _dataMemory)
    {
        this._controlUnit = controlUnit;
        this._instructionMemory = _instructionMemory;
        this._dataMemory = _dataMemory;
    }


    public void setClock(float frequency)
    {
        this._controlUnit.setClock(frequency);
    }

    public void setPcRegister(int address)
    {
        try
        {
            this._controlUnit.setPcRegister(address);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    public void start()
    {
        if (!this._controlUnit.isRunning())
        {
            this._controlUnit.start();
        }
    }

    public void loadData(String[] data)
    {
        if (!this._controlUnit.isRunning())
        {
            this._dataUploaded = this._dataMemory.load(data);
        }
    }

    public void loadProgram(String[] data)
    {
        if (!this._controlUnit.isRunning())
        {
            this._programUploaded = this._instructionMemory.load(data);
        }
    }

    public void pause() throws InterruptedException
    {
        if (this._controlUnit.isRunning())
        {
            this._controlUnit.pause();
        }
    }

    public void go() throws InterruptedException
    {
        if (this._controlUnit.isPause())
        {
            this._controlUnit.go();
        }
    }

    public void stop() throws InterruptedException
    {
        System.out.println("Stop Call");
        this._controlUnit.stop();
    }

    public void next() throws InterruptedException
    {
        if (this._controlUnit.isPause())
        {
            this._controlUnit.next(true);
        }
    }

    public boolean isReady()
    {
        return this._dataUploaded && this._programUploaded;
    }
}
