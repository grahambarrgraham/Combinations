import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;
import static util.TestUtil.asList;

import java.util.List;

import org.junit.Test;

public class getFirstTest {

    @Test
    public void testFindFirst() {

        List<List<Integer>> l = asList(new int[][] { { 1 }, { 2 }, { 3, 4 }, { 4, 5 }, { 5 } });

        System.err
                .println("This demonstrates that the stream does not need to traverse the"
                        + " whole list, even though filter precedes getFirst in the stream declaration below");
        
        List<Integer> list = 
                l.stream()
                .peek(a -> System.err.println("pre filter " + a + "  " + Thread.currentThread().getName()))
                .filter(a -> a.size() == 2)
                .peek(a -> System.err.println("post filter " + a + "  " + Thread.currentThread().getName()))
                .findFirst().get();
        
        assertThat(list, hasItems(3,4));
    }

    @Test
    public void testFindFirstParallel() {

        List<List<Integer>> l = asList(new int[][] { { 1 }, { 2 }, { 3, 4 }, { 4, 5 }, { 5 } });

        System.err
        .println("This demonstrates that the stream definition still guarantees the correct answer, if "
                + " when it is run in parallel");
        
        List<Integer> list = 
                l.stream()
                .parallel()
                .peek(a -> System.err.println("pre filter " + a + "  " + Thread.currentThread().getName()))
                .filter(a -> a.size() == 2)
                .peek(a -> System.err.println("post filter " + a + "  " + Thread.currentThread().getName()))
                .findFirst().get();
        
        assertThat(list, hasItems(3,4));
    }
}
