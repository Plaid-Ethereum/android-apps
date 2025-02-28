package com.dashlane.storage.userdata.accessor

import com.dashlane.database.Database
import com.dashlane.database.MemorySummaryRepository
import com.dashlane.session.SessionManager
import com.dashlane.session.repository.UserDatabaseRepository
import com.dashlane.storage.userdata.accessor.filter.DataChangeHistoryFilter
import com.dashlane.storage.userdata.accessor.filter.vaultFilter
import com.dashlane.vault.model.VaultItem
import com.dashlane.vault.summary.SummaryObject
import com.dashlane.xml.domain.SyncObject
import dagger.Lazy
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataChangeHistoryQueryImpl @Inject constructor(
    private val sessionManager: SessionManager,
    private val userDataRepository: UserDatabaseRepository,
    private val vaultDataQuery: Lazy<VaultDataQuery>
) : DataChangeHistoryQuery {

    private val database: Database?
        get() = sessionManager.session?.let { userDataRepository.getRacletteDatabase(it) }

    private val memorySummaryRepository: MemorySummaryRepository?
        get() = database?.memorySummaryRepository

    @Suppress("UNCHECKED_CAST")
    override fun query(filter: DataChangeHistoryFilter): VaultItem<SyncObject.DataChangeHistory>? {
        val summary = memorySummaryRepository?.databaseDataChangeHistorySummary ?: return null
        val item: SummaryObject.DataChangeHistory = summary.data.find {
            it.objectId == filter.objectUid
        } ?: return null
        return vaultDataQuery.get().queryLegacy(vaultFilter { specificUid(item.id) }) as? VaultItem<SyncObject.DataChangeHistory>
    }
}
