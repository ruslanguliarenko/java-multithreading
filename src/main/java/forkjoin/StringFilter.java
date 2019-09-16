package forkjoin;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;

public class StringFilter extends RecursiveTask<List<String>> {
    private final int threshold;
    private final Predicate<String> filter;
    private final List<String> data;

    public StringFilter(List<String> data, Predicate<String> filter, int threshold) {
        this.filter = filter;
        this.data = data;
        this.threshold = threshold;
    }

    protected List<String> compute() {

        if (data.size() < threshold) {
            return filter();
        }

        int mid = 1 + data.size() >>> 1;

        StringFilter firstSubTask = new StringFilter(data.subList(0, mid), filter, threshold);
        StringFilter secondSubTask = new StringFilter(data.subList(mid, data.size()), filter, threshold);
        invokeAll(firstSubTask, secondSubTask);

        List<String> filteredData = new ArrayList<>();
        filteredData.addAll(firstSubTask.join());
        filteredData.addAll(secondSubTask.join());
        return filteredData;

    }

    private List<String> filter() {
        return data.stream()
                    .filter(filter)
                    .collect(toList());
    }
}