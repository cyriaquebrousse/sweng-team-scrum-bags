package ch.epfl.scrumtool.database.google.conversion;

import ch.epfl.scrumtool.database.google.containers.InsertResponse;
import ch.epfl.scrumtool.entity.Sprint;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumSprint;

/**
 * Ensures conversion bewteen ScurmSprint and Sprint
 * 
 * @author vincent
 * 
 */
public class SprintConverters {
    public static final EntityConverter<ScrumSprint, Sprint> SCRUMSPRINT_TO_SPRINT = 
            new EntityConverter<ScrumSprint, Sprint>() {

        @Override
        public Sprint convert(ScrumSprint dbSprint) {
            assert dbSprint != null;

            Sprint.Builder sprint = new Sprint.Builder();

            String key = dbSprint.getKey();
            if (key != null) {
                sprint.setKey(key);
            }

            String title = dbSprint.getTitle();
            if (title != null) {
                sprint.setTitle(title);
            }

            sprint.setDeadline(dbSprint.getDate());

            return sprint.build();
        }
    };

    public static final EntityConverter<Sprint, ScrumSprint> SPRINT_TO_SCRUMSPRINT = 
            new EntityConverter<Sprint, ScrumSprint>() {

        @Override
        public ScrumSprint convert(Sprint sprint) {
            assert sprint != null;

            ScrumSprint dbSprint = new ScrumSprint();

            if (!sprint.getKey().equals("")) {
                dbSprint.setKey(sprint.getKey());
            }
            
            dbSprint.setTitle(sprint.getTitle());
            dbSprint.setDate(sprint.getDeadline());
            // Currently we don't need LastModDate and LasModUser

            return dbSprint;
        }
    };
    
    public static final EntityConverter<InsertResponse<Sprint>, Sprint> OPSTATSPRINT_TO_SPRINT = 
            new EntityConverter<InsertResponse<Sprint>, Sprint>() {

        @Override
        public Sprint convert(InsertResponse<Sprint> a) {
            return a.getEntity()
                    .getBuilder()
                    .setKey(a.getkeyReponse().getKey())
                    .build();
        }
    };

}
