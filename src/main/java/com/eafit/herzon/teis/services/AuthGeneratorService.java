package com.eafit.herzon.teis.services;

import com.eafit.herzon.teis.models.CustomUser;
import com.eafit.herzon.teis.models.Jewel;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;


/**
    * Genera un documento de recibo en formato PDF.
*/
@Service("AuthGeneratorService")
public class AuthGeneratorService implements  IdocumentGenerator {


  /**
     * Genera un documento de recibo en formato PDF.
     *
     * @param jewel La joya para la cual se genera el recibo
     * @param user El usuario que recibe el recibo
     * @return ByteArrayOutputStream con el contenido del PDF generado
  */
  @Override
  public ByteArrayOutputStream generateDocument(Jewel jewel, CustomUser user) {
    try (PDDocument document = new PDDocument()) {
      PDPage page = new PDPage();
      document.addPage(page);

      try (PDPageContentStream content = new PDPageContentStream(document, page)) {
        content.beginText();
        content.newLineAtOffset(100, 700);
        content.showText("Documento para: " + user.getUsername());
        content.showText("Joya: " + jewel.getName());
        content.endText();
      }

      ByteArrayOutputStream out = new ByteArrayOutputStream();
      document.save(out);
      return out;
    } catch (IOException e) {
      throw new RuntimeException("Error al generar PDF", e);
    }
  }

  @Override
  public String getDocumentTipe() {
    return "";
  }

}