/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.model;

import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author philb
 */
public class ApplicationInfo {

    private static final String BUILD_PROPERTIES_RESOURCE = "/build-info.properties";
    private static final String GIT_PROPERTIES_RESOURCE = "/git.properties";

    private Properties buildProperties = new Properties();
    private Properties gitProperties = new Properties();

    public ApplicationInfo() {

        try (InputStream in = getClass().getResourceAsStream(BUILD_PROPERTIES_RESOURCE)) {
            buildProperties.load(in);
        } catch (Exception ex) {
        }

        try (InputStream in = getClass().getResourceAsStream(GIT_PROPERTIES_RESOURCE)) {
            gitProperties.load(in);
        } catch (Exception ex) {
        }
    }

    public String getProjectName() {
        return buildProperties.getProperty("project.name");
    }

    public String getProjectVersion() {
        return buildProperties.getProperty("project.version");
    }

    public String getProjectGroupId() {
        return buildProperties.getProperty("project.groupId");
    }

    public String getProjectArtifactId() {
        return buildProperties.getProperty("project.artifactId");
    }

    public String getGitBranch() {
        return gitProperties.getProperty("git.branch");
    }

    public String getGitCommit() {
        return gitProperties.getProperty("git.commit.id.abbrev");
    }

    public String getGitCommitTime() {
        return gitProperties.getProperty("git.commit.time");
    }
    
    public String getBuildTime() {
        return gitProperties.getProperty("git.build.time");
    }
}
