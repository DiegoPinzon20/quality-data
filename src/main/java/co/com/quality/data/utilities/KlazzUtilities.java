package co.com.quality.data.utilities;

import net.thucydides.core.csv.FailedToInitializeTestData;
import net.thucydides.core.csv.FieldName;
import net.thucydides.core.csv.InstanceBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class KlazzUtilities {

    private KlazzUtilities() {
    }

    public static <T> List<T> getDataAsInstancesOf(List<Map<String, String>> data, final Class<T> clazz, final Object... constructorArgs) {
        List<T> resultsList = new ArrayList<>();
        for (Map<String, String> rowData : data) {
            resultsList.add(newInstanceFrom(clazz, rowData, constructorArgs));
        }
        return resultsList;
    }

    public static <T> T newInstanceFrom(final Class<T> clazz, final Map<String, String> rowData, final Object... constructorArgs) {
        T newObject = createNewInstanceOf(clazz, constructorArgs);
        assignPropertiesFromTestData(clazz, rowData, newObject);
        return newObject;
    }

    private static <T> T createNewInstanceOf(final Class<T> clazz, final Object... constructorArgs) {
        try {
            return InstanceBuilder.newInstanceOf(clazz, constructorArgs);
        } catch (Exception exception) {
            throw new FailedToInitializeTestData("Could not create test data beans", exception);
        }
    }

    private static <T> void assignPropertiesFromTestData(final Class<T> clazz, final Map<String, String> rowData, final T newObject) {
        Set<String> propertyNames = rowData.keySet();
        boolean validPropertyFound = false;
        for (String columnHeading : propertyNames) {
            String value = rowData.get(columnHeading);
            String property = FieldName.from(columnHeading).inNormalizedForm();

            if (assignPropertyValue(newObject, property, value)) {
                validPropertyFound = true;
            }
        }
        if (!validPropertyFound) {
            throw new FailedToInitializeTestData("No properties or public fields matching the data columns were found "
                    + "or could be assigned for the class " + clazz.getName()
                    + "using test data: " + rowData);
        }
    }

    private static <T> boolean assignPropertyValue(final T newObject, final String property, final String value) {
        boolean valueWasAssigned = true;
        try {
            InstanceBuilder.inObject(newObject).setPropertyValue(property, value);
        } catch (FailedToInitializeTestData e) {
            valueWasAssigned = false;
        }
        return valueWasAssigned;
    }

}
