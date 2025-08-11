/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.model.appinfo;

/**
 *
 * @author philb
 */
public class LicenseInfoHtmlRenderer {

    private final LicenseSummary licenseSummary;

    public LicenseInfoHtmlRenderer(LicenseSummary licenseSummary) {
        this.licenseSummary = licenseSummary;
    }

    public String getHtml() {
        String html = """
                      <html>
                      <body background="#ffffff">
                      <h1>Acknowledgements</h1>
                      This project is built on open source software and uses the following third party libraries. 
                      <br/>
                      <br/>
                      <br/>
                      __BODY__
                      </body>
                      </html>
                      """;

        StringBuilder sb = new StringBuilder();

        for (Dependency dep : licenseSummary.getDependencies()) {
            sb.append(getFormattedDependencyName(dep));

            for (License license : dep.getLicenses()) {
                sb.append(getFormattedLicense(license));
            }

            sb.append("<br/>");
        }

        return html.replace("__BODY__", sb.toString());
    }

    private String getFormattedLicense(License license) {
        String template = "<a href='__URL__'>__LICENSE__</a><br/>";
        return template.replace("__URL__", license.getUrl()).replace("__LICENSE__", license.getName());
    }

    private String getFormattedDependencyName(Dependency dep) {
        String name = dep.getGroupId() + "." + dep.getArtifactId() + " v" + dep.getVersion();
        return "<b>" + name + "</b><br/>";
    }
}
