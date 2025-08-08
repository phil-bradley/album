/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.model;

import java.util.List;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 *
 * @author philb
 */
@JacksonXmlRootElement(localName = "licenseSummary")
public record LicenseInfo(
        @JacksonXmlElementWrapper(localName = "dependencies")
        List<Dependency> dependencies
        ) {

    public record Dependency(
            String groupId,
            String artifactId,
            String version,
            @JacksonXmlElementWrapper(localName = "licenses")
            List<License> license
            ) {

    }

    public record License(
            String name,
            String url,
            String file
            ) {

    }
}
