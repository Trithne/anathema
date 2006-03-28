package net.sf.anathema.character.impl.model.charm.combo;

import net.sf.anathema.character.generic.magic.ICharm;
import net.sf.anathema.character.generic.magic.charms.CharmType;
import net.sf.anathema.character.generic.magic.charms.ICharmTypeVisitor;

public class SupplementalCharmComboRules extends AbstractComboRules {

  public boolean isComboLegal(final ICharm supplementalCharm, final ICharm otherCharm) {
    final boolean[] legal = new boolean[1];
    otherCharm.getCharmType().accept(new ICharmTypeVisitor() {
      public void visitSimple(CharmType visitedType) {
        legal[0] = false;
      }

      public void visitExtraAction(CharmType visitedType) {
        legal[0] = false;
      }

      public void visitReflexive(CharmType visitedType) {
        legal[0] = true;
      }

      public void visitSupplemental(CharmType visitedType) {
        boolean samePrerequisite = haveSamePrerequisite(supplementalCharm, otherCharm);
        boolean attributePrerequisites = haveAttributePrerequisites(supplementalCharm, otherCharm);
        legal[0] = samePrerequisite || attributePrerequisites;
      }

      public void visitSpecial(CharmType visitedType) {
        // TODO Auto-generated method stub
      }
    });
    return legal[0];
  }
}