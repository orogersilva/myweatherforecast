package com.orogersilva.myweatherforecast.ui.dialog

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.orogersilva.myweatherforecast.ui.R

@Composable
fun NoConnectivityDialog(
    dismissLabel: String,
    onDismiss: () -> Unit,
    confirmLabel: String,
    onConfirm: () -> Unit
) {
    GenericDialog(
        title = stringResource(id = R.string.no_internet_dialog_title),
        description = stringResource(id = R.string.no_internet_dialog_description),
        dismissLabel = dismissLabel,
        onDismiss = onDismiss,
        confirmLabel = confirmLabel,
        onConfirm = onConfirm
    )
}

@Preview
@Composable
private fun NoConnectivityDialogPreview() {
    NoConnectivityDialog(
        "Cancel",
        onDismiss = { },
        confirmLabel = "Try again",
        onConfirm = { }
    )
}
