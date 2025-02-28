package com.dashlane.storage.userdata.accessor.filter

import com.dashlane.storage.userdata.accessor.filter.space.SpaceFilter
import com.dashlane.teamspaces.manager.DataIdentifierSpaceCategorization
import com.dashlane.teamspaces.manager.TeamSpaceAccessor
import com.dashlane.teamspaces.ui.CurrentTeamSpaceUiFilter
import com.dashlane.util.inject.OptionalProvider
import com.dashlane.vault.summary.SummaryObject
import com.dashlane.vault.util.AuthentifiantPackageNameMatcher
import javax.inject.Inject

class FilterToPredicateSummary @Inject constructor(
    private val teamSpaceAccessorProvider: OptionalProvider<TeamSpaceAccessor>,
    private val currentTeamSpaceUiFilter: CurrentTeamSpaceUiFilter,
    private val authentifiantPackageNameMatcher: AuthentifiantPackageNameMatcher
) {

    fun toPredicate(filter: BaseFilter): (SummaryObject) -> Boolean {
        return { accept(filter, it) }
    }

    private fun accept(filter: BaseFilter, vaultItem: SummaryObject): Boolean {
        return hasCorrectSpace(filter, vaultItem) &&
            ((filter !is CredentialFilter) || acceptCredentialFilter(filter, vaultItem)) &&
            ((filter !is CollectionFilter) || acceptCollectionFilter(filter, vaultItem))
    }

    private fun hasCorrectSpace(filter: BaseFilter, vaultItem: SummaryObject): Boolean {
        val teamspaceAccessor = teamSpaceAccessorProvider.get()
            ?: return false 
        val spaceFilter = filter as? SpaceFilter
            ?: return true 
        val spaces = spaceFilter.getSpacesRestrictions(currentTeamSpaceUiFilter)
            ?: return true 
        return spaces.any {
            DataIdentifierSpaceCategorization(
                teamspaceAccessor,
                currentTeamSpaceUiFilter,
                it
            ).canBeDisplay(vaultItem)
        }
    }

    private fun acceptCredentialFilter(
        filter: CredentialFilter,
        syncObject: SummaryObject
    ): Boolean {
        return syncObject is SummaryObject.Authentifiant &&
            filter.email?.equals(
                syncObject.email,
                ignoreCase = true
            ) ?: true && 
            hasCorrectDomain(filter, syncObject) &&
            hasCorrectPackageName(filter, syncObject)
    }

    private fun acceptCollectionFilter(
        filter: CollectionFilter,
        summaryObject: SummaryObject
    ): Boolean {
        return summaryObject is SummaryObject.Collection &&
            filter.withVaultItem?.let { it in (summaryObject.vaultItems ?: emptyList()) } ?: true &&
            filter.withoutVaultItem?.let {
                it !in (summaryObject.vaultItems ?: emptyList())
            } ?: true &&
            filter.withVaultItemId?.let { filterItemId ->
                filterItemId in (summaryObject.vaultItems?.map { it.id } ?: emptyList())
            } ?: true &&
            filter.withoutVaultItemId?.let { filterItemId ->
                filterItemId !in (summaryObject.vaultItems?.map { it.id } ?: emptyList())
            } ?: true &&
            filter.name?.equals(summaryObject.name) ?: true
    }

    private fun hasCorrectDomain(
        filter: CredentialFilter,
        authentifiant: SummaryObject.Authentifiant
    ): Boolean =
        hasCorrectDomain(
            filter,
            authentifiant.url,
            authentifiant.userSelectedUrl,
            authentifiant.title,
            authentifiant.linkedServices?.associatedDomains?.map { it.domain }
        )

    private fun hasCorrectPackageName(
        filter: CredentialFilter,
        authentifiant: SummaryObject.Authentifiant
    ) =
        filter.packageName?.let {
            authentifiantPackageNameMatcher.matchPackageName(authentifiant, it)
        }
            ?: true 
}