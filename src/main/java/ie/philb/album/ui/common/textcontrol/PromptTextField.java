/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.common.textcontrol;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JTextField;

/**
 *
 * @author philb
 */
class PromptTextField extends JTextField {

    private String prompt;

    public PromptTextField(String prompt) {
        super();
        this.prompt = prompt;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        try {
            g.setFont(new Font("Arial", Font.PLAIN, 12));
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(Color.lightGray);
            g2d.drawString(prompt, 5, 5);
        } catch (Exception ignored) {
        }
    }
}
