package ch.epfl.scrumtool.utils;

import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.server.scrumtool.model.KeyResponse;

public final class TestConstants {
    public static final String validKey = "v";
    public static final String invalidKey = "";


    public static final Project TEST_PROJECT_W_KEY = new Project.Builder()
    .setDescription("Test Project")
    .setKey("key")
    .setName("Test Project")
    .build();

    public static final Project TEST_PROJECT_WO_KEY = new Project.Builder(TEST_PROJECT_W_KEY)
    .setKey("")
    .build();

    public static final KeyResponse VALID_KEY_RESPONSE = new KeyResponse().setKey(validKey);
    
    public static final KeyResponse INVALID_KEY_RESPONSE = new KeyResponse().setKey(invalidKey);
    
}
