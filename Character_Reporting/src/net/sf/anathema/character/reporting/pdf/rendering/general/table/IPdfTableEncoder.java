package net.sf.anathema.character.reporting.pdf.rendering.general.table;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfContentByte;
import net.sf.anathema.character.generic.character.IGenericCharacter;
import net.sf.anathema.character.reporting.pdf.rendering.elements.Bounds;

public interface IPdfTableEncoder {

  public float encodeTable(PdfContentByte directContent, IGenericCharacter character, Bounds bounds) throws DocumentException;

  public boolean hasContent(IGenericCharacter character);

}