package ch.epfl.scrumtool.database.google.conversion;


/**
 * For database operations where no answer is sent from the server a pseudo-void-converter is needed
 * 
 * @author aschneuw
 *
 */
public final class VoidConverter {

    public  static final EntityConverter<Void, Void> VOID_TO_VOID = new EntityConverter<Void, Void>() {
        @Override
        public Void convert(Void a) {
            return a;
        }
    };
}
