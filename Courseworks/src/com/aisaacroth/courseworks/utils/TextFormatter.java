package com.aisaacroth.courseworks.utils;

public class TextFormatter {
    private final String P_START_TAG = "&lt;p&gt;";
    private final String P_END_TAG = "&lt;/p&gt;";
    
    public TextFormatter() {
        
    }

    public String formatText(String textString) {
       String frontTagLessString = removeStartPTags(textString);
       String formattedString = replaceWithNewLines(frontTagLessString);
       return formattedString;
    }
    
    public String removeStartPTags(String textString) {
        String startTagFreeString = textString.replace(P_START_TAG, "");
        return startTagFreeString;
    }
    
    public String removeEndPTags(String textString) {
        String endTagFreeString = textString.replace(P_END_TAG, "");
        return endTagFreeString;
    }
    
    public String replaceWithNewLines(String textString) {
        String newLineString = textString.replace(P_END_TAG, "\n");
        return newLineString;
    }
}
