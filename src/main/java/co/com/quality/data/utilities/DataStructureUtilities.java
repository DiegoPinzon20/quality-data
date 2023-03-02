package co.com.quality.data.utilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static co.com.quality.data.utilities.GeneralUtility.getTypeClass;

public class DataStructureUtilities {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataStructureUtilities.class);

    private DataStructureUtilities() {
    }

    public static <T, X> List<T> getMapKeys(Map<T, X> map) {
        return map != null ? new ArrayList<>(map.keySet()) : Collections.emptyList();
    }

    public static <T, X> void printMapKeysAndValues(Map<T, X> map) {
        if (map != null)
            map.forEach((k, v) -> LOGGER.info("Key ({}): '{}' -> Value ({}): '{}'", getTypeClass(k), k, getTypeClass(v), v));
    }

    public static <T> void printListValues(List<T> list) {
        if (list != null) list.forEach(x -> LOGGER.info("List Value ({}): '{}'", getTypeClass(x), x));
    }

    public static <T> void printArray(T[] array) {
        if (array != null)
            Arrays.stream(array).forEach(t -> LOGGER.info("Array Value ({}): {}", getTypeClass(t), t));
    }

    public static <T> List<Map<T, T>> filterListOfMap(List<Map<T, T>> mapList, Set<T> filters, T filterKeyMap) {
        if (mapList == null) {
            return Collections.emptyList();
        } else if (filters == null || filterKeyMap == null) {
            return mapList;
        } else {
            return mapList.stream()
                    .filter(dataRowMap -> filters.contains(dataRowMap.get(filterKeyMap)))
                    .collect(Collectors.toList());
        }
    }

}
