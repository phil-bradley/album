/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.model.appinfo;

import ie.philb.album.model.appinfo.LicenseInfo.Dependency;
import ie.philb.album.model.appinfo.LicenseInfo.License;

/**
 *
 * @author philb
 */
public class LicenseInfoHtmlRenderer {

    private LicenseInfo licenseInfo;

    public LicenseInfoHtmlRenderer(LicenseInfo info) {
        this.licenseInfo = info;
    }

    public String getHtml() {
        StringBuilder sb = new StringBuilder();

        for (Dependency dep : licenseInfo.dependencies()) {
            sb.append(getFormattedDependencyName(dep));

            for (License license : dep.license()) {
                sb.append(getFormattedLicense(license));
            }
        }

        return "<html><body>" + sb.toString() + "</body></html>";
    }

    private String getFormattedLicense(License license) {
        String template = "<a href='__URL__'>__LICENSE__</a><br/>";
        return template.replace("__URL__", license.url()).replace("__LICENSE__", license.name());
    }

    private String getFormattedDependencyName(Dependency dep) {
        String name = dep.groupId() + "." + dep.artifactId() + " v" + dep.version();
        return "<h1>" + name + "</h1>";
    }
}
