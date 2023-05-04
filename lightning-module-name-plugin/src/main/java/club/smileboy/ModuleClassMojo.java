package club.smileboy;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.*;
import java.util.List;
import java.util.Properties;

/**
 * A Maven plugin to rewrite a Java class file with the current project module name.
 *
 * 类似于将模块名写入到 一个类路径上,然后进行打包 ...
 *
 * 类似于maven-resource-plugin ..
 */
@Mojo(name = "module-class", defaultPhase = LifecyclePhase.PROCESS_CLASSES)
public class ModuleClassMojo extends AbstractMojo {

  /**
   * The Maven project.
   * 当前打包的maven 项目 ..
   */
  @Parameter(defaultValue = "${project}", readonly = true)
  private MavenProject project;

  public void execute() throws MojoExecutionException {
    String moduleName = project.getArtifactId();
    getLog().info("write module name: " + moduleName + " to lightning-module-info.properties");

    // 将模块名写入 Java 文件的属性中
    Properties properties = new Properties();
    properties.setProperty("module.name", moduleName);
    FileOutputStream outputStream = null;
    try {
      outputStream = new FileOutputStream("target/classes/lightning-module-info.properties");
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    //
    try {
      properties.store(outputStream, null);
      // 写入失败 ...
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}