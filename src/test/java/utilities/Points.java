package utilities;

import backend.helpers.Point;
import com.google.common.collect.ContiguousSet;
import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.Range;
import com.google.common.collect.Sets;

import java.util.Set;
import java.util.stream.Collectors;

public class Points {
    public static Set<Point> combinations(int min, int max) {
        return Sets.combinations(ContiguousSet.create(Range.closedOpen(min, max), DiscreteDomain.integers()), 2)
                .stream()
                .map(loc -> new Point((int) loc.toArray()[0], (int) loc.toArray()[1]))
                .collect(Collectors.toUnmodifiableSet());
    }
}
