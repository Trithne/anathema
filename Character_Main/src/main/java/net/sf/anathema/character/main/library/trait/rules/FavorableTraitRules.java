package net.sf.anathema.character.main.library.trait.rules;

import net.sf.anathema.character.main.traits.ITraitTemplate;
import net.sf.anathema.character.main.traits.TraitType;
import net.sf.anathema.hero.model.Hero;

public class FavorableTraitRules extends TraitRules implements IFavorableTraitRules {

  public FavorableTraitRules(TraitType traitType, ITraitTemplate template, Hero hero) {
    super(traitType, template, hero);
  }

  @Override
  public boolean isRequiredFavored() {
    return getTemplate().isRequiredFavored();
  }
}