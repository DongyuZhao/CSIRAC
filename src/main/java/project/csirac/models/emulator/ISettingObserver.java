package project.csirac.models.emulator;

import project.csirac.viewmodels.emulator.SettingViewModel;

/**
 * Created by Dy.Zhao on 2016/1/14 0014.
 */
public interface ISettingObserver
{
    /**
     * Update current setting views
     *
     * @param sessionId
     *         the session id
     * @param model
     * the view model
     */
    void pushCurrentSettings(String sessionId, SettingViewModel model);
}
