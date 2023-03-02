package co.com.quality.data.utilities;

import net.thucydides.core.steps.stepdata.CSVTestDataSource;
import net.thucydides.core.steps.stepdata.TestDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static co.com.quality.data.utilities.DataStructureUtilities.filterListOfMap;

public class CSVUtilities {

    private static final Logger LOGGER = LoggerFactory.getLogger(CSVUtilities.class);
    private static String defaultResourcePath= "src/test/resources/";
    private static final String CSV_FILE_EXTENSION = ".csv";

    private CSVUtilities() {
    }
    
    public static void setResourcePath(String resourcePath){
        defaultResourcePath = resourcePath;
    }

    private static Optional<TestDataSource> getDataFromCsvFileAsTestDataSource(String csvFileName, String folderWhereTheFileIs, char separator) {
        String fullPathCsvFile;
        if (folderWhereTheFileIs == null || Keys.EMPTY_STRING.equalsIgnoreCase(folderWhereTheFileIs)) {
            fullPathCsvFile = defaultResourcePath + csvFileName + CSV_FILE_EXTENSION;
        } else {
            fullPathCsvFile = defaultResourcePath + folderWhereTheFileIs + SystemPropertyUtilities.getFileSeparator() + csvFileName + CSV_FILE_EXTENSION;
        }
        try {
            return Optional.of(new CSVTestDataSource(fullPathCsvFile, separator));
        } catch (IOException ioException) {
            LOGGER.error("It was not possible to access the indicated file: {}.", csvFileName, ioException);
            return Optional.empty();
        }
    }

    public static List<Map<String, String>> getData(String csvFileName, String folderWhereTheFileIs, char separator) {
        TestDataSource testDataSource = getDataFromCsvFileAsTestDataSource(csvFileName, folderWhereTheFileIs, separator).orElse(null);
        return testDataSource != null ? testDataSource.getData() : Collections.emptyList();
    }

    public static List<Map<String, String>> getDataWithFilter(String csvFileName, String folderWhereTheFileIs, char separator, String filter, String filterColumnName) {
        Set<String> filters = new HashSet<>(Collections.singletonList(filter));
        return filterListOfMap(
                getData(csvFileName, folderWhereTheFileIs, separator),
                filters,
                filterColumnName
        );
    }

    public static List<Map<String, String>> getDataWithMultipleFilters(String csvFileName, String folderWhereTheFileIs, char separator, Set<String> filters, String filterColumnName) {

        List<Map<String, String>> dataFromCsvFile = getData(csvFileName, folderWhereTheFileIs, separator);
        if (filters == null || filterColumnName == null) return dataFromCsvFile;

        return dataFromCsvFile.stream()
                .filter(dataRowMap -> filters.contains(dataRowMap.get(filterColumnName)))
                .collect(Collectors.toList());
    }

    public static <T> List<T> getDataAsModel(String csvFileName, String folderWhereTheFileIs, char separator, Class<T> klazz) {
        TestDataSource testDataSource = getDataFromCsvFileAsTestDataSource(csvFileName, folderWhereTheFileIs, separator).orElse(null);
        return testDataSource != null ? testDataSource.getDataAsInstancesOf(klazz) : Collections.emptyList();
    }

    public static <T> List<T> getDataAsModelWithMultipleFilters(String csvFileName, String folderWhereTheFileIs, char separator, Class<T> klazz, Set<String> filters, String filterColumnName) {
        List<Map<String, String>> dataFromCsvFile = getData(csvFileName, folderWhereTheFileIs, separator);
        if (filters == null || filterColumnName == null)
            return KlazzUtilities.getDataAsInstancesOf(dataFromCsvFile, klazz);
        List<Map<String, String>> filteredData = filterListOfMap(dataFromCsvFile, filters, filterColumnName);
        return KlazzUtilities.getDataAsInstancesOf(filteredData, klazz);
    }

    public static boolean saveInformation(File file, String lineData) {
        try (FileWriter pw = new FileWriter(file, true)) {
            pw.append(lineData);
            pw.append(Keys.LINE_BREAK);
            pw.flush();
            return true;
        } catch (IOException exception) {
            LOGGER.error("Error while writing information: '{}'.", lineData, exception);
            return false;
        }
    }

}
