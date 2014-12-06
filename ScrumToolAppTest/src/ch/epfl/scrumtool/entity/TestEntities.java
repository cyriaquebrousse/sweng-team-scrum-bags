package ch.epfl.scrumtool.entity;

public final class TestEntities {
    public static final Project TEST_PROJECT_W_KEY = new Project.Builder()
        .setDescription("Test Project")
        .setKey("key")
        .setName("Test Project")
        .build();
    
    public static final Project TEST_PROJECT_WO_KEY = new Project.Builder(TEST_PROJECT_W_KEY)
        .setKey("")
        .build();
    
}
