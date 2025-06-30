/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.common.textcontrol;

import ie.philb.album.ui.common.font.ApplicationFont;
import java.awt.Color;
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

    private void fireUpdated() {
        for (TextControlChangeListener listener : getListeners()) {
            listener.formatUpdated(this);
        }
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        if (!Objects.equals(text, this.text)) {
            this.text = text;
            fireUpdated();
        }
    }

    public boolean isBold() {
        return isBold;
    }

    public void setBold(boolean isBold) {
        if (this.isBold != isBold) {
            this.isBold = isBold;
            fireUpdated();
        }
    }

    public boolean isItalic() {
        return isItalic;
    }

    public void setItalic(boolean isItalic) {
        if (this.isItalic != isItalic) {
            this.isItalic = isItalic;
            fireUpdated();
        }
    }

    public boolean isUnderline() {
        return isUnderline;
    }

    public void setUnderline(boolean isUnderline) {
        if (this.isUnderline != isUnderline) {
            this.isUnderline = isUnderline;
            fireUpdated();
        }
    }

    public String getFontFamily() {
        return fontFamily;
    }

    public void setFontFamily(String fontFamily) {
        if (!Objects.equals(fontFamily, this.fontFamily)) {
            this.fontFamily = fontFamily;
            fireUpdated();
        }
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        if (this.fontSize != fontSize) {
            this.fontSize = fontSize;
            fireUpdated();
        }
    }

    public Color getFontColor() {
        return fontColor;
    }

    public void setFontColor(Color fontColor) {
        if (!Objects.equals(fontColor, this.fontColor)) {
            this.fontColor = fontColor;
            fireUpdated();
        }
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
