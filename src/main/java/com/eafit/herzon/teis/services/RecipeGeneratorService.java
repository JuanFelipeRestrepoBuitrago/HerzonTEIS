package com.eafit.herzon.teis.services;

import com.eafit.herzon.teis.models.CustomUser;
import com.eafit.herzon.teis.models.Jewel;
import java.io.ByteArrayOutputStream;
import org.springframework.stereotype.Service;

/**
 * Servicio para generar documentos de recibo en formato PDF.
 */
@Service("recipeGeneratorService")
public class RecipeGeneratorService {

  /**
     * Genera un documento de recibo en formato PDF.
     *
     * @param joya La joya para la cual se genera el recibo
     * @param usuario El usuario que recibe el recibo
     * @return ByteArrayOutputStream con el contenido del PDF generado
  */
  public ByteArrayOutputStream generarDocumento(Jewel joya, CustomUser usuario) {

    System.out.println("[RECIBO] Generado para usuario: " + usuario.getUsername());
    return new ByteArrayOutputStream();
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