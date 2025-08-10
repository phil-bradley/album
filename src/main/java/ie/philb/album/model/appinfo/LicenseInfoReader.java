/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.model.appinfo;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.io.InputStream;

/**
 *
 * @author philb
 */
public class LicenseInfoReader {

    public LicenseInfo read() throws Exception {
        XmlMapper xmlMapper = new XmlMapper();

        try (InputStream in = getClass().getResourceAsStream("/licenses.xml")) {
            LicenseInfo info = xmlMapper.readValue(in, LicenseInfo.class);
            return info;
        } catch (Exception ex) {
            System.out.println("Cannot load license: " + ex.getMessage());
            throw ex;
        }
    }
}
