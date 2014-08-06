package com.aisaacroth.courseworks.utils;

import android.util.Log;

public class TextFormatter {
    private final String P_START_TAG = "&lt;p&gt;";
    private final String P_END_TAG = "&lt;/p&gt;";
    private final String BR_TAG = "&lt;br /&gt;";

    public TextFormatter() {

    }

    public String formatText(String textString) {
        String brString = textString;
        String frontTagLessString = removeStartPTags(brString);
        String newlineFreeString = removeCurrentNewLines(frontTagLessString);
        String untrimmedString = replaceWithNewLines(newlineFreeString);
        String[] trimmedStrings = trimStrings(untrimmedString);
        String formattedString = rebuiltString(trimmedStrings);
        return formattedString;
    }

    private String removeStartPTags(String textString) {
        return textString.replace(P_START_TAG, "");
    }

    private String replaceWithNewLines(String textString) {
        return textString.replace(P_END_TAG, "\n\n");
    }

    private String removeCurrentNewLines(String textString) {
        return textString.replace("\n", "");
    }

    private String[] trimStrings(String textString) {
        String[] textStrings = textString.split("\n\n");
        for (int i = 0; i < textStrings.length; i++) {
            textStrings[i] = textStrings[i].trim();
            if (textStrings[i].contains(BR_TAG)) {
                textStrings[i] = replaceBRTags(textStrings[i]);
            }
        }
        return textStrings;
    }

    private String rebuiltString(String[] textStrings) {
        StringBuilder builder = new StringBuilder();
        for (String string : textStrings) {
            builder.append(string + "\n\n");
        }
        return builder.toString();
    }

    public String replaceBRTags(String textString) {
        return textString.replace(BR_TAG, "\n");
    }
}
