package project.csirac.models.emulator;

import java.util.List;

import project.csirac.models.emulator.model.Instruction;

/**
 * Created by Dy.Zhao on 2016/1/4 0004.
 */
public interface IComputeUnit {

	String computeResult(Instruction decodeInstruction, List<Integer> allData);
}
