package ch.epfl.scrumtool.database.google.conversion;


/**
 * @author vincent
 *
 */
public final class VoidConverter {

    public  static final EntityConverter<Void, Void> VOID_TO_VOID = 
            new EntityConverter<Void, Void>() {
        @Override
        public Void convert(Void a) {
            return a;
        }
    };
}
