package com.eafit.herzon.teis.services;


import com.eafit.herzon.teis.models.CustomUser;
import com.eafit.herzon.teis.models.Jewel;
import java.io.ByteArrayOutputStream;

/**
 * Servicio para generar documentos de recibo en formato PDF.
*/
public interface IdocumentGenerator {

  /**
     * Servicio para generar documentos de recibo en formato PDF.
  */
  ByteArrayOutputStream generateDocument(Jewel jewel, CustomUser user);

  /**
     * Servicio para generar documentos de recibo en formato PDF.
  */
  String getDocumentTipe();
}
