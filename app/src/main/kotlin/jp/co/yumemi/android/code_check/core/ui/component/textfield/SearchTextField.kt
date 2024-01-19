package jp.co.yumemi.android.code_check.core.ui.component.textfield

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import androidx.compose.ui.unit.dp
import jp.co.yumemi.android.code_check.core.designsystem.theme.CodeCheckAppTheme

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SearchTextField(
    modifier: Modifier = Modifier,
    searchQuery: String = "",
    placeholder: String = "",
    onSearchQueryChanged: (String) -> Unit = {},
    onTapImeAction: (String) -> Unit = {},
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
){
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    BasicTextField(
        modifier = modifier
            .height(56.0.dp)
            .fillMaxWidth(1.0f)
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = MaterialTheme.shapes.small,
            )
            .border(
                width = 0.5.dp,
                color = MaterialTheme.colorScheme.outlineVariant,
                shape = MaterialTheme.shapes.small,
            )
            .focusRequester(focusRequester)
            .testTag("SearchTextField"),
        value = searchQuery,
        onValueChange = onSearchQueryChanged,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(
            onSearch = {
                keyboardController?.hide()
                onTapImeAction(searchQuery)
            }
        ),
        textStyle = TextStyle(
            color = MaterialTheme.colorScheme.onSurface,
        ),
        decorationBox = @Composable { innerTextField ->
            TextFieldDefaults.TextFieldDecorationBox(
                value = searchQuery,
                innerTextField = innerTextField,
                enabled = true,
                singleLine = true,
                visualTransformation = VisualTransformation.None,
                interactionSource = interactionSource,
                leadingIcon = {
                    Box(modifier = Modifier.offset(x = 4.dp)) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurface,
                        )
                    }
                },
                placeholder = {
                    if (searchQuery.isBlank()) {
                        Text(
                            text = placeholder,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        Box(modifier = Modifier.offset(x = (-4).dp)) {
                            IconButton(
                                onClick = {
                                    onSearchQueryChanged("")
                                },
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                )
                            }
                        }
                    }
                },
                contentPadding = TextFieldDefaults.textFieldWithLabelPadding(0.dp),
                container = {},
            )
        }
    )
}

private class QueryProvider : CollectionPreviewParameterProvider<String>(
    listOf(
        "",
        "android-coding-check"
    )
)


@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SearchTextFieldPreview(
    @PreviewParameter(QueryProvider::class) searchQuery: String,
) {
    CodeCheckAppTheme {
        SearchTextField(
            searchQuery = searchQuery,
            placeholder = "文字列を入力してください",
            onSearchQueryChanged = {},
            onTapImeAction = {},
        )
    }
}