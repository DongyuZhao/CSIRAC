package project.emulator.framework.api.config.symbol;

import project.emulator.framework.Bootstrap;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Dy.Zhao on 2016/1/22 0022.
 */
public class SymbolTable implements ISymbolTranslator {
    private Map<String, Integer> _symbolTable = new HashMap<>();
    /*
     * register a mapping between a pair of symbol and code.
     */

    @Override
    public void registerSymbol(String symbol, int code) {
        this._symbolTable.put(symbol, code);
    }
    /*
     * Translate the particular symbol to particular code.
     */
    @Override
    public int translateToCode(String symbol) {
        if (this._symbolTable.keySet().contains(symbol)) {
            return this._symbolTable.get(symbol);
        }
        return Bootstrap.innerConfig.defaultCellContent();
    }
    
    /*
     * Translate the particular code to particular symbol.
     */
    @Override
    public String translateToSymbol(int code) {
        if (this._symbolTable.values().contains(code)) {
            String result = "";
            for (Map.Entry<String, Integer> entry : this._symbolTable.entrySet()) {
                int value = entry.getValue();
                if (value == code) {
                    result = result.concat(entry.getKey()).concat("/");
                }
            }
            char[] rc = result.toCharArray();
            if (rc.length > 0) {
                if (rc[rc.length - 1] == '/') {
                    String newResult = "";
                    for (int i = 0; i < rc.length - 1; i++) {
                        newResult += rc[i];
                    }
                    return newResult;
                }
            }
            return result;
        }
        return null;
    }
    /*
     * Translate string input to code.
     */
    @Override
    public int[] translateInput(String input) {
        Pattern p = Bootstrap.innerConfig.inputFilterPattern();
        Matcher m = p.matcher(input);
        if (m.matches()) {
            String[] split = input.split("[\\s]*[,]*\\s|[\\s]*,[\\s]*");
            int[] result = new int[Bootstrap.innerConfig.mainDataSectionCount()];
            int j = 0;
            for (String aSplit : split) {
                if (!aSplit.equals("")) {
                    result[j] = this.translateToCode(aSplit);
                    j++;
                }
            }
            return result;
        }
        return new int[0];
    }
    /*
     * Translate output to string output.
     */

    @Override
    public String translateOutput(int[] data) {
        String result = "";
        if (data.length == Bootstrap.innerConfig.mainDataSectionCount()) {
            for (int aData : data) {
                result.concat("" + aData);
            }
        }
        return result;
    }
    /*
     * Trim the data according CPU inner length.
     */
    @Override
    public int[] trimData(int[] data) {
        if (data.length != Bootstrap.innerConfig.mainDataSectionCount()) {
            int[] result = new int[Bootstrap.innerConfig.mainDataSectionCount()];
            for (int i = 0; i < data.length; i++) {
                if (Bootstrap.innerConfig.alignLeft()) {
                    if (i < result.length) {
                        result[i] = data[i];
                    }
                } else {
                    int index = Bootstrap.innerConfig.mainDataSectionCount() - data.length + i;
                    if (index >= 0) {
                        result[index] = data[i];
                    }
                }
            }
            return result;
        }
        return data;
    }
}
