package project.emulator.framework.api.debugger;

import project.emulator.framework.Bootstrap;

/**
 * Created by Dy.Zhao on 2016/1/22 0022.
 */
public class MemoryMessage
{
    protected int _unitAddress;

    protected int _cellAddress;

    protected String _newValue = "";


    public MemoryMessage(int unitAddress, int cellAddress, int[] data)
    {
        int[] trimmedData = Bootstrap.symbolTranslator.trimData(data);
        this._unitAddress = unitAddress;
        this._cellAddress = cellAddress;
        for (int i = 0; i < trimmedData.length; i++)
        {
            this._newValue += trimmedData[i];
            if (i != trimmedData.length - 1)
            {
                this._newValue += ",\t";
            }
        }
    }

    public int getGroupAddress()
    {
        return _unitAddress;
    }

    public void setGroupAddress(int _groupAddress)
    {
        this._unitAddress = _groupAddress;
    }

    public int getCellAddress()
    {
        return _cellAddress;
    }

    public void setCellAddress(int _cellAddress)
    {
        this._cellAddress = _cellAddress;
    }

    public String getNewValue()
    {
        return _newValue;
    }

    public void setNewValue(String _newValue)
    {
        this._newValue = _newValue;
    }
}
