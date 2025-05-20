package com.eafit.herzon.teis.services;

import com.eafit.herzon.teis.models.CartItem;
import com.eafit.herzon.teis.models.CustomUser;
import com.eafit.herzon.teis.models.Jewel;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

/**
 * Servicio para generar documentos de recibo en formato PDF.
 */
@Service("recipeGeneratorService")
public class RecipeGeneratorService  implements IrecipeGenerator {

  /**
     * Genera un documento de recibo en formato PDF.
     *
     * @param cartItems La joya para la cual se genera el recibo
     * @param user El usuario que recibe el recibo
     * @return ByteArrayOutputStream con el contenido del PDF generado
  */
  @Override
  public ByteArrayOutputStream generateDocument(List<CartItem> cartItems, CustomUser user) {
    try (PDDocument document = new PDDocument()) {
      PDPage page = new PDPage(PDRectangle.A4);
      document.addPage(page);

      try (PDPageContentStream content = new PDPageContentStream(document, page)) {
        // --- ENCABEZADO --- //
        content.setNonStrokingColor(0, 56, 31); // Verde oscuro #00381F
        content.addRect(0, page.getMediaBox().getHeight() - 80,
                page.getMediaBox().getWidth(), 80);
        content.fill();

        content.beginText();
        content.setFont(PDType1Font.HELVETICA_BOLD, 20);
        content.setNonStrokingColor(212, 175, 55); // Dorado #D4AF37
        content.newLineAtOffset(50, page.getMediaBox().getHeight() - 50);
        content.showText("HERZONTEIS - RECIBO DE COMPRA");
        content.endText();

        // --- INFORMACIÓN DEL CLIENTE --- //
        content.beginText();
        content.setFont(PDType1Font.HELVETICA_BOLD, 12);
        content.setNonStrokingColor(0, 0, 0); // Negro
        content.newLineAtOffset(50, 650);
        content.showText("Cliente: " + user.getUsername());
        content.newLineAtOffset(0, -20);
        content.showText("Email: " + user.getEmail());
        content.newLineAtOffset(0, -20);
        content.showText("Fecha: " + LocalDate.now()
                        .format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        content.newLineAtOffset(0, -20);
        content.showText("N° Recibo: "
                + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        content.endText();

        // --- TABLA DE PRODUCTOS --- //
        // Encabezado de tabla
        content.setNonStrokingColor(212, 175, 55); // Dorado
        content.addRect(50, 550, page.getMediaBox().getWidth() - 100, 30);
        content.fill();

        content.beginText();
        content.setFont(PDType1Font.HELVETICA_BOLD, 12);
        content.setNonStrokingColor(0, 56, 31); // Verde oscuro
        content.newLineAtOffset(60, 555);
        content.showText("PRODUCTO");
        content.newLineAtOffset(300, 0);
        content.showText("CANTIDAD");
        content.newLineAtOffset(100, 0);
        content.showText("PRECIO C/U");
        content.newLineAtOffset(100, 0);
        content.showText("TOTAL");
        content.endText();

        // Filas de productos
        float yposition = 520;
        double totalCompra = 0;

        for (CartItem item : cartItems) {
          Jewel jewel = item.getJewel();
          double totalItem = jewel.getPrice() * item.getQuantity();
          totalCompra += totalItem;

          content.beginText();
          content.setFont(PDType1Font.HELVETICA, 10);
          content.setNonStrokingColor(0, 0, 0); // Negro
          content.newLineAtOffset(60, yposition);
          content.showText(jewel.getName());
          content.newLineAtOffset(300, 0);
          content.showText(String.valueOf(item.getQuantity())); // Mostrar cantidad real
          content.newLineAtOffset(100, 0);
          content.showText("$" + String.format("%,.2f", jewel.getPrice()));
          content.newLineAtOffset(100, 0);
          content.showText("$" + String.format("%,.2f", totalItem)); // Total por item
          content.endText();

          yposition -= 20;

          // Línea divisoria
          content.setNonStrokingColor(200, 200, 200); // Gris claro
          content.moveTo(50, yposition + 5);
          content.lineTo(page.getMediaBox().getWidth() - 50, yposition + 5);
          content.stroke();
          yposition -= 10;
        }

        // --- TOTALES --- //
        content.beginText();
        content.setFont(PDType1Font.HELVETICA_BOLD, 14);
        content.setNonStrokingColor(0, 0, 0); // Negro
        content.newLineAtOffset(page.getMediaBox().getWidth() - 180, 150);
        content.showText("TOTAL: $" + String.format("%,.2f", totalCompra));
        content.endText();

        // --- PIE DE PÁGINA --- //
        content.setNonStrokingColor(0, 56, 31); // Verde oscuro
        content.addRect(0, 0, page.getMediaBox().getWidth(), 60);
        content.fill();

        content.beginText();
        content.setFont(PDType1Font.HELVETICA, 10);
        content.setNonStrokingColor(255, 255, 255); // Blanco
        content.newLineAtOffset(50, 20);
        content.showText("Gracias por su compra - HerzonTEIS Joyería Suiza a tu alcance");
        content.showText("© 2025 HerzonTEIS - Todos los derechos reservados");
        content.newLineAtOffset(0, -15);
        content.showText("Tel: +57 123 456 7890 | info@herzonteis.com");
        content.endText();

      } catch (IOException e) {
        throw new RuntimeException("Error al generar contenido del PDF", e);
      }

      ByteArrayOutputStream out = new ByteArrayOutputStream();
      document.save(out);
      return out;
    } catch (IOException e) {
      throw new RuntimeException("Error al generar PDF", e);
    }
  }

  /**
     * Obtiene el tipo de documento generado por este servicio.
     *
     * @return String que representa el tipo de documento
  */
  public String getDocumentType() {
    return "RECIBO_COMPRA";
  }
}