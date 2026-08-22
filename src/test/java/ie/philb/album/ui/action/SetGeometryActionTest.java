/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package ie.philb.album.ui.action;

import ie.philb.album.AppEventBus;
import ie.philb.album.AppSession;
import ie.philb.album.Context;
import ie.philb.album.model.AlbumModel;
import ie.philb.album.model.PageGeometry;
import ie.philb.album.model.PageModel;
import ie.philb.album.model.PageSize;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 *
 * @author philb
 */
public class SetGeometryActionTest {

    @Test
    public void testDoAction() throws Exception {

        Context context = new Context(null, new AppSession(new AppEventBus()));
        context.session().setAlbumModel(new AlbumModel(PageSize.A4_Landscape, 0, 0));

        List<PageGeometry> geometries = List.of(
                PageGeometry.blank(),
                PageGeometry.square(2),
                PageGeometry.square(10),
                PageGeometry.rectangle(1, 5),
                PageGeometry.rectangle(PageGeometry.MAX_CELL_WIDTH, PageGeometry.MAX_CELL_HEIGHT),
                PageGeometry.withColumns(5, 4, 3, 2, 1),
                PageGeometry.withRows(8, 7, 6)
        );

        int pageCount = geometries.size();

        AddPageAction newPageAction = new AddPageAction(context.session());

        for (int i = 0; i < pageCount; i++) {
            newPageAction.doAction();
        }

        for (int i = 0; i < pageCount; i++) {
            PageModel pageModel = context.session().getAlbumModel().getPages().get(i);
            new SetGeometryAction(context.session(), pageModel, geometries.get(i)).doAction();
        }

        assertEquals(pageCount, context.session().getAlbumModel().getPages().size());

        for (int i = 0; i < pageCount; i++) {
            PageModel pageModel = context.session().getAlbumModel().getPages().get(i);
            PageGeometry pageGeometry = pageModel.getGeometry();
            assertEquals(geometries.get(i), pageGeometry);
        }
    }

}
