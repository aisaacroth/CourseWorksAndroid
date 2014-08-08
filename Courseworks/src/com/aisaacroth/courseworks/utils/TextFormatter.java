package com.aisaacroth.courseworks.utils;

public class TextFormatter {
    private final String P_START_TAG = "&lt;p&gt;";
    private final String P_END_TAG = "&lt;/p&gt;";
    private final String BR_TAG = "&lt;br /&gt;";

    public TextFormatter() {

    }

    public String formatText(String textString) {
        String paragraphString = parseParagraphs(textString);
        String formattedString = parseBreaks(paragraphString);
        return formattedString;
    }

    private String parseParagraphs(String textString) {
        String frontTagLessString = removeTags(textString, P_START_TAG);
        String newlineFreeString = removeCurrentNewLines(frontTagLessString);
        String untrimmedString = replaceWithBlankLines(newlineFreeString,
                P_END_TAG);
        String[] trimmedStrings = trimStrings(untrimmedString, "\n\n");
        String paragraphString = rebuildString(trimmedStrings, "\n\n");
        return paragraphString;
    }

    private String parseBreaks(String textString) {
        String formattedString = null;
        if (textString.contains(BR_TAG)) {
            String unformattedString = replaceWithNewLine(textString, BR_TAG);
            String[] trimmedStrings = trimStrings(unformattedString, "\n");
            formattedString = rebuildString(trimmedStrings, "\n");
        }

        return formattedString;
    }

    private String removeTags(String textString, String tagType) {
        return textString.replace(tagType, "");
    }

    private String replaceWithBlankLines(String textString, String tagType) {
        return textString.replace(tagType, "\n\n");
    }

    private String replaceWithNewLine(String textString, String tagType) {
        return textString.replace(tagType, "\n");
    }

    private String removeCurrentNewLines(String textString) {
        return textString.replace("\n", "");
    }

    private String[] trimStrings(String textString, String pattern) {
        String[] textStrings = textString.split(pattern);

        for (int i = 0; i < textStrings.length; i++) {
            textStrings[i] = textStrings[i].trim();
        }
        return textStrings;
    }

    private String rebuildString(String[] textStrings, String endline) {
        StringBuilder builder = new StringBuilder();
        for (String string : textStrings) {
            builder.append(string + endline);
        }
        return builder.toString();
    }

}
