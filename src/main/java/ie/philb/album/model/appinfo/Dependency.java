/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.model.appinfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.Data;

/**
 *
 * @author philb
 */
@Data
public class Dependency {

    private String groupId;
    private String artifactId;
    private String version;
    private List<License> licenses = new ArrayList<>();

    public void setLicenses(List<License> licenses) {
        this.licenses.clear();
        this.licenses.addAll(licenses);
    }

    public List<License> getLicenses() {
        return Collections.unmodifiableList(licenses);
    }
}
