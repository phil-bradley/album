/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.common.filters;

import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;

/**
 *
 * @author philb
 */
public class BrightnessFilter implements ImageFilter {

    public static final int MIN_BRIGHTNESS = 0;
    public static final int MAX_BRIGHTNESS = 500;
    public static final int DEFAULT_BRIGHTNESS = 100;

    private float brightnessAdjustment = DEFAULT_BRIGHTNESS;

    public float getBrightnessAdjustment() {
        return brightnessAdjustment;
    }

    public void setBrightnessAdjustment(float brightnessAdjustment) {

        validateBrightness(brightnessAdjustment);

        if (brightnessAdjustment == this.brightnessAdjustment) {
            return;
        }

        this.brightnessAdjustment = brightnessAdjustment;
    }

    @Override
    public BufferedImage filter(BufferedImage image) {

        float factor = brightnessAdjustment / (float) 100;

        System.out.println("Brightness factor: " + factor);

        RescaleOp rescaleOp = new RescaleOp(factor, 0, null);
        BufferedImage brightened = new BufferedImage(
                image.getWidth(),
                image.getHeight(),
                image.getType()
        );

        rescaleOp.filter(image, brightened);
        return brightened;
    }

    private void validateBrightness(float brightnessAdjustment) {

        if (brightnessAdjustment < MIN_BRIGHTNESS) {
            throw new IllegalArgumentException("Brightness adjustment " + brightnessAdjustment + " < min value " + MIN_BRIGHTNESS);
        }

        if (brightnessAdjustment > MAX_BRIGHTNESS) {
            throw new IllegalArgumentException("Brightness adjustment " + brightnessAdjustment + " > max value " + MAX_BRIGHTNESS);
        }
    }
}
