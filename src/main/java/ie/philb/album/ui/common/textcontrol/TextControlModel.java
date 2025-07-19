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

/**
 *
 * @author philb
 */
public class TextControlModel {

    private final List<TextControlChangeListener> listeners = new ArrayList<>();
    private String text = "";
    private boolean isBold = false;
    private boolean isItalic = false;
    private boolean isUnderline = false;
    private String fontFamily = ApplicationFont.NotoSerif.name();
    private int fontSize = 24;
    private Color fontColor = Color.DARK_GRAY;

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

    @Override
    public String toString() {
        return "TextControlModel{" + "listeners=" + listeners + ", text=" + text + ", isBold=" + isBold + ", isItalic=" + isItalic + ", isUnderline=" + isUnderline + ", fontFamily=" + fontFamily + ", fontSize=" + fontSize + ", fontColor=" + fontColor + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.text);
        hash = 37 * hash + (this.isBold ? 1 : 0);
        hash = 37 * hash + (this.isItalic ? 1 : 0);
        hash = 37 * hash + (this.isUnderline ? 1 : 0);
        hash = 37 * hash + Objects.hashCode(this.fontFamily);
        hash = 37 * hash + this.fontSize;
        hash = 37 * hash + Objects.hashCode(this.fontColor);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TextControlModel other = (TextControlModel) obj;
        if (this.isBold != other.isBold) {
            return false;
        }
        if (this.isItalic != other.isItalic) {
            return false;
        }
        if (this.isUnderline != other.isUnderline) {
            return false;
        }
        if (this.fontSize != other.fontSize) {
            return false;
        }
        if (!Objects.equals(this.text, other.text)) {
            return false;
        }
        if (!Objects.equals(this.fontFamily, other.fontFamily)) {
            return false;
        }
        return Objects.equals(this.fontColor, other.fontColor);
    }

}
