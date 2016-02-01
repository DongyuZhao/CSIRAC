package project.csirac.core.processor;

import project.emulator.framework.Bootstrap;

/**
 * Created by Dy.Zhao on 2016/1/27 0027.
 */
public class DataTranslator {
    public static int translateToNumber(int[] input) {
        if (input != null && input.length == Bootstrap.getInnerConfig().normalDataSectionCount()) {
            int result = 0;
            for (int i = 0; i < input.length; i++) {
                if (input[i] < 0) {
                    return Bootstrap.getInnerConfig().finishSignal();
                }
                result = result * Bootstrap.getInnerConfig().maximumPerSection() + input[i];
            }
            return result;
        }
        return 0;
    }

    public static int[] translateToData(int input) {
        int[] result = new int[Bootstrap.getInnerConfig().normalDataSectionCount()];
        if (input >= 0) {
            for (int i = 0; i < result.length; i++) {

            }
        }
        return result;
    }

    public boolean verifyNumber(int[] input) {
        for (int anInput : input) {
            if (anInput >= Bootstrap.getInnerConfig().maximumPerSection()) {
                return false;
            }
        }
        return true;
    }
}
