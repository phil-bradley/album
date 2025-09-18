/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.io;

import ie.philb.album.model.PageGeometry;
import java.util.List;

/**
 *
 * @author philb
 */
public record PageData(List<CellData> cells, PageGeometry pageGeometry) {

}
