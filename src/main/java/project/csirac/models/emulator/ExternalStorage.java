package project.csirac.models.emulator;

import java.util.HashMap;
import java.util.Map;

import project.csirac.helper.ArrayHelper;

/**
 * Created by Dy.Zhao on 2016/1/14 0014.
 */
public class ExternalStorage implements IExternalStorage {
	private Map<String, String[]> _programPool = new HashMap<>();

	/**
	 * Save the program of the session
	 *
	 * @param sessionId
	 *            the session id
	 * @param program
	 */
	@Override
	public void saveProgram(String sessionId, String[] program) {
		this._programPool.put(sessionId, program);
	}

	/**
	 * Load the program of the session
	 *
	 * @param sessionId
	 *            the session id;
	 * @param start
	 *            the start address of the segment
	 * @param length
	 *            the length of the segment
	 *
	 * @return the program;
	 */
	@Override
	public String[] loadProgram(String sessionId, int start, int length) {
		if (!this._sessionExists(sessionId)) {
			throw new IllegalArgumentException("Session Not Exists");
		}
		return ArrayHelper.getSubArray(this._programPool.get(sessionId), start, length);
	}

	private boolean _sessionExists(String sessionId) {
		return this._programPool.containsKey(sessionId);
	}
}
