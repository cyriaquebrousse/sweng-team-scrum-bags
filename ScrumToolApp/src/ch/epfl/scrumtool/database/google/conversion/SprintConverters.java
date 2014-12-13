package ch.epfl.scrumtool.database.google.conversion;

import static ch.epfl.scrumtool.util.Assertions.assertTrue;
import static ch.epfl.scrumtool.util.Preconditions.throwIfInconsistentData;
import ch.epfl.scrumtool.database.google.containers.InsertResponse;
import ch.epfl.scrumtool.entity.Sprint;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumSprint;

/**
 * Ensures conversion bewteen ScurmSprint and Sprint
 * 
 * @author vincent
 * 
 */
public final class SprintConverters {
    public static final EntityConverter<ScrumSprint, Sprint> SCRUMSPRINT_TO_SPRINT = 
            new EntityConverter<ScrumSprint, Sprint>() {

        @Override
        public Sprint convert(ScrumSprint dbSprint) {
            assertTrue(dbSprint != null);
            throwIfInconsistentData("Trying to convert a Sprint with null parameters",
                    dbSprint.getKey(), dbSprint.getTitle(), dbSprint.getDate());

            Sprint.Builder sprint = new Sprint.Builder();

            String key = dbSprint.getKey();
            sprint.setKey(key);

            String title = dbSprint.getTitle();
            sprint.setTitle(title);

            sprint.setDeadline(dbSprint.getDate());

            return sprint.build();
        }
    };

    public static final EntityConverter<Sprint, ScrumSprint> SPRINT_TO_SCRUMSPRINT = 
            new EntityConverter<Sprint, ScrumSprint>() {

        @Override
        public ScrumSprint convert(Sprint sprint) {
            ScrumSprint dbSprint = new ScrumSprint();
            if (!sprint.getKey().equals("")) {
                dbSprint.setKey(sprint.getKey());
            }
            
            dbSprint.setTitle(sprint.getTitle());
            dbSprint.setDate(sprint.getDeadline());

            return dbSprint;
        }
    };
    
    public static final EntityConverter<InsertResponse<Sprint>, Sprint> INSERTRESPONSE_TO_SPRINT = 
            new EntityConverter<InsertResponse<Sprint>, Sprint>() {

        @Override
        public Sprint convert(InsertResponse<Sprint> a) {
            return a.getEntity()
                    .getBuilder()
                    .setKey(a.getKeyReponse().getKey())
                    .build();
        }
    };
}
