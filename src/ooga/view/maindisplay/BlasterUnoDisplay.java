package ooga.view.maindisplay;

import ooga.controller.UnoDisplayController;
import ooga.view.BlasterDisplay;

public class BlasterUnoDisplay extends UnoDisplay {

    private BlasterDisplay blasterDisplay;

    public BlasterUnoDisplay(UnoDisplayController controller, String language) {
        super(controller, language);
        this.blasterDisplay = new BlasterDisplay(controller, language);
    }

    @Override
    protected void createScene() {
        super.createScene();
        getUnoDisplay().setTop(blasterDisplay.getDisplayableItem());
    }

    @Override
    public void render() {
        super.render();
        blasterDisplay.update();
    }
}
