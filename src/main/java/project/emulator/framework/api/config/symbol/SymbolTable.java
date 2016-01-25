package project.emulator.framework.api.config.symbol;

import project.emulator.framework.Bootstrap;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Dy.Zhao on 2016/1/22 0022.
 */
public class SymbolTable implements ISymbolTranslator
{
    private Map<String, Integer> _symbolTable = new HashMap<>();

    @Override
    public void registerSymbol(String symbol, int code)
    {
        this._symbolTable.put(symbol, code);
    }

    @Override
    public int translateToCode(String symbol)
    {
        if (this._symbolTable.keySet().contains(symbol))
        {
            return this._symbolTable.get(symbol);
        }
        return Bootstrap.innerConfig.defaultCellContent();
    }

    @Override
    public String translateToSymbol(int code)
    {
        if (this._symbolTable.values().contains(code))
        {
            String result = "";
            for (Map.Entry<String, Integer> entry : this._symbolTable.entrySet())
            {
                int value = entry.getValue();
                if (value == code)
                {
                    result = result + entry.getKey() + "/";
                }
            }
            char[] rc = result.toCharArray();
            if (rc.length > 0)
            {
                if (rc[rc.length - 1] == '/')
                {
                    String newResult = "";
                    for (int i = 0; i < rc.length - 1; i++)
                    {
                        newResult += rc[i];
                    }
                    return  newResult;
                }
            }
            return result;
        }
        return null;
    }

    @Override
    public int[] translateInput(String input)
    {
        Pattern p = Bootstrap.innerConfig.inputFilterPattern();
        Matcher m = p.matcher(input);
        if (m.matches())
        {
            String[] split = input.split("\\s");
            int[] result = new int[Bootstrap.innerConfig.mainDataSectionCount()];
            for (int i = 0; i < split.length; i++)
            {
                if (!split[i].equals(""))
                {
                    result[i] = this.translateToCode(split[i]);
                }
            }
            return result;
        }
        return new int[0];
    }

    @Override
    public String translateOutput(int[] data)
    {
        String result = "";
        if (data.length == Bootstrap.innerConfig.mainDataSectionCount())
        {
            for (int i = 0; i < data.length; i++)
            {
                result += data[i];
            }
        }
        return result;
    }

    @Override
    public int[] trimData(int[] data)
    {
        if (data.length != Bootstrap.innerConfig.mainDataSectionCount())
        {
            int[] result = new int[Bootstrap.innerConfig.mainDataSectionCount()];
            for (int i = 0; i < data.length; i++)
            {
                if (Bootstrap.innerConfig.alignLeft())
                {
                    result[i] = data[i];
                }
                else
                {
                    result[Bootstrap.innerConfig.mainDataSectionCount() - Bootstrap.innerConfig.simplifiedDataSectionCount() + i] = data[i];
                }
            }
            return result;
        }
        return data;
    }
}
