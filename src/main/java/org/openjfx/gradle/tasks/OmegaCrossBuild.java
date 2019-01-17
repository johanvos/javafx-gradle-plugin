package org.openjfx.gradle.tasks;

import com.gluonhq.omega.Omega;
import org.gradle.api.DefaultTask;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.DependencySet;
import org.gradle.api.file.FileCollection;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.tasks.TaskAction;
import org.gradle.api.tasks.compile.JavaCompile;

import java.util.Map;

public class OmegaCrossBuild extends DefaultTask {

    @TaskAction
    public void action() {
        System.err.println("OmegaLocalBuild action");
        try {
            final Map<String, ?> properties = getProject().getProperties();
            String mainClassName = (String) properties.get("mainClassName");
            String name = getProject().getName();
            for (Configuration configuration : getProject().getBuildscript().getConfigurations()) {
                System.err.println("CONFIG = "+configuration);
                DependencySet deps = configuration.getAllDependencies();
                System.err.println("DEPS = "+deps);
                deps.forEach(dep -> {
                    System.err.println("DEP = "+dep);
                });
            }
            System.err.println("mcn = "+mainClassName+" and name = "+name);
            JavaCompile compileTask = (JavaCompile)this.getProject().getTasks().findByName(JavaPlugin.COMPILE_JAVA_TASK_NAME);
            FileCollection classpath = compileTask.getClasspath();
            System.err.println("CLASSPATH = "+classpath.getFiles());
            System.err.println("JAVACP = "+System.getProperty("java.class.path"));
            Omega.crossBuild(mainClassName,name);
            // System.err.println(this.getProject().getProperties());
            //    Omega.run("com.gluonhq.svmsample", "testapp");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
