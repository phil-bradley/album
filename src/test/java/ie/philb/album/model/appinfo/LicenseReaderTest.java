/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.model.appinfo;

import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

/**
 *
 * @author philb
 */
public class LicenseReaderTest {

    @Test
    void foo() throws Exception {
        String xml = """
                     <?xml version="1.0" encoding="UTF-8" standalone="no"?>
                     <licenseSummary>
                         <dependencies>
                             <dependency>
                                 <groupId>ch.qos.logback</groupId>
                                 <artifactId>logback-classic</artifactId>
                                 <version>1.5.18</version>
                                 <licenses>
                                     <license>
                                         <name>Eclipse Public License - v 1.0</name>
                                         <url>http://www.eclipse.org/legal/epl-v10.html</url>
                                         <file>eclipse public license - v 1.0 - epl-v10.html</file>
                                     </license>
                                     <license>
                                         <name>GNU Lesser General Public License</name>
                                         <url>http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html</url>
                                     </license>
                                 </licenses>
                             </dependency>
                             <dependency>
                                 <groupId>ch.qos.logback</groupId>
                                 <artifactId>logback-core</artifactId>
                                 <version>1.5.18</version>
                                 <licenses>
                                     <license>
                                         <name>Eclipse Public License - v 1.0</name>
                                         <url>http://www.eclipse.org/legal/epl-v10.html</url>
                                         <file>eclipse public license - v 1.0 - epl-v10.html</file>
                                     </license>
                                     <license>
                                         <name>GNU Lesser General Public License</name>
                                         <url>http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html</url>
                                     </license>
                                 </licenses>
                             </dependency>
                         </dependencies>
                     </licenseSummary>
                     """;

        LicenseSummary licenseSummary = new LicenseReader().fromXml(xml);
        assertNotNull(licenseSummary);

        List<Dependency> dependencies = licenseSummary.getDependencies();
        assertEquals(2, dependencies.size());

        Dependency dep0 = dependencies.get(0);
        assertEquals("ch.qos.logback", dep0.getGroupId());
        assertEquals("logback-classic", dep0.getArtifactId());
        assertEquals("1.5.18", dep0.getVersion());

        List<License> licenses = dep0.getLicenses();
        assertEquals(2, licenses.size());
        
        License license0 = licenses.get(0);
        assertEquals("Eclipse Public License - v 1.0", license0.getName());
        assertEquals("http://www.eclipse.org/legal/epl-v10.html", license0.getUrl());
    }
}
