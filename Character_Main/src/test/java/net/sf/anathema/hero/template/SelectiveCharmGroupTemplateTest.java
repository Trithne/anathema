package net.sf.anathema.hero.template;

import net.sf.anathema.character.main.magic.parser.charms.SelectiveCharmGroupTemplate;
import org.junit.Test;

public class SelectiveCharmGroupTemplateTest {

  @Test
  public void allowsThresholdsEqualToNumberOfChoices() throws Exception {
    String[] ids = new String[5];
    new SelectiveCharmGroupTemplate(ids, 5, "foo");
  }
}
