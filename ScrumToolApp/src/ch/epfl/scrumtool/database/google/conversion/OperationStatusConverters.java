package ch.epfl.scrumtool.database.google.conversion;

import ch.epfl.scrumtool.server.scrumtool.model.OperationStatus;

/**
 * @author vincent
 *
 */
public class OperationStatusConverters {

    public static final EntityConverter<OperationStatus, Boolean> OPSTAT_TO_BOOLEAN = 
            new EntityConverter<OperationStatus, Boolean>() {

        @Override
        public Boolean convert(OperationStatus a) {
            return a.getSuccess();
        }
    };
}
