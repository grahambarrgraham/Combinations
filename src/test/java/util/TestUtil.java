package util;
import java.util.AbstractList;
import java.util.List;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;


public final class TestUtil {

    private TestUtil() {
        //
    }
    
    public static Matcher<List<List<Integer>>> matches(final int[][] array) {
        return new BaseMatcher<List<List<Integer>>>() {

            @Override
            public boolean matches(Object arg0) {
                @SuppressWarnings("unchecked")
                List<List<Integer>> actual = (List<List<Integer>>) arg0;
                List<List<Integer>> expected = asList(array);

                if (!(actual.size() == expected.size())) {
                    return false;
                }

                for (int i = 0; i < actual.size(); i++) {
                    if (!actual.get(i).equals(expected.get(i))) {
                        return false;
                    }
                }
                return true;
            }

            @Override
            public void describeTo(Description arg0) {
                // TODO Auto-generated method stub
            }

        };
    }

    public static List<Integer> asList(final int[] is) {
        return new AbstractList<Integer>() {
            public Integer get(int i) {
                return is[i];
            }

            public int size() {
                return is.length;
            }
        };
    }

    public static List<List<Integer>> asList(final int[][] is) {
        return new AbstractList<List<Integer>>() {
            public List<Integer> get(int i) {
                return asList(is[i]);
            }

            public int size() {
                return is.length;
            }
        };
    }

    
}
