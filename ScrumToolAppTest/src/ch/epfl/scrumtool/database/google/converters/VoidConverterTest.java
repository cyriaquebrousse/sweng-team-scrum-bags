package ch.epfl.scrumtool.database.google.converters;

import ch.epfl.scrumtool.database.google.conversion.VoidConverter;
import junit.framework.TestCase;

public class VoidConverterTest extends TestCase {
    public void testVoidConverter() {
        Void a = VoidConverter.VOID_TO_VOID.convert(null);
        assertEquals(a, null);
    }
    
}
