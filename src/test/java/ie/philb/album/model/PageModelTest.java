/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.model;

import ie.philb.album.util.TestUtils;
import java.io.File;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 *
 * @author philb
 */
public class PageModelTest {

    @Test
    void givenPageModel_expectInitialised() {

        int pageId = 15;
        int margin = 12;

        PageGeometry geometry = PageGeometry.square(2);
        PageModel pageModel = new PageModel(geometry, PageSize.A4_Landscape).withMargin(margin);
        pageModel.setPageId(pageId);

        assertEquals(pageModel.getGeometry(), geometry);
        assertEquals(PageSize.A4_Landscape, pageModel.getPageSize());
        assertEquals(pageId, pageModel.getPageId());
        assertEquals(margin, pageModel.getVerticalMargin());
        assertEquals(margin, pageModel.getHorizontalMargin());
        assertEquals(4, pageModel.getCellCount());
        assertEquals(4, pageModel.getPageEntries().size());
    }

    @Test
    void givenPageModel_whenGeometryUpdated_expectedNewCellCount() {

        PageGeometry geometry = PageGeometry.square(2);
        PageModel pageModel = new PageModel(geometry, PageSize.A4_Landscape);
        assertEquals(4, pageModel.getCellCount());

        PageGeometry newGeometry = PageGeometry.square(3);
        pageModel.setGeometry(newGeometry);
        assertEquals(9, pageModel.getCellCount());
    }

    @Test
    void givenPageModel_whenImageSet_expectedPageEntryUpdated() {

        PageGeometry geometry = PageGeometry.square(2);
        PageModel pageModel = new PageModel(geometry, PageSize.A4_Landscape);

        File file = TestUtils.getTestImageFile();

        pageModel.setImage(file, 1);

        PageEntryModel pem = pageModel.getPageEntries().get(1);
        assertNotNull(pem);

        File pemFile = pem.getImageFile();
        assertNotNull(pemFile);

        String pemFileName = pemFile.getName();
        assertTrue(pemFileName.endsWith(file.getName()));
    }

    @Test
    void givenPageModel_whenIndexInvalid_expectException() {

        PageGeometry geometry = PageGeometry.square(2);
        PageModel pageModel = new PageModel(geometry, PageSize.A4_Landscape);

        File file = TestUtils.getTestImageFile();
        Exception thrown = assertThrows(
                RuntimeException.class,
                () -> {
                    pageModel.setImage(file, 17);
                }
        );
        assertTrue(thrown.getMessage().contains("Cannot add image at position"));
    }

    @Test
    void givenPageModel_whenImageFileInvalid_expectException() {

        PageGeometry geometry = PageGeometry.square(2);
        PageModel pageModel = new PageModel(geometry, PageSize.A4_Landscape);

        File notExists = new File("notexists");

        Exception thrown = assertThrows(
                RuntimeException.class,
                () -> {
                    pageModel.setImage(notExists, 1);
                }
        );
        assertTrue(thrown.getMessage().contains("Failed to set image"));
    }

    @Test
    @Disabled
    void givenPageModel_whenGeometryUpdated_expectedImagePreserved() {

    }

    @Test
    @Disabled
    void givenPageModel_whenGeometryUpdated_expectedZoomPreserved() {

    }
}
