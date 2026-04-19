/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.common.fields;

import ie.philb.album.util.StringUtils;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Point;
import javax.swing.JTextField;

/**
 *
 * @author philb
 */
public abstract class PromptField extends JTextField {

    private String prompt = "";
    private String promptPrefix = "";
    private String promptSuffix = "";
    private PromptAlignment promptAlignment = PromptAlignment.LEADING;
    private boolean isPromptEnabled = true;
    private boolean hidePromptOnInput = true;

    protected void setPromptEnabled(boolean enabled) {
        this.isPromptEnabled = enabled;
    }

    private void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    private void setPromptPrefix(String prefix) {
        this.promptPrefix = prefix;
    }

    private void setPromptSuffix(String suffix) {
        this.promptSuffix = suffix;
    }

    private void setPromptAlignment(PromptAlignment alignment) {
        this.promptAlignment = alignment;
    }

    private void setHidePromptOnInput(boolean hide) {
        this.hidePromptOnInput = hide;
    }

    protected abstract String getDefaultPrompt();

    protected String getPromptMessage() {

        if (!isPromptEnabled) {
            return "";
        }

        String promptMessage = StringUtils.hasValue(prompt) ? prompt : getDefaultPrompt();
        return promptPrefix + promptMessage + promptSuffix;
    }

    public PromptSetter prompt() {
        setPromptEnabled(true);
        return new PromptSetter(this);
    }

    public class PromptSetter {

        private final PromptField field;

        public PromptSetter(PromptField field) {
            this.field = field;
        }

        public PromptSetter withPrefix(String prefix) {
            field.setPromptPrefix(prefix);
            return this;
        }

        public PromptSetter withSuffix(String suffix) {
            field.setPromptSuffix(suffix);
            return this;
        }

        public PromptSetter withAlignment(PromptAlignment alignment) {
            field.setPromptAlignment(alignment);
            return this;
        }

        public PromptSetter withPrompt(String prompt) {
            field.setPrompt(prompt);
            return this;
        }

        public PromptSetter withHideOnInput(boolean hide) {
            field.setHidePromptOnInput(hide);
            return this;
        }
    }

    private Point getPromptPosition(Graphics2D g2, String promptMessage) {

        Insets insets = getInsets();
        FontMetrics fm = g2.getFontMetrics();

        int textWidth = fm.stringWidth(promptMessage);

        int x;
        if (promptAlignment == PromptAlignment.LEADING) {
            x = insets.left + 2;
        } else {
            x = getWidth() - insets.right - textWidth - 2;
        }

        int y = getHeight() / 2 + fm.getAscent() / 2 - 2;

        return new Point(x, y);
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        boolean hidePrompt = StringUtils.hasValue(getText()) && hidePromptOnInput;
        String promptMessage = getPromptMessage();

        Font font = new Font("sansserif", Font.PLAIN, 12);
                
        if (StringUtils.hasValue(promptMessage) && !hidePrompt) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setColor(Color.LIGHT_GRAY);
            g2.setFont(font);
            
            Point position = getPromptPosition(g2, promptMessage);

            g2.drawString(promptMessage, position.x, position.y);
            g2.dispose();
        }

    }
}
