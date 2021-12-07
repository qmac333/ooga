package ooga.view.gamescreens;

import ooga.controller.interfaces.UnoDisplayController;
import ooga.view.gamescreens.BasicUnoDisplay;
import ooga.view.subdisplays.BlasterDisplay;

public class BlastUnoDisplay extends BasicUnoDisplay {

  private BlasterDisplay blasterDisplay;

  public BlastUnoDisplay(UnoDisplayController controller, String language, String cssFile) {
    super(controller, language, cssFile);
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
