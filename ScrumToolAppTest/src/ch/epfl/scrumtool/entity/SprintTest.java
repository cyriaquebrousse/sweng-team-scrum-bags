package ch.epfl.scrumtool.entity;

import junit.framework.TestCase;

public class SprintTest extends TestCase {
    
    private final static String KEY = "key";
            
    public void testEqualsRef() {
        Sprint s1 = new Sprint.Builder().build();
        Sprint s2 = s1;
        
        assertEquals(s1, s2);
    }
    
    public void testEqualsStruct() {
        Sprint s1 = new Sprint.Builder().setDeadline(500).setKey(KEY).build();
        Sprint s2 = new Sprint.Builder().setKey(KEY).build();
        
        assertEquals(s1, s2);
    }
    
    public void testCopyConstructor() {
        Sprint s1 = new Sprint.Builder().build();
        Sprint s2 = new Sprint.Builder(s1).build();
        
        assertEquals(s1, s2);
    }
    
    public void testHashCode() {
        Sprint s1 = new Sprint.Builder().setKey(KEY).build();
        Sprint s2 = new Sprint.Builder(s1).build();
        
        assertEquals(s1.hashCode(), s2.hashCode());
    }
    
    public void testCompareToSameDeadline() {
        Sprint s1 = new Sprint.Builder().build();
        Sprint s2 = new Sprint.Builder(s1).build();
        
        assertTrue(s1.compareTo(s2) == 0);
    }
    
    public void testCompareToBefore() {
        Sprint s1 = new Sprint.Builder().setDeadline(0).build();
        Sprint s2 = new Sprint.Builder().setDeadline(1).build();
        
        assertTrue(s1.compareTo(s2) < 0);
    }
    
    public void testCompareToAfter() {
        Sprint s1 = new Sprint.Builder().setDeadline(1).build();
        Sprint s2 = new Sprint.Builder().setDeadline(0).build();
        
        assertTrue(s1.compareTo(s2) > 0);
    }

}
