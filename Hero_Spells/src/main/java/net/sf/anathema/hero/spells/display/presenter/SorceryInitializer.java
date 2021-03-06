package net.sf.anathema.hero.spells.display.presenter;

import net.sf.anathema.character.main.view.SectionView;
import net.sf.anathema.framework.IApplicationModel;
import net.sf.anathema.framework.environment.Environment;
import net.sf.anathema.hero.display.presenter.HeroModelInitializer;
import net.sf.anathema.hero.display.presenter.RegisteredInitializer;
import net.sf.anathema.hero.model.Hero;
import net.sf.anathema.hero.spells.model.CircleModel;
import net.sf.anathema.hero.spells.model.SorceryModel;
import net.sf.anathema.framework.environment.dependencies.Weight;

import static net.sf.anathema.hero.display.HeroModelGroup.Magic;

@RegisteredInitializer(Magic)
@Weight(weight = 200)
public class SorceryInitializer implements HeroModelInitializer {
  private IApplicationModel model;

  public SorceryInitializer(IApplicationModel model) {
    this.model = model;
  }

  @Override
  public void initialize(SectionView sectionView, Hero hero, Environment environment) {
    boolean canLeanSorcery = hero.getTemplate().getMagicTemplate().getSpellMagic().canLearnSorcery();
    if (canLeanSorcery) {
      String titleKey = "CardView.CharmConfiguration.Spells.Title";
      CircleModel circleModel = new SorceryModel(hero);
      new SpellInitializer(model, titleKey, circleModel).initialize(sectionView, hero, environment);
    }
  }
}
