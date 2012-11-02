package net.sf.anathema.character.equipment.item;

import net.sf.anathema.character.equipment.MaterialComposition;
import net.sf.anathema.character.equipment.item.model.IEquipmentStatsCreationFactory;
import net.sf.anathema.character.equipment.item.model.IEquipmentTemplateEditModel;
import net.sf.anathema.character.generic.equipment.weapon.IEquipmentStats;
import net.sf.anathema.framework.presenter.resources.BasicUi;
import net.sf.anathema.lib.gui.action.SmartAction;
import net.sf.anathema.lib.resources.IResources;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

public final class AddNewStatsAction extends SmartAction {
  private final IEquipmentStatsCreationFactory statsFactory;
  private final IResources resources;
  private final IEquipmentTemplateEditModel editModel;

  public AddNewStatsAction(IResources resources, IEquipmentTemplateEditModel editModel,
                           IEquipmentStatsCreationFactory statsFactory) {
    super(new BasicUi(resources).getAddIcon());
    this.resources = resources;
    this.editModel = editModel;
    this.statsFactory = statsFactory;
    setToolTipText(resources.getString("Equipment.Creation.Stats.AddActionTooltip")); //$NON-NLS-1$
  }

  @Override
  protected void execute(Component parentComponent) {
    List<String> definedNames = new ArrayList<>();
    for (IEquipmentStats stats : editModel.getStats()) {
      definedNames.add(stats.getName().getId());
    }
    String[] nameArray = definedNames.toArray(new String[definedNames.size()]);
    MaterialComposition materialComposition = editModel.getMaterialComposition();
    IEquipmentStats equipmentStats = statsFactory.createNewStats(parentComponent, resources, nameArray, materialComposition);
    if (equipmentStats == null) {
      return;
    }
    editModel.addStatistics(equipmentStats);
  }
}