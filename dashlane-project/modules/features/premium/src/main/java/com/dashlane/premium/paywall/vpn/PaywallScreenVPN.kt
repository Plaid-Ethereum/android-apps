package com.dashlane.premium.paywall.vpn

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dashlane.design.component.DashlaneLogoLockup
import com.dashlane.design.component.Icon
import com.dashlane.design.iconography.IconTokens
import com.dashlane.design.theme.DashlaneTheme
import com.dashlane.design.theme.tooling.DashlanePreview
import com.dashlane.hermes.generated.definitions.AnyPage
import com.dashlane.premium.R
import com.dashlane.premium.offer.common.model.OfferType
import com.dashlane.premium.paywall.common.PaywallIntroScreen
import com.dashlane.premium.paywall.common.PaywallIntroState
import com.dashlane.premium.paywall.common.PaywallViewModel
import com.dashlane.ui.activities.intro.DescriptionItem
import com.dashlane.ui.activities.intro.LinkItem

@Composable
fun PaywallScreenVPN(
    modifier: Modifier = Modifier,
    intro: PaywallIntroState,
    navigateUp: () -> Unit,
    navigateToOffer: () -> Unit,
    onCancelClick: () -> Unit,
    onClickLink: (LinkItem) -> Unit
) {
    PaywallIntroScreen(
        modifier = modifier,
        intro = intro,
        navigateUp = navigateUp,
        onNegativeButtonClicked = onCancelClick,
        onPositiveButtonClicked = navigateToOffer,
        onClickLink = onClickLink
    ) {
        val portrait = LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT
        if (portrait) {
            IllustrationVPN()
        } else {
            IllustrationVPNLandScape()
        }
    }
}

@Composable
private fun IllustrationVPN() {
    Column(
        modifier = Modifier.wrapContentSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DashlaneLogoLockup(height = 40.dp)
        Icon(
            modifier = Modifier
                .padding(8.dp)
                .size(16.dp),
            token = IconTokens.actionCloseOutlined,
            contentDescription = null,
            tint = DashlaneTheme.colors.textNeutralStandard
        )
        Image(
            painter = painterResource(id = R.drawable.logo_hotspot_shield_seek),
            contentDescription = null
        )
    }
}

@Composable
private fun IllustrationVPNLandScape() {
    Row(
        modifier = Modifier.wrapContentSize(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        DashlaneLogoLockup(height = 40.dp)
        Icon(
            modifier = Modifier
                .padding(16.dp)
                .size(16.dp),
            token = IconTokens.actionCloseOutlined,
            contentDescription = null,
            tint = DashlaneTheme.colors.textNeutralStandard
        )
        Image(
            painter = painterResource(id = R.drawable.logo_hotspot_shield_seek),
            contentDescription = null
        )
    }
}

@Composable
@Preview
@Preview(device = Devices.TABLET)
private fun PreviewPaywallScreenVPN() {
    DashlanePreview {
        PaywallScreenVPN(
            intro = PaywallIntroState(
                title = R.string.paywall_intro_vpn_third_party_title_in_trial,
                descriptionList = listOf(
                    DescriptionItem(
                        imageIconToken = IconTokens.featureVpnOutlined,
                        titleResId = R.string.paywall_intro_vpn_third_party_message_1
                    ),
                    DescriptionItem(
                        imageIconToken = IconTokens.healthPositiveOutlined,
                        titleResId = R.string.paywall_intro_vpn_third_party_message_2
                    ),
                    DescriptionItem(
                        imageIconToken = IconTokens.webOutlined,
                        titleResId = R.string.paywall_intro_vpn_third_party_message_3
                    )
                ),
                linkList = listOf(
                    LinkItem.ExternalLinkItem(
                        R.string.paywall_intro_vpn_learn_more_cta,
                        PaywallViewModel.HOTSPOT_SHIELD_URL
                    )
                ),
                page = AnyPage.PAYWALL_VPN,
                goToOfferCTA = R.string.paywall_intro_buy_premium_cta,
                closeCTA = R.string.paywall_intro_close_cta,
                destinationOfferType = OfferType.PREMIUM,
                ctaListForLogs = emptyList()
            ),
            navigateUp = { },
            navigateToOffer = { },
            onCancelClick = { },
            onClickLink = { }
        )
    }
}