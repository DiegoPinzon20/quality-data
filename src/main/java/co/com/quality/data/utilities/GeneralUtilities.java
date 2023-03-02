package co.com.quality.data.utilities;

public class GeneralUtilities {

    private GeneralUtilities() {
    }

    public static String getTypeClass(Object object) {
        return object.getClass().getSimpleName();
    }
}
