package ch.epfl.scrumtool.database.google.conversion;

import static ch.epfl.scrumtool.util.Assertions.assertTrue;
import static ch.epfl.scrumtool.util.Preconditions.throwIfNull;
import ch.epfl.scrumtool.database.google.containers.InsertResponse;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumProject;
import ch.epfl.scrumtool.util.Preconditions;

/**
 * Ensures conversion between ScrumProject and Project
 * 
 * @author vincent
 * 
 */
public final class ProjectConverters {

    public static final EntityConverter<ScrumProject, Project> SCRUMPROJECT_TO_PROJECT = 
            new EntityConverter<ScrumProject, Project>() {

        @Override
        public Project convert(ScrumProject dbProject) {
            throwIfNull("Trying to convert a Project with null parameters",
                    dbProject.getKey(),
                    dbProject.getName(),
                    dbProject.getDescription());

            Preconditions.throwIfInvalidKey(dbProject.getKey());
            Project.Builder project = new Project.Builder();

            String key = dbProject.getKey();
            project.setKey(key);

            String name = dbProject.getName();
            project.setName(name);

            String description = dbProject.getDescription();
            project.setDescription(description);

            return project.build();
        }

    };

    public static final EntityConverter<Project, ScrumProject> PROJECT_TO_SCRUMPROJECT = 
            new EntityConverter<Project, ScrumProject>() {

        @Override
        public ScrumProject convert(Project project) {
            assertTrue(project != null);

            ScrumProject dbProject = new ScrumProject();

            if (!project.getKey().equals("")) {
                dbProject.setKey(project.getKey());
            }
            
            dbProject.setName(project.getName());
            dbProject.setDescription(project.getDescription());
            return dbProject;
        }

    };

    public static final EntityConverter<InsertResponse<Project>, Project> INSERTRESPONE_TO_PROJECT = 
            new EntityConverter<InsertResponse<Project>, Project>() {

        @Override
        public Project convert(InsertResponse<Project> a) {
            return a.getEntity()
                    .getBuilder()
                    .setKey(a.getKeyReponse().getKey())
                    .build();
        }
    };

}
