package ch.epfl.scrumtool.entity;

import ch.epfl.scrumtool.entity.Sprint.Builder;
import junit.framework.TestCase;

public class SprintTest extends TestCase {
    
    private static final String KEY = "key";
    private static final String TITLE = "title";
            
    public void testEqualsRef() {
        Sprint s1 = newBuilder().build();
        Sprint s2 = s1;
        
        assertEquals(s1, s2);
    }
    
    public void testEqualsStruct() {
        Sprint s1 = newBuilder().setDeadline(500).setKey(KEY).build();
        Sprint s2 = newBuilder().setKey(KEY).build();
        
        assertEquals(s1, s2);
    }
    
    public void testCopyConstructor() {
        Sprint s1 = newBuilder().build();
        Sprint s2 = newBuilder(s1).build();
        
        assertEquals(s1, s2);
    }
    
    public void testHashCode() {
        Sprint s1 = newBuilder().setKey(KEY).build();
        Sprint s2 = newBuilder(s1).build();
        
        assertEquals(s1.hashCode(), s2.hashCode());
    }
    
    public void testCompareToSameDeadline() {
        Sprint s1 = newBuilder().build();
        Sprint s2 = newBuilder(s1).build();
        
        assertTrue(s1.compareTo(s2) == 0);
    }
    
    public void testCompareToBefore() {
        Sprint s1 = newBuilder().setDeadline(0).build();
        Sprint s2 = newBuilder().setDeadline(1).build();
        
        assertTrue(s1.compareTo(s2) < 0);
    }
    
    public void testCompareToAfter() {
        Sprint s1 = newBuilder().setDeadline(1).build();
        Sprint s2 = newBuilder().setDeadline(0).build();
        
        assertTrue(s1.compareTo(s2) > 0);
    }

    private Sprint.Builder newBuilder() {
        return new Builder().setTitle(TITLE);
    }

    private Sprint.Builder newBuilder(Sprint other) {
        return new Builder(other).setTitle(TITLE);
    }

}
