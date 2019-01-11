package org.openjfx.gradle.tasks;

import com.gluonhq.omega.Omega;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

public class OmegaLocalBuild extends DefaultTask {

    @TaskAction
    public void action() {
        System.err.println("OmegaLocalBuild");
        try {
            Omega.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
