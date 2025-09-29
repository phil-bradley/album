/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.common.textcontrol;

import ie.philb.album.ui.common.font.ApplicationFont;
import ie.philb.album.util.FontUtils;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 *
 * @author philb
 */
@EqualsAndHashCode
@ToString
public class TextControlModel {

    public static final ApplicationFont DEFAULT_FONT = ApplicationFont.NotoSerif;
    public static final int DEFAULT_FONT_SIZE = 24;
    public static final Color DEFAULT_FONT_COLOR = Color.DARK_GRAY;

    private final List<TextControlChangeListener> listeners = new ArrayList<>();
    private String text = "";
    private boolean isBold = false;
    private boolean isItalic = false;
    private boolean isUnderline = false;
    private String fontFamily = DEFAULT_FONT.name();
    private int fontSize = DEFAULT_FONT_SIZE;
    private Color fontColor = DEFAULT_FONT_COLOR;

    public void addChangeListener(TextControlChangeListener listener) {
        listeners.add(listener);
    }

    public void removeChangeListener(TextControlChangeListener listener) {
        listeners.remove(listener);
    }

    private List<TextControlChangeListener> getListeners() {
        return Collections.unmodifiableList(new ArrayList<>(listeners));
    }

    private void fireFormatUpdated() {
        for (TextControlChangeListener listener : getListeners()) {
            listener.formatUpdated(this);
        }
    }

    private void fireTextUpdated() {
        getListeners().forEach(listener -> {
            listener.textUpdated(this);
        });
    }

    private void fireEditCancelled() {
        getListeners().forEach(listener -> {
            listener.textEditCancelled(this);
        });
    }

    private void fireEditSelected() {
        getListeners().forEach(listener -> {
            listener.textEditSelected(this);
        });
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        fireTextUpdated();
    }

    public void cancelTextEdit() {
        fireEditCancelled();
    }

    public void textEditSelected() {
        fireEditSelected();
    }

    public boolean isBold() {
        return isBold;
    }

    public void setBold(boolean isBold) {
        if (this.isBold != isBold) {
            this.isBold = isBold;
            fireFormatUpdated();
        }
    }

    public boolean isItalic() {
        return isItalic;
    }

    public void setItalic(boolean isItalic) {
        if (this.isItalic != isItalic) {
            this.isItalic = isItalic;
            fireFormatUpdated();
        }
    }

    public boolean isUnderline() {
        return isUnderline;
    }

    public void setUnderline(boolean isUnderline) {
        if (this.isUnderline != isUnderline) {
            this.isUnderline = isUnderline;
            fireFormatUpdated();
        }
    }

    public String getFontFamily() {
        return fontFamily;
    }

    public void setFontFamily(String fontFamily) {
        if (!Objects.equals(fontFamily, this.fontFamily)) {
            this.fontFamily = fontFamily;
            fireFormatUpdated();
        }
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        if (this.fontSize != fontSize) {
            this.fontSize = fontSize;
            fireFormatUpdated();
        }
    }

    public Color getFontColor() {
        return fontColor;
    }

    public void setFontColor(Color fontColor) {
        if (!Objects.equals(fontColor, this.fontColor)) {
            this.fontColor = fontColor;
            fireFormatUpdated();
        }
    }

    public Font getDisplayFont(double fontScalingFactor) {

        float scaledSize = (float) (fontScalingFactor * getFontSize());
        Font font = ApplicationFont.byFamilyName(getFontFamily()).getFont(isBold, isItalic).deriveFont(scaledSize);

        if (isUnderline) {
            return FontUtils.underline(font);
        }

        return font;
    }

}
