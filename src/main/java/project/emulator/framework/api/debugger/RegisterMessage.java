package project.emulator.framework.api.debugger;

import project.emulator.framework.Bootstrap;

/**
 * Created by Dy.Zhao on 2016/1/22 0022.
 */
public class RegisterMessage {
    protected String _address;

    protected String _newValue = "";

    public RegisterMessage(int address, int[] data) {
        this._address = Bootstrap.symbolTranslator.translateToSymbol(address);
        int[] trimmedData = Bootstrap.symbolTranslator.trimData(data);
        for (int i = 0; i < trimmedData.length; i++) {
            this._newValue += trimmedData[i];
            if (i != trimmedData.length - 1) {
                this._newValue += ",\t";
            }
        }
    }

    public RegisterMessage(String address, int[] data) {
        this._address = address;
        int[] trimmedData = Bootstrap.symbolTranslator.trimData(data);
        for (int i = 0; i < trimmedData.length; i++) {
            this._newValue += trimmedData[i];
            if (i != trimmedData.length - 1) {
                this._newValue += ",\t";
            }
        }
    }

    public RegisterMessage(String address, String data) {
        this._address = address;
        this._newValue = data;
    }

    public String getAddress() {
        return _address;
    }

    public void setAddress(String _address) {
        this._address = _address;
    }

    public String getNewValue() {
        return _newValue;
    }

    public void setNewValue(String _newValue) {
        this._newValue = _newValue;
    }
}
