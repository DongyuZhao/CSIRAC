package project.csirac.core.decoder;

import project.csirac.core.CsiracBootstrap;

/**
 * Created by Dy.Zhao on 2016/1/23 0023.
 */

public class AddressDecoder {
	/*
	 * according present instructions to judge the address of present operation.
	 * 
	 * @param opCode means the operation code which is 'K register' in the former version emulator.
	 * @param section0 means the 1-5 bits of the input data.
	 * @param section1 means the 6-10 bits of the input data.
	 * 
	 */
    static int determineAddress(int opCode, int section0, int section1) {
        int address = -1;
        if (opCode == CsiracBootstrap.symbolTranslator.translateToCode("M")) {
            address = section0 * CsiracBootstrap.innerConfig.cellPerUnit() + section1;
        } else if (opCode == CsiracBootstrap.symbolTranslator.translateToCode("D")) {
            address = section1 % 16;
        } else if (opCode == CsiracBootstrap.symbolTranslator.translateToCode("A") || opCode == CsiracBootstrap.symbolTranslator.translateToCode("B") || opCode == CsiracBootstrap.symbolTranslator.translateToCode("C")) {
            address = opCode;
        }
        // TODO:: Add for further instructions
        return address;
    }
}
