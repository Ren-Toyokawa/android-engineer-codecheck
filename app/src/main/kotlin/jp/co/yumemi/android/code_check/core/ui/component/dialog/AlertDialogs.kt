package jp.co.yumemi.android.code_check.core.ui.component.dialog

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.core.designsystem.preview.MultiThemePreviews
import jp.co.yumemi.android.code_check.core.designsystem.theme.CodeCheckAppTheme

@Composable
fun NetworkErrorDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit = {},
    onClickConfirm: () -> Unit = {},
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Text(
                text = stringResource(R.string.network_error_dialog_title),
                style = MaterialTheme.typography.headlineSmall,
            )
        },
        text = {
            Text(
                text = stringResource(R.string.network_error_dialog_message),
                style = MaterialTheme.typography.bodyLarge,
            )
        },
        confirmButton = {
            TextButton(onClickConfirm) {
                Text(
                    text = stringResource(R.string.error_dialog_positive_button),
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        }
    )
}

@Composable
fun CantFetchRepositoryInfoDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit = {},
    onClickConfirm: () -> Unit = {},
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Text(
                text = stringResource(R.string.error_dialog_title),
                style = MaterialTheme.typography.headlineSmall,
            )
        },
        text = {
            Text(
                text = stringResource(R.string.error_dialog_message),
                style = MaterialTheme.typography.bodyLarge,
            )
        },
        confirmButton = {
            TextButton(onClick = onClickConfirm){
                Text(
                    text = stringResource(R.string.error_dialog_positive_button),
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        }
    )
}

@MultiThemePreviews
@Composable
fun NetworkErrorDialogPreview() {
    CodeCheckAppTheme {
        NetworkErrorDialog()
    }
}

@MultiThemePreviews
@Composable
fun CantFetchRepositoryInfoDialogPreview() {
    CodeCheckAppTheme {
        CantFetchRepositoryInfoDialog()
    }
}
