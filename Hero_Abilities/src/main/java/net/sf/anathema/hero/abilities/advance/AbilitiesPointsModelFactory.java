package net.sf.anathema.hero.abilities.advance;

import net.sf.anathema.hero.abilities.model.AbilitiesModel;
import net.sf.anathema.hero.initialization.SimpleModelTreeEntry;
import net.sf.anathema.hero.model.HeroModelAutoCollector;
import net.sf.anathema.hero.model.HeroModelFactory;
import net.sf.anathema.hero.points.PointsModel;
import net.sf.anathema.hero.template.TemplateFactory;

@HeroModelAutoCollector
public class AbilitiesPointsModelFactory extends SimpleModelTreeEntry implements HeroModelFactory {

  public AbilitiesPointsModelFactory() {
    super(AbilitiesPointModel.ID, AbilitiesModel.ID, PointsModel.ID);
  }

  @Override
  public AbilitiesPointModel create(TemplateFactory templateFactory, String templateId) {
    return new AbilitiesPointModel();
  }
}
