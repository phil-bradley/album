package ie.philb.album.ui.common;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.awt.GridBagConstraints;
import java.awt.Insets;

/**
 *
 * @author Philip.Bradley
 */
public class GridBagCellConstraints extends GridBagConstraints {

    private static final long serialVersionUID = 1L;

    public GridBagCellConstraints() {
        this(0, 0);
    }

    public GridBagCellConstraints(int x, int y) {
        this(x, y, GridBagConstraints.NORTH, GridBagConstraints.NONE);
    }

    public GridBagCellConstraints(int x, int y, int anchor) {
        this(x, y, anchor, GridBagConstraints.NONE);
    }

    public GridBagCellConstraints(int x, int y, int anchor, int fill) {
        this(x, y, anchor, fill, new Insets(0, 0, 0, 0));
    }

    public GridBagCellConstraints(int x, int y, int anchor, int fill, int insetSize) {
        this(x, y, anchor, fill, new Insets(insetSize, insetSize, insetSize, insetSize));
    }

    public GridBagCellConstraints(int x, int y, int anchor, int fill, Insets insets) {
        this(x, y, anchor, fill, insets, 0);
    }

    public GridBagCellConstraints(int x, int y, int anchor, int fill, Insets insets, double weight) {
        super(x, y, 1, 1, weight, weight, anchor, fill, insets, 0, 0);
    }

    public GridBagCellConstraints x(int x) {
        this.gridx = x;
        return this;
    }

    public GridBagCellConstraints y(int y) {
        this.gridy = y;
        return this;
    }

    public GridBagCellConstraints xy(int x, int y) {
        this.gridx = x;
        this.gridy = y;
        return this;
    }

    public GridBagCellConstraints incx() {
        this.gridx++;
        return this;
    }

    public GridBagCellConstraints incy() {
        this.gridy++;
        return this;
    }

    public GridBagCellConstraints setWeight(double w) {
        if (w > 1) {
            w = 1;
        }
        if (w < 0) {
            w = 0;
        }
        weightx = w;
        weighty = w;

        return (this);
    }

    public GridBagCellConstraints weightx(double w) {
        return setWeightX(w);
    }

    public GridBagCellConstraints weighty(double w) {
        return setWeightY(w);
    }

    public GridBagCellConstraints setWeightX(double w) {
        if (w > 1) {
            w = 1;
        }
        if (w < 0) {
            w = 0;
        }
        weightx = w;

        return (this);
    }

    public GridBagCellConstraints setWeightY(double w) {
        if (w > 1) {
            w = 1;
        }
        if (w < 0) {
            w = 0;
        }
        weighty = w;

        return (this);
    }

    public GridBagCellConstraints weight(double w) {
        return this.setWeight(w);
    }

    public GridBagCellConstraints weight(double weightx, double weighty) {
        return this.setWeight(weightx, weighty);
    }

    public GridBagCellConstraints setWeight(double weightx, double weighty) {
        this.weightx(weightx);
        this.weighty(weighty);

        return (this);
    }

    public GridBagCellConstraints anchor(int anchor) {
        this.anchor = anchor;
        return this;
    }

    public GridBagCellConstraints gridx(int x) {
        this.gridx = x;
        return this;
    }

    public GridBagCellConstraints grid(int x, int y) {
        this.gridx = x;
        this.gridy = y;
        return this;
    }

    public GridBagCellConstraints gridy(int y) {
        this.gridy = y;
        return this;
    }

    public GridBagCellConstraints gridwidth(int w) {
        this.gridwidth = w;
        return this;
    }

    public GridBagCellConstraints gridheight(int h) {
        this.gridheight = h;
        return this;
    }

    public GridBagCellConstraints width(int w) {
        return gridwidth(w);
    }

    public GridBagCellConstraints height(int h) {
        return gridheight(h);
    }

    public GridBagCellConstraints fill(int f) {
        this.fill = f;
        return this;
    }

    public GridBagCellConstraints ipad(int p) {
        this.ipadx = p;
        this.ipady = p;
        return this;
    }

