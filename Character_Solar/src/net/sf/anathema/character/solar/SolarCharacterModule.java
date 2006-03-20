package net.sf.anathema.character.solar;

import net.sf.anathema.character.generic.framework.ICharacterGenerics;
import net.sf.anathema.character.generic.framework.additionaltemplate.IAdditionalViewFactory;
import net.sf.anathema.character.generic.framework.additionaltemplate.model.IAdditionalModelFactory;
import net.sf.anathema.character.generic.framework.additionaltemplate.persistence.IAdditionalPersisterFactory;
import net.sf.anathema.character.generic.framework.module.CharacterGenericsModuleAdapter;
import net.sf.anathema.character.generic.impl.caste.CasteCollection;
import net.sf.anathema.character.generic.impl.magic.persistence.CharmCache;
import net.sf.anathema.character.generic.magic.charms.special.ISpecialCharm;
import net.sf.anathema.character.generic.template.ITemplateRegistry;
import net.sf.anathema.character.generic.type.CharacterType;
import net.sf.anathema.character.solar.additional.AdditionalSolarRules;
import net.sf.anathema.character.solar.caste.SolarCaste;
import net.sf.anathema.character.solar.reporting.SolarVoidStateReportTemplate;
import net.sf.anathema.character.solar.template.ISolarSpecialCharms;
import net.sf.anathema.character.solar.template.SolarTemplate;
import net.sf.anathema.character.solar.virtueflaw.SolarVirtueFlawModelFactory;
import net.sf.anathema.character.solar.virtueflaw.SolarVirtueFlawPersisterFactory;
import net.sf.anathema.character.solar.virtueflaw.SolarVirtueFlawTemplate;
import net.sf.anathema.character.solar.virtueflaw.SolarVirtueFlawViewFactory;
import net.sf.anathema.lib.exception.PersistenceException;
import net.sf.anathema.lib.logging.Logger;
import net.sf.anathema.lib.registry.IRegistry;
import net.sf.anathema.lib.resources.IResources;

public class SolarCharacterModule extends CharacterGenericsModuleAdapter {

  private final Logger logger = Logger.getLogger(SolarCharacterModule.class);
  private final AdditionalSolarRules additionalSolarRules = new AdditionalSolarRules();

  @Override
  public void registerCommonData(ICharacterGenerics characterGenerics) {
    characterGenerics.getCharmProvider().setSpecialCharms(
        CharacterType.SOLAR,
        new ISpecialCharm[] { ISolarSpecialCharms.OX_BODY_TECHNIQUE });
    characterGenerics.getAdditionalTemplateParserRegistry().register(
        SolarVirtueFlawTemplate.ID,
        new SolarVirtueFlawParser());
    characterGenerics.getCasteCollectionRegistry().register(
        CharacterType.SOLAR,
        new CasteCollection(SolarCaste.values()));
  }

  @Override
  public void addCharacterTemplates(ICharacterGenerics characterGenerics) {
    registerParsedTemplate(characterGenerics, "template/Solar.template"); //$NON-NLS-1$
    // CharmCache charmProvider = CharmCache.getInstance();
    // registerSolarTemplate(characterGenerics.getTemplateRegistry(), charmProvider);
  }

  private void registerSolarTemplate(ITemplateRegistry templateRegistry, CharmCache charmProvider) {
    try {
      templateRegistry.register(new SolarTemplate(charmProvider, additionalSolarRules));
    }
    catch (PersistenceException exception) {
      logger.error("Solar Charms not found", exception); //$NON-NLS-1$
    }
  }

  @Override
  public void addReportTemplates(ICharacterGenerics generics, IResources resources) {
    generics.getReportTemplateRegistry().add(new SolarVoidStateReportTemplate(resources));
  }

  @Override
  public void addAdditionalTemplateData(ICharacterGenerics characterGenerics) {
    IRegistry<String, IAdditionalModelFactory> additionalModelFactoryRegistry = characterGenerics.getAdditionalModelFactoryRegistry();
    String templateId = SolarVirtueFlawTemplate.ID;
    additionalModelFactoryRegistry.register(templateId, new SolarVirtueFlawModelFactory());
    IRegistry<String, IAdditionalViewFactory> additionalViewFactoryRegistry = characterGenerics.getAdditionalViewFactoryRegistry();
    additionalViewFactoryRegistry.register(templateId, new SolarVirtueFlawViewFactory());
    IRegistry<String, IAdditionalPersisterFactory> persisterFactory = characterGenerics.getAdditonalPersisterFactoryRegistry();
    persisterFactory.register(templateId, new SolarVirtueFlawPersisterFactory());
  }
}