package ch.epfl.scrumtool.database.google.conversion;

import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumProject;

/**
 * Ensures conversion between ScrumProject and Project
 * 
 * @author vincent
 * 
 */
public class ProjectConverters {

    public static final EntityConverter<ScrumProject, Project> SCRUMPROJECT_TO_PROJECT = new EntityConverter<ScrumProject, Project>() {

        @Override
        public Project convert(ScrumProject dbProject) {
            assert dbProject != null;

            Project.Builder project = new Project.Builder();

            String key = dbProject.getKey();
            if (key != null) {
                project.setKey(key);
            }

            String name = dbProject.getName();
            if (name != null) {
                project.setName(name);
            }

            String description = dbProject.getDescription();
            if (description != null) {
                project.setDescription(description);
            }

            return project.build();
        }

    };

    public static final EntityConverter<Project, ScrumProject> PROJECT_TO_SCRUMPROJECT = new EntityConverter<Project, ScrumProject>() {

        @Override
        public ScrumProject convert(Project project) {
            assert project != null;

            ScrumProject dbProject = new ScrumProject();

            dbProject.setKey(project.getKey());
            dbProject.setName(project.getName());
            dbProject.setDescription(project.getDescription());
            // Currently we don't need LastModDate and LasModUser
            return dbProject;
        }

    };

}
