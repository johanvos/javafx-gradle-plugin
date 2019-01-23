
/*
 * Copyright (c) 2019, Gluon
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * * Neither the name of the copyright holder nor the names of its
 *   contributors may be used to endorse or promote products derived from
 *   this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.openjfx.gradle.tasks;

import com.gluonhq.omega.Omega;
import org.gradle.api.DefaultTask;
import org.gradle.api.Task;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.DependencySet;
import org.gradle.api.file.FileCollection;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.tasks.TaskAction;
import org.gradle.api.tasks.compile.JavaCompile;

import java.util.Map;

public class OmegaLocalBuild extends DefaultTask {

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
            String jfxsdk = (String)getProject().findProperty("javafxsdk");
            Omega.setJavaFXRoot(jfxsdk);
            String svm = (String)getProject().findProperty("svm");
            Omega.setJavaFXRoot(svm);
            Omega.localBuild(mainClassName,name);
            // System.err.println(this.getProject().getProperties());
        //    Omega.run("com.gluonhq.svmsample", "testapp");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
