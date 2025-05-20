package com.eafit.herzon.teis.services;

import com.eafit.herzon.teis.models.CustomUser;
import com.eafit.herzon.teis.models.Jewel;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.imageio.ImageIO;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
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
      PDPage page = new PDPage(PDRectangle.A4);
      document.addPage(page);

      try (PDPageContentStream content = new PDPageContentStream(document, page)) {
        // Estilo de fondo verde elegante
        content.setNonStrokingColor(0, 56, 31); // Verde oscuro (#00381F)
        content.addRect(0, 0, page.getMediaBox().getWidth(), 120);
        content.fill();

        // Logo o título
        content.beginText();
        content.setFont(PDType1Font.HELVETICA_BOLD, 24);
        content.setNonStrokingColor(212, 175, 55); // Dorado (#D4AF37)
        content.newLineAtOffset(50, 750);
        content.showText("HerzonTEIS - Certificado de Autenticidad");
        content.endText();

        if (jewel.getImageUrl() != null && !jewel.getImageUrl().isEmpty()) {
          try {
            BufferedImage bufferedImage = ImageIO.read(new URL(jewel.getImageUrl()));
            PDImageXObject pdImage = LosslessFactory.createFromImage(document, bufferedImage);
            float scale = 0.3f; // Escala de la imagen
            float x = (page.getMediaBox().getWidth() - (pdImage.getWidth() * scale)) / 2;
            content.drawImage(pdImage, 150, 450, 300,
                    300);
          } catch (Exception e) {
            // Si falla la imagen, continuar sin ella
            System.err.println("Error cargando imagen: " + e.getMessage());
          }
        }

        content.beginText();
        content.setFont(PDType1Font.HELVETICA_BOLD, 14);
        content.setNonStrokingColor(0, 0, 0); // Negro
        content.newLineAtOffset(50, 450);
        content.showText("Detalles de la Joya:");
        content.endText();

        content.beginText();
        content.setFont(PDType1Font.HELVETICA, 12);
        content.newLineAtOffset(50, 430);
        content.showText("Nombre: " + jewel.getName());
        content.newLineAtOffset(0, -20);
        content.showText("Categoría: " + jewel.getCategory());
        content.newLineAtOffset(0, -20);
        content.showText("Details: " + jewel.getDetails());
        content.newLineAtOffset(0, -20);
        content.showText("Precio: $" + String.format("%,.2f", jewel.getPrice()));
        content.endText();

        // Información del cliente
        content.beginText();
        content.setFont(PDType1Font.HELVETICA_BOLD, 14);
        content.newLineAtOffset(50, 350);
        content.showText("Información del Cliente:");
        content.endText();

        content.beginText();
        content.setFont(PDType1Font.HELVETICA, 12);
        content.newLineAtOffset(50, 330);
        content.showText("Nombre: " + user.getUsername());
        content.newLineAtOffset(0, -20);
        content.showText("Email: " + user.getEmail());
        content.newLineAtOffset(0, -20);
        content.showText("Fecha: " + LocalDate.now().format(DateTimeFormatter.ISO_DATE));
        content.endText();

        // Pie de página
        content.setNonStrokingColor(0, 56, 31); // Verde oscuro
        content.addRect(0, 0, page.getMediaBox().getWidth(), 40);
        content.fill();

        content.beginText();
        content.setFont(PDType1Font.HELVETICA, 10);
        content.setNonStrokingColor(255, 255, 255); // Blanco
        content.newLineAtOffset(50, 20);
        content.showText("© 2025 HerzonTEIS - Todos los derechos reservados");
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