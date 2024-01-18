package jp.co.yumemi.android.code_check.feature

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.pressImeActionButton
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import jp.co.yumemi.android.code_check.MainActivity
import jp.co.yumemi.android.code_check.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class RepositorySearchFragmentTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    var activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun search_scenario() {
        // 検索バーにテキストを入力
        onView(withId(R.id.searchInputText))
            .perform(
                typeText("Android")
            )

        // 検索ボタンをタップ
        onView(withId(R.id.searchInputText)).perform(pressImeActionButton())

        // RecyclerViewのアイテムが表示されることを確認
        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()))

        // RecyclerViewの最初のアイテムをタップ
        onView(withId(R.id.recyclerView))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0, click()
                )
            )

        // RepositoryInfoFragmentのViewが表示されることを確認
        onView(withId(R.id.ownerIconView)).check(matches(isDisplayed()))
        onView(withId(R.id.nameView)).check(matches(isDisplayed()))
        onView(withId(R.id.languageView)).check(matches(isDisplayed()))
        onView(withId(R.id.starsView)).check(matches(isDisplayed()))
        onView(withId(R.id.watchersView)).check(matches(isDisplayed()))
        onView(withId(R.id.forksView)).check(matches(isDisplayed()))
        onView(withId(R.id.openIssuesView)).check(matches(isDisplayed()))
    }
}