package com.maba.osads.helper;

import java.util.Arrays;
import java.util.List;

public class IdsHelper {

    public static String mapListToCommaSeparatedIds(List<String> productIdsToPromote) {
        return String.join(",",productIdsToPromote);
    }

    public static List<String> mapCommaSeparatedIdsToList(String commaSeparatedIds){
        return Arrays.asList(commaSeparatedIds.split("\\s*,\\s*"));
    }
}
