package co.com.quality.data.utilities;

public class GeneralUtility {

    private GeneralUtility() {
    }

    public static String getTypeClass(Object object) {
        return object.getClass().getSimpleName();
    }
}
