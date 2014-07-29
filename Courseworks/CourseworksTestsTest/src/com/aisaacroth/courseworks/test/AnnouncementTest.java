package com.aisaacroth.courseworks.test;

import com.aisaacroth.courseworks.structures.Announcement;

import junit.framework.TestCase;

/**
 * Test suite for the Announcement class and object.
 * 
 * @author Alexander Roth
 * @date 2014-07-29
 */
public class AnnouncementTest extends TestCase {

    private Announcement testAnnouncement;
    
    protected void setUp() throws Exception {
        super.setUp();
        testAnnouncement = new Announcement();
        setAttributes();
    }
    
    private void setAttributes() {
        testAnnouncement.setTitle("Hi!");
        testAnnouncement.setBodyText("Testing");
        testAnnouncement.setDueDate("2014-01-01");
        testAnnouncement.setPostedDate("2014-01-01");
        testAnnouncement.setProfessorName("John Doe");
        testAnnouncement.setId("bd91eca3-a367-42fa-b4ca-ad0b5681f43c");
    }
    
    public void testAllFields() {
        assertEquals("Testing", testAnnouncement.getBodyText()); 
        assertNotNull(testAnnouncement);
        assertEquals("2014-01-01", testAnnouncement.getDueDate());
        assertEquals("2014-01-01", testAnnouncement.getPostedDate());
        assertEquals("John Doe", testAnnouncement.getProfessorName());
        assertEquals("Hi!", testAnnouncement.getTitle());
    }
    
    public void testBodyText() {
        assertEquals("Testing", testAnnouncement.getBodyText());
    }
    
    public void testConstructor() {
        assertNotNull(testAnnouncement);
    }
    
    public void testDueDate() {
        assertEquals("2014-01-01", testAnnouncement.getDueDate());
    }
    
    public void testId() {
        assertEquals("bd91eca3-a367-42fa-b4ca-ad0b5681f43c", testAnnouncement.getId());
    }
    
    public void testPostedDate() {
        assertEquals("2014-01-01", testAnnouncement.getPostedDate());
    }
    
    public void testProfessorName() {
        assertEquals("John Doe", testAnnouncement.getProfessorName());
    }
    
    public void testTitle() {
        assertEquals("Hi!", testAnnouncement.getTitle());
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

}
