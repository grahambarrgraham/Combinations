import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 
 * Simple demonstration of Java-8 functional techniques for generating all r
 * sized
 * combinations of a list of n numbers (no repeats).
 * 
 * It's not an optimised or especially readable.
 * 
 * However, it does clearly demonstrate how the "parallel" stream mechanism can
 * be used to make a function run <em>much</em> faster (orders of magnitude),
 * with the just the addition of one statement : .parallel()
 *
 * TODO - optimise
 * TODO - generalise to support permutations
 * TODO - the sort, union and combine methods are clunky, can these be written
 * in a more functional/fluent way (without resorting to a 3rd party collections
 * API?) 
 * 
 * @author graham
 *
 */
public final class Combinations {

    private boolean parallel = true;

    public Combinations(boolean parallelMapReduce) {
        parallel = parallelMapReduce;
    }

    /**
     * 
     * @param r
     *            - the size of the combination groups
     * @param n
     *            - the numbers
     * @return a stream of lists of size r, which are the sorted list of all r
     *         sized combinations of n
     */
    public Stream<List<Integer>> generateCombinations(int r, List<Integer> n) {

        if (r == 1) {
            return n.stream().map(u -> Arrays.asList(u));
        }

        return generateCombinations1(r - 1, n).map(u -> combine(u, n)).reduce((t, u) -> union(t, u)).get().stream()
                .map(u -> sort(u)).distinct();

    }

    /*
     * This method exists just to demonstrate the use of .parallel() - it would
     * usually be inline
     */
    private Stream<List<Integer>> generateCombinations1(int size, List<Integer> values) {
        return parallel ? generateCombinations(size, values).parallel() : generateCombinations(size, values);
    }

    private static List<Integer> sort(List<Integer> u) {
        Collections.sort(u);
        return u;
    }

    private static List<List<Integer>> union(List<List<Integer>> t, List<List<Integer>> u) {
        ArrayList<List<Integer>> arrayList = new ArrayList<List<Integer>>();
        arrayList.addAll(t);
        arrayList.addAll(u);
        return arrayList;
    }

    private static List<List<Integer>> combine(List<Integer> u, List<Integer> values) {
        return values.stream().filter((v) -> !u.contains(v)).map(v -> combine(u, v)).collect(Collectors.toList());
    }

    private static List<Integer> combine(List<Integer> u, Integer integer) {
        ArrayList<Integer> arrayList = new ArrayList<Integer>(u);
        arrayList.add(integer);
        return arrayList;
    }

}
