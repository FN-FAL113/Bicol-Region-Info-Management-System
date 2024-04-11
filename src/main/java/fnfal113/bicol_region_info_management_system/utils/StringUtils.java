package main.java.fnfal113.bicol_region_info_management_system.utils;

public class StringUtils {

    public static String toPascalCase(String str) {
        String[] strArr = str.split("_");

        String newStr = "";

        for (String string : strArr) {
            newStr += string.substring(0, 1).toUpperCase() + string.substring(1) + " ";
        }

        return newStr;
        
    }

}

