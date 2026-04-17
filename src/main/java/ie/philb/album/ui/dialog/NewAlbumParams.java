/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.dialog;

import ie.philb.album.model.PageSize;

/**
 *
 * @author philb
 */
public record NewAlbumParams(String title,
        int margin,
        int gutter,
        int pages,
        PageSize pageSize) {
    
}