    public GridBagCellConstraints setInsetSize(int s) {
        insets = new Insets(s, s, s, s);
        return (this);
    }

    public GridBagCellConstraints inset(int s) {
        return this.setInsetSize(s);
    }

    public GridBagCellConstraints insetHorizontal(int s) {
        return this.insets(new Insets(insets.top, s, insets.bottom, s));
    }

    public GridBagCellConstraints insetVertical(int s) {
        return this.insets(new Insets(s, insets.left, s, insets.right));
    }

    public GridBagCellConstraints inset(int horizontal, int vertical) {
        return this.insets(new Insets(vertical, horizontal, vertical, horizontal));
    }

    public GridBagCellConstraints insetLeft(int s) {
        this.insets.left = s;
        return this;
    }

    public GridBagCellConstraints insetRight(int s) {
        this.insets.right = s;
        return this;
    }

    public GridBagCellConstraints insetTop(int s) {
        this.insets.top = s;
        return this;
    }

    public GridBagCellConstraints insetBottom(int s) {
        this.insets.bottom = s;
        return this;
    }

    public GridBagCellConstraints inset(int top, int left, int bottom, int right) {
        return this.insets(new Insets(top, left, bottom, right));
    }

    public GridBagCellConstraints insets(Insets insets) {
        this.insets = insets;
        return this;
    }

    public GridBagCellConstraints setPadding(int x, int y) {
        this.ipadx = x;
        this.ipady = y;

        return this;
    }

    public GridBagCellConstraints pad(int x, int y) {
        return this.setPadding(x, y);
    }

    public GridBagCellConstraints pad(int x) {
        return this.setPadding(x, x);
    }

    public GridBagCellConstraints padx(int x) {
        return this.setPadding(x, this.ipady);
    }

    public GridBagCellConstraints pady(int y) {
        return this.setPadding(this.ipadx, y);
    }

    public GridBagCellConstraints fillNone() {
        return this.fill(GridBagCellConstraints.NONE);
    }

    public GridBagCellConstraints fillHorizontal() {
        return this.fill(GridBagCellConstraints.HORIZONTAL);
    }

    public GridBagCellConstraints fillHorizontal(int weightx) {
        return fillHorizontal().weightx(weightx);
    }

    public GridBagCellConstraints fillVertical() {
        return this.fill(GridBagCellConstraints.VERTICAL);
    }

    public GridBagCellConstraints fillVertical(int weighty) {
        return fillVertical().weighty(weighty);
    }

    public GridBagCellConstraints fillBoth() {
        return this.fill(GridBagCellConstraints.BOTH);
    }

    public GridBagCellConstraints fillBoth(int weight) {
        return fillBoth().weight(weight);
    }

    public GridBagCellConstraints anchorNorth() {
        return this.anchor(GridBagCellConstraints.NORTH);
    }

    public GridBagCellConstraints anchorNorthEast() {
        return this.anchor(GridBagCellConstraints.NORTHEAST);
    }

    public GridBagCellConstraints anchorEast() {
        return this.anchor(GridBagCellConstraints.EAST);
    }

    public GridBagCellConstraints anchorSouthEast() {
        return this.anchor(GridBagCellConstraints.SOUTHEAST);
    }

    public GridBagCellConstraints anchorSouth() {
        return this.anchor(GridBagCellConstraints.SOUTH);
    }

    public GridBagCellConstraints anchorSouthWest() {
        return this.anchor(GridBagCellConstraints.SOUTHWEST);
    }

    public GridBagCellConstraints anchorWest() {
        return this.anchor(GridBagCellConstraints.WEST);
    }

    public GridBagCellConstraints anchorNorthWest() {
        return this.anchor(GridBagCellConstraints.NORTHWEST);
    }

    public GridBagCellConstraints anchorCenter() {
        return this.anchor(GridBagCellConstraints.CENTER);
    }

    public GridBagCellConstraints center() {
        return this.anchor(GridBagCellConstraints.CENTER);
    }
}
