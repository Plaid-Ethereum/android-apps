package com.dashlane.ui.dialogs.fragment

import com.dashlane.preference.ConstantsPrefs
import com.dashlane.preference.PreferencesManager
import com.dashlane.session.SessionManager
import com.dashlane.ui.dialogs.fragment.TeamRevokedDialogDisplayer.Companion.ARG_SPACE_REVOKED_ID
import com.dashlane.ui.dialogs.fragments.NotificationDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import java.time.Clock
import java.time.Duration
import java.time.Instant
import javax.inject.Inject

@AndroidEntryPoint
class SpaceRevokedDialog : NotificationDialogFragment() {

    @Inject
    lateinit var clock: Clock

    @Inject
    lateinit var sessionManager: SessionManager

    @Inject
    lateinit var preferencesManager: PreferencesManager

    private var startShown: Instant = Instant.EPOCH

    override fun onResume() {
        super.onResume()
        startShown = clock.instant()
    }

    override fun onPause() {
        super.onPause()
        markAsRead()
    }

    private fun markAsRead() {
        if (startShown == Instant.EPOCH || Duration.between(startShown, clock.instant()) < Duration.ofMillis(500)) {
            
            return
        }
        val preferences = preferencesManager[sessionManager.session?.username]
        val spaceId = preferences.getString(ConstantsPrefs.NEED_POPUP_SPACE_REVOKED_FOR) ?: return
        if (spaceId == requireArguments().getString(ARG_SPACE_REVOKED_ID)) {
            
            preferences.remove(ConstantsPrefs.NEED_POPUP_SPACE_REVOKED_FOR)
        }
    }
}
