package kate0013.cst2335.torunse;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    /**
     * Test case to check the main activity
     */
    @Test
    public void mainActivityTest() {
        ViewInteraction appCompatEditText = onView(withId(R.id.passWordField));
        appCompatEditText.perform(replaceText("12345"), closeSoftKeyboard());

        ViewInteraction materialButton = onView(withId(R.id.loginButton));
        materialButton.perform(click());

        ViewInteraction textView = onView(withId(R.id.passWordTextView));
        textView.check(matches(withText("You shall not pass!")));
    }

    /**
     * Test case to test the checkPasswordComplexity function for no Lowercase text
     */
    @Test
    public void testCheckPasswordComplexityMissingLowercase() {
        // find the view
        ViewInteraction appCompatEditText = onView(withId(R.id.passWordField));
        // type in password
        appCompatEditText.perform(replaceText("TEST#18"));
        // find the button
        ViewInteraction materialButton = onView(withId(R.id.loginButton));
        // click the button
        materialButton.perform(click());
        //find the text view
        ViewInteraction textView = onView(withId(R.id.passWordTextView));
        // check the text
        textView.check(matches(withText("You shall not pass!")));
    }

    /**
     * Test case to test the checkPasswordComplexity function against Lowercase text
     */
    @Test
    public void testCheckPasswordComplexityMissingUppercase() {
        ViewInteraction appCompatEditText = onView(withId(R.id.passWordField));
        appCompatEditText.perform(replaceText("test#18"));
        ViewInteraction materialButton = onView(withId(R.id.loginButton));
        materialButton.perform(click());
        ViewInteraction textView = onView(withId(R.id.passWordTextView));
        textView.check(matches(withText("You shall not pass!")));
    }

    /**
     * This test case will test the checkPasswordComplexity,
     * it will test the non digit value
     */
    @Test
    public void testCheckPasswordComplexityMissingDigit() {
        ViewInteraction appCompatEditText = onView(withId(R.id.passWordField));
        appCompatEditText.perform(replaceText("Test#"));
        ViewInteraction materialButton = onView(withId(R.id.loginButton));
        materialButton.perform(click());
        ViewInteraction textView = onView(withId(R.id.passWordTextView));
        textView.check(matches(withText("You shall not pass!")));
    }

    /**
     * This test case will test the checkPasswordComplexity,
     * it will test the non special characters value
     */
    @Test
    public void testCheckPasswordComplexityMissingSpecialChar() {
        ViewInteraction appCompatEditText = onView(withId(R.id.passWordField));
        appCompatEditText.perform(replaceText("Test18"));
        ViewInteraction materialButton = onView(withId(R.id.loginButton));
        materialButton.perform(click());
        ViewInteraction textView = onView(withId(R.id.passWordTextView));
        textView.check(matches(withText("You shall not pass!")));
    }

    /**
     * Test case to test that the entered password follows all the requirements
     */
    @Test
    public void testCheckPasswordAllRequirements() {
        ViewInteraction appCompatEditText = onView(withId(R.id.passWordField));
        appCompatEditText.perform(replaceText("Test18#"));
        ViewInteraction materialButton = onView(withId(R.id.loginButton));
        materialButton.perform(click());
        ViewInteraction textView = onView(withId(R.id.passWordTextView));
        textView.check(matches(withText("Your password is complex enough")));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}