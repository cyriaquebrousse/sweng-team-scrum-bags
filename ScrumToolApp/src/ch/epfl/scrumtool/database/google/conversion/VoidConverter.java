package ch.epfl.scrumtool.database.google.conversion;


/**
 * @author vincent
 *
 */
public class VoidConverter {

    public static final EntityConverter<Void, Boolean> VOID_TO_BOOLEAN = 
            new EntityConverter<Void, Boolean>() {

        @Override
        public Boolean convert(Void a) {
            return true;
        }
    };
}
