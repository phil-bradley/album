/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.model.appinfo;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author philb
 */
public class LicenseReader {

    private static final String LICENSE_RESOURCE = "/licenses.xml";
    private final XmlMapper xmlMapper = new XmlMapper();

    public LicenseReader() {
        xmlMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    public LicenseSummary read() throws Exception {
        return fromXml(fromResource(LICENSE_RESOURCE));
    }

    public LicenseSummary fromXml(String xml) throws Exception {
        LicenseSummary info = xmlMapper.readValue(xml, LicenseSummary.class);
        return info;
    }

    private String fromResource(String resourcePath) throws URISyntaxException, IOException {
        return IOUtils.resourceToString(resourcePath, Charset.defaultCharset());
    }
}
