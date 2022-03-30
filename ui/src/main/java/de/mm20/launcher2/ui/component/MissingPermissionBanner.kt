package de.mm20.launcher2.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import de.mm20.launcher2.ui.R

@Composable
fun MissingPermissionBanner(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    secondaryAction: @Composable () -> Unit = {}
) {
    Banner(
        modifier = modifier,
        text = text,
        icon = Icons.Rounded.Lock,
        primaryAction = {
            TextButton(
                modifier = Modifier.padding(start = 8.dp),
                onClick = onClick
            ) {
                Text(
                    stringResource(R.string.grant_permission),
                )
            }
        },
        secondaryAction = secondaryAction
    )
}