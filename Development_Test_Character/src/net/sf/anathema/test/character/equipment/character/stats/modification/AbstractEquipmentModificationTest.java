package net.sf.anathema.test.character.equipment.character.stats.modification;

import net.sf.anathema.character.equipment.MagicalMaterial;
import net.sf.anathema.character.equipment.impl.character.model.stats.modification.AccuracyModification;
import net.sf.anathema.character.equipment.impl.character.model.stats.modification.DamageModification;
import net.sf.anathema.character.equipment.impl.character.model.stats.modification.DefenseModification;
import net.sf.anathema.character.equipment.impl.character.model.stats.modification.RangeModification;
import net.sf.anathema.character.equipment.impl.character.model.stats.modification.RateModification;
import net.sf.anathema.character.equipment.impl.character.model.stats.modification.SpeedModification;
import net.sf.anathema.character.equipment.impl.character.model.stats.modification.WeaponStatsType;
import net.sf.anathema.character.generic.rules.IExaltedRuleSet;

import org.junit.Assert;

public abstract class AbstractEquipmentModificationTest {

  protected abstract MagicalMaterial getMagicMaterial();

  protected abstract IExaltedRuleSet getRuleSet();

  protected final void assertAccuracyModification(int expected, int original, WeaponStatsType type) {
    AccuracyModification modification = new AccuracyModification(getMagicMaterial(), getRuleSet());
    Assert.assertEquals(expected, modification.getModifiedValue(original, type));
  }

  protected final void assertRangeModification(int expected, int original, WeaponStatsType type) {
    RangeModification modification = new RangeModification(getMagicMaterial(), getRuleSet());
    Assert.assertEquals(expected, modification.getModifiedValue(original, type));
  }

  protected final void assertDefenseModification(int expected, int original, WeaponStatsType type) {
    DefenseModification modification = new DefenseModification(getMagicMaterial(), getRuleSet());
    Assert.assertEquals(expected, modification.getModifiedValue(original, type));
  }

  protected final void assertSpeedModification(int expected, int original, WeaponStatsType type) {
    SpeedModification modification = new SpeedModification(getMagicMaterial(), getRuleSet());
    Assert.assertEquals(expected, modification.getModifiedValue(original, type));
  }

  protected final void assertDamageModification(int expected, int original, WeaponStatsType type) {
    DamageModification modification = new DamageModification(getMagicMaterial(), getRuleSet());
    Assert.assertEquals(expected, modification.getModifiedValue(original, type));
  }

  protected final void assertRateModification(int expected, int original, WeaponStatsType type) {
    RateModification modification = new RateModification(getMagicMaterial(), getRuleSet());
    Assert.assertEquals(expected, modification.getModifiedValue(original, type));
  }

  protected final void assertSpeedUnmodified() {
    assertSpeedModification(1, 1, WeaponStatsType.Bow);
    assertSpeedModification(1, 1, WeaponStatsType.Thrown);
    assertSpeedModification(1, 1, WeaponStatsType.Melee);
  }


  protected final void assertRangeUnmodified() {
    assertRangeModification(1, 1, WeaponStatsType.Bow);
    assertRangeModification(1, 1, WeaponStatsType.Thrown);
  }

  protected final void assertRateUnmodified() {
    assertRateModification(1, 1, WeaponStatsType.Bow);
    assertRateModification(1, 1, WeaponStatsType.Thrown);
    assertRateModification(1, 1, WeaponStatsType.Melee);
  }

  protected final void assertDamageUnmodified() {
    assertDamageModification(1, 1, WeaponStatsType.Bow);
    assertDamageModification(1, 1, WeaponStatsType.Thrown);
    assertDamageModification(1, 1, WeaponStatsType.Melee);
  }

  protected final void assertAccuracyUnmodified() {
    assertAccuracyModification(1, 1, WeaponStatsType.Bow);
    assertAccuracyModification(1, 1, WeaponStatsType.Thrown);
    assertAccuracyModification(1, 1, WeaponStatsType.Melee);
  }

  protected final void assertDefenseUnmodified() {
    assertDefenseModification(1, 1, WeaponStatsType.Bow);
    assertDefenseModification(1, 1, WeaponStatsType.Thrown);
    assertDefenseModification(1, 1, WeaponStatsType.Melee);
  }
}