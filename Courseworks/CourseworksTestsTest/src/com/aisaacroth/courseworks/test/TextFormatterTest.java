package com.aisaacroth.courseworks.test;

import android.util.Log;

import com.aisaacroth.courseworks.utils.TextFormatter;

import junit.framework.TestCase;

public class TextFormatterTest extends TestCase {
    private static final String P_START_TAG = "&lt;p&gt;";
    private static final String P_END_TAG = "&lt;/p&gt;";

    private TextFormatter formatter;
    private String testString;
    private String formattedString;

    protected void setUp() throws Exception {
        super.setUp();
        formatter = new TextFormatter();
        testString = prepareTestMessage();
    }

    private String prepareTestMessage() {
        return "&lt;p&gt;  Dear students:&lt;/p&gt; "
                + "&lt;p&gt;  This message will be in English as it is very important. I was "
                + "just notified that there is a scheduling conflict with our classroom"
                + "for the final exam. They moved us to the fourth floor. &lt;/p&gt;"
                + "&lt;p&gt;  HAM 411 SPAN 1201.001&lt;/p&gt;"
                + "&lt;p&gt;  Call me if you have any problems.&lt;/p&gt;"
                + "&lt;p&gt;  Cell: 610-420-3822&lt;/p&gt;"
                + "&lt;p&gt;  Saludos,&lt;/p&gt;"
                + "&lt;p&gt;  Jessica&lt;/p&gt;<";
    }

    public void testConstructor() {
        assertNotNull(formatter);
    }

    public void testFormatString() {
        formattedString = formatter.formatText(testString);
        Log.d("STRING", formattedString);
        assertTrue(!hasStartTag(formattedString));
        assertTrue(!hasEndTag(formattedString));
        assertTrue(formattedString.contains("\n"));
    }

    private boolean hasEndTag(String textString) {
        return (textString.contains(P_END_TAG)) ? true : false;
    }

    private boolean hasStartTag(String textString) {
        return (textString.contains(P_START_TAG)) ? true : false;
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

}
