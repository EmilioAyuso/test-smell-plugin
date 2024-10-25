package com.github.EmilioAyuso;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.LifecyclePhase;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

@Mojo(name = "detect-smells", defaultPhase = LifecyclePhase.TEST)
public class TestSmellDetectorMojo extends AbstractMojo {

    @Parameter(property = "nameProject", required = true)
    private String nameProject;

    @Parameter(property = "pathTest", required = true)
    private String pathTest;

    @Parameter(property = "pathSrc", required = true)
    private String pathSrc;

    @Parameter(property = "pathJar", required = true)
    private String pathJar;

    @Override
    public void execute() throws MojoExecutionException {
        getLog().info("Running Test Smell Detection with tsDetect");

        try {
            //creamos el archivo csv
            File inputCSV = new File(nameProject + ".csv");
            BufferedWriter writer = new BufferedWriter(new FileWriter(inputCSV));
            writer.write(nameProject + "," + pathTest + "," + pathSrc);
            writer.close();


            ProcessBuilder processBuilder = new ProcessBuilder("java", "-jar", pathJar, inputCSV.getAbsolutePath());
            Process p = processBuilder.start();
            p.waitFor();
            
            getLog().info("Análisis completado");

        } catch (Exception e) {
            throw new MojoExecutionException("Failed to run tsDetect", e);
        }
    }
}
