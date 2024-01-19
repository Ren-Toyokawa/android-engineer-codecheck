package jp.co.yumemi.android.code_check.feature

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasImeAction
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performImeAction
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.text.input.ImeAction
import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import jp.co.yumemi.android.code_check.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class RepositorySearchFragmentTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    var composeTestRule = createAndroidComposeRule<MainActivity>()


    @Test
    fun search_scenario() {
        // 検索バーにテキストを入力
        composeTestRule
            .onNodeWithTag("SearchTextField")
            .assertIsDisplayed()
            .performTextInput("検索クエリ")

        // 検索ボタンをタップ
        composeTestRule.onNode(hasImeAction(ImeAction.Search)).performImeAction()

        // 検索結果が表示されることを確認
        composeTestRule.onNodeWithText("dummy1").assertIsDisplayed()

        // RecyclerViewの最初のアイテムをタップ
        composeTestRule.onNodeWithText("dummy1").performClick()

        // RepositoryInfoFragmentのViewが表示されることを確認
        composeTestRule.onNodeWithTag("RepositoryInfoScreen").assertIsDisplayed()

        composeTestRule.onNodeWithTag("IssueButton").performClick()

        composeTestRule.onNodeWithTag("RepositoryIssueScreen").assertIsDisplayed()
    }
}