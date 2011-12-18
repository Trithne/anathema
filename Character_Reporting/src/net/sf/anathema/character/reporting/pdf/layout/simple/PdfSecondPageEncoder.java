package net.sf.anathema.character.reporting.pdf.layout.simple;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import net.sf.anathema.character.generic.character.IGenericCharacter;
import net.sf.anathema.character.generic.character.IGenericDescription;
import net.sf.anathema.character.generic.impl.rules.ExaltedEdition;
import net.sf.anathema.character.generic.magic.IMagicStats;
import net.sf.anathema.character.reporting.pdf.rendering.elements.Bounds;
import net.sf.anathema.character.reporting.pdf.rendering.boxes.backgrounds.PdfBackgroundEncoder;
import net.sf.anathema.character.reporting.pdf.rendering.boxes.experience.PdfExperienceEncoder;
import net.sf.anathema.character.reporting.pdf.rendering.general.box.IPdfContentBoxEncoder;
import net.sf.anathema.character.reporting.pdf.rendering.general.box.PdfBoxEncoder;
import net.sf.anathema.character.reporting.pdf.rendering.page.IPdfPageEncoder;
import net.sf.anathema.character.reporting.pdf.rendering.page.IVoidStateFormatConstants;
import net.sf.anathema.character.reporting.pdf.rendering.page.PdfPageConfiguration;
import net.sf.anathema.character.reporting.pdf.rendering.boxes.magic.PdfComboEncoder;
import net.sf.anathema.character.reporting.pdf.rendering.boxes.magic.PdfMagicEncoder;
import net.sf.anathema.character.reporting.pdf.rendering.boxes.magic.PdfGenericCharmEncoder;
import net.sf.anathema.lib.resources.IResources;

import java.util.List;

public class PdfSecondPageEncoder implements IPdfPageEncoder {

  private final BaseFont baseFont;
  private final PdfPageConfiguration configuration;
  private final PdfBoxEncoder boxEncoder;
  private final IResources resources;
  private final SimpleEncodingRegistry encodingRegistry;

  public PdfSecondPageEncoder(IResources resources, SimpleEncodingRegistry encodingRegistry, PdfPageConfiguration configuration) {
    this.resources = resources;
    this.encodingRegistry = encodingRegistry;
    this.baseFont = encodingRegistry.getBaseFont();
    this.configuration = configuration;
    this.boxEncoder = new PdfBoxEncoder(resources, baseFont);
  }

  public void encode(Document document, PdfContentByte directContent, IGenericCharacter character, IGenericDescription description) throws  DocumentException {
    float distanceFromTop = 0;
    float languageHeight = 60;
    float backgroundHeight = 104;
    float experienceHeight = backgroundHeight - languageHeight - IVoidStateFormatConstants.PADDING;
    encodeBackgrounds(directContent, character,description, distanceFromTop, backgroundHeight);
    encodePossessions(directContent, character,description, distanceFromTop, backgroundHeight);
    encodeLanguages(directContent, character,description, distanceFromTop, languageHeight);
    distanceFromTop += languageHeight + IVoidStateFormatConstants.PADDING;
    encodeExperience(directContent, character,description, distanceFromTop, experienceHeight);
    distanceFromTop += experienceHeight + IVoidStateFormatConstants.PADDING;
    float comboHeight = encodeCombos(directContent, character, distanceFromTop);
    if (comboHeight > 0) {
      distanceFromTop += comboHeight + IVoidStateFormatConstants.PADDING;
    }
    if (character.getTemplate().getEdition() == ExaltedEdition.SecondEdition) {
      float genericCharmsHeight = encodeGenericCharms(directContent, character,description, distanceFromTop);
      if (genericCharmsHeight != 0) {
        distanceFromTop += genericCharmsHeight + IVoidStateFormatConstants.PADDING;
      }
    }

    float remainingHeight = configuration.getContentHeight() - distanceFromTop;
    List<IMagicStats> printMagic = PdfMagicEncoder.collectPrintMagic(character);
    encodeCharms(directContent, printMagic, distanceFromTop, remainingHeight);
    while (!printMagic.isEmpty()) {
      document.newPage();
      encodeCharms(directContent, printMagic, 0, configuration.getContentHeight());
    }
  }

  private float encodeCombos(PdfContentByte directContent, IGenericCharacter character, float distanceFromTop) throws DocumentException {
    Bounds restOfPage = new Bounds(configuration.getLeftX(), configuration.getLowerContentY(), configuration.getContentWidth(), configuration.getContentHeight() - distanceFromTop);
    return new PdfComboEncoder(resources, baseFont).encodeCombos(directContent, character, restOfPage);
  }

  private float encodeExperience(PdfContentByte directContent, IGenericCharacter character, IGenericDescription description, float distanceFromTop, float height) throws DocumentException {
    Bounds bounds = configuration.getThirdColumnRectangle(distanceFromTop, height);
    IPdfContentBoxEncoder encoder = new PdfExperienceEncoder(resources, baseFont);
    boxEncoder.encodeBox(directContent, encoder, character,description, bounds);
    return height;
  }

  private float encodeLanguages(PdfContentByte directContent, IGenericCharacter character, IGenericDescription description, float distanceFromTop, float height) throws DocumentException {
    Bounds bounds = configuration.getThirdColumnRectangle(distanceFromTop, height);
    IPdfContentBoxEncoder encoder = encodingRegistry.getLinguisticsEncoder();
    boxEncoder.encodeBox(directContent, encoder, character,description, bounds);
    return height;
  }

  private float encodeBackgrounds(PdfContentByte directContent, IGenericCharacter character, IGenericDescription description, float distanceFromTop, float height) throws DocumentException {
    Bounds backgroundBounds = configuration.getFirstColumnRectangle(distanceFromTop, height, 1);
    IPdfContentBoxEncoder encoder = new PdfBackgroundEncoder(resources, baseFont);
    boxEncoder.encodeBox(directContent, encoder, character,description, backgroundBounds);
    return height;
  }

  private float encodePossessions(PdfContentByte directContent, IGenericCharacter character, IGenericDescription description, float distanceFromTop, float height) throws DocumentException {
    Bounds bounds = configuration.getSecondColumnRectangle(distanceFromTop, height, 1);
    IPdfContentBoxEncoder encoder = encodingRegistry.getPossessionsEncoder();
    boxEncoder.encodeBox(directContent, encoder, character,description, bounds);
    return height;
  }

  private float encodeGenericCharms(PdfContentByte directContent, IGenericCharacter character, IGenericDescription description, float distanceFromTop) throws DocumentException {
    if (character.getGenericCharmStats().length > 0) {
      float height = 55 + character.getGenericCharmStats().length * 11;
      Bounds bounds = configuration.getFirstColumnRectangle(distanceFromTop, height, 3);
      IPdfContentBoxEncoder encoder = new PdfGenericCharmEncoder(resources, baseFont);
      boxEncoder.encodeBox(directContent, encoder, character, description, bounds);
      return height;
    }
    else {
      return 0;
    }
  }

  private float encodeCharms(PdfContentByte directContent, List<IMagicStats> printMagic, float distanceFromTop, float height) throws DocumentException {
    Bounds bounds = configuration.getFirstColumnRectangle(distanceFromTop, height, 3);
    IPdfContentBoxEncoder encoder = new PdfMagicEncoder(resources, baseFont, printMagic);
    boxEncoder.encodeBox(directContent, encoder, null, null, bounds);
    return height;
  }
}