import static org.hamcrest.MatcherAssert.assertThat;

import java.util.AbstractList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.core.Is;
import org.junit.Test;

public class CombinationsTest {

    @Test
    public void testR1N5() {

        List<List<Integer>> result = new Combinations(true).generateCombinations(1, Arrays.asList(1, 2, 3, 4, 5))
                .collect(Collectors.toList());

        System.err.println(result);
        
        assertThat(result, matches(new int[][] { { 1 }, { 2 }, { 3 }, { 4 }, { 5 } }));
    }

    @Test
    public void testR2N3() {

        List<List<Integer>> result = new Combinations(true).generateCombinations(2, Arrays.asList(1, 2, 3))
                .collect(Collectors.toList());

        System.err.println(result);
        
        assertThat(result, matches(new int[][] { { 1, 2 }, { 1, 3 }, { 2, 3 } }));
    }

    @Test
    public void testR3N3() {

        List<List<Integer>> result = new Combinations(true).generateCombinations(3, Arrays.asList(1, 2, 3))
                .collect(Collectors.toList());

        System.err.println(result);
        
        assertThat(result, matches(new int[][] { { 1, 2, 3 } }));
    }
    
    @Test
    public void testParallel() {

        long now = System.currentTimeMillis();

        int anyR = 5;
        List<Integer> anyN = Arrays.asList(1, 2, 5, 10, 20, 50, 100, 200, 500, 600, 700, 800);
        
        List<List<Integer>> result = new Combinations(false).generateCombinations(anyR,anyN).collect(Collectors.toList());

        long sequential = System.currentTimeMillis() - now;
        System.err.println("Resultset size : " + result.size() + " took " + sequential + "ms sequential");

        now = System.currentTimeMillis();

        result = new Combinations(true).generateCombinations(anyR,anyN).collect(Collectors.toList());

        long parallel = System.currentTimeMillis() - now;
        System.err.println("Resultset size : " + result.size() + " took " + parallel + "ms parallel");
        
        assertThat(parallel < sequential, Is.is(true));
        
    }
    
    private Matcher<List<List<Integer>>> matches(final int[][] array) {
        return new BaseMatcher<List<List<Integer>>>() {

            @Override
            public boolean matches(Object arg0) {
                @SuppressWarnings("unchecked")
                List<List<Integer>> actual = (List<List<Integer>>) arg0;
                List<List<Integer>> expected = asList(array);
                
                if (!(actual.size() == expected.size())) {
                    return false;
                }
                
                for(int i = 0; i < actual.size(); i++) {
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
    
    private List<Integer> asList(final int[] is)
    {
            return new AbstractList<Integer>() {
                    public Integer get(int i) { return is[i]; }
                    public int size() { return is.length; }
            };
    }
    
    private List<List<Integer>> asList(final int[][] is)
    {
            return new AbstractList<List<Integer>>() {
                    public List<Integer> get(int i) { return asList(is[i]); }
                    public int size() { return is.length; }
            };
    }
    
}
