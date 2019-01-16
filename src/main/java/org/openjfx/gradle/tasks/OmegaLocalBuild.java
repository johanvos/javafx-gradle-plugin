package org.openjfx.gradle.tasks;

import com.gluonhq.omega.Omega;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

import java.util.Map;

public class OmegaLocalBuild extends DefaultTask {

    @TaskAction
    public void action() {
        System.err.println("OmegaLocalBuild action");
        try {
            final Map<String, ?> properties = getProject().getProperties();
            String mainClassName = (String) properties.get("mainClassName");
            String name = getProject().getName();
            System.err.println("mcn = "+mainClassName+" and name = "+name);
            Omega.run(mainClassName,name);
            // System.err.println(this.getProject().getProperties());
        //    Omega.run("com.gluonhq.svmsample", "testapp");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
