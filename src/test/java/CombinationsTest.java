import static org.hamcrest.MatcherAssert.assertThat;
import static util.TestUtil.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

        List<List<Integer>> result = new Combinations(true).generateCombinations(2, Arrays.asList(1, 2, 3)).collect(
                Collectors.toList());

        System.err.println(result);

        assertThat(result, matches(new int[][] { { 1, 2 }, { 1, 3 }, { 2, 3 } }));
    }

    @Test
    public void testR3N3() {

        List<List<Integer>> result = new Combinations(true).generateCombinations(3, Arrays.asList(1, 2, 3)).collect(
                Collectors.toList());

        System.err.println(result);

        assertThat(result, matches(new int[][] { { 1, 2, 3 } }));
    }

    @Test
    public void testParallel() {

        long now = System.currentTimeMillis();

        int anyR = 5;
        List<Integer> anyN = Arrays.asList(1, 2, 5, 10, 20, 50, 100, 200, 500, 600, 700, 800);

        List<List<Integer>> result = new Combinations(false).generateCombinations(anyR, anyN).collect(
                Collectors.toList());

        long sequential = System.currentTimeMillis() - now;
        System.err.println("Resultset size : " + result.size() + " took " + sequential + "ms sequential");

        now = System.currentTimeMillis();

        result = new Combinations(true).generateCombinations(anyR, anyN).collect(Collectors.toList());

        long parallel = System.currentTimeMillis() - now;
        System.err.println("Resultset size : " + result.size() + " took " + parallel + "ms parallel");

        assertThat(parallel < sequential, Is.is(true));

    }

}
