package com.eafit.herzon.teis.services;

import com.eafit.herzon.teis.models.Cart;
import com.eafit.herzon.teis.models.CartItem;
import com.eafit.herzon.teis.models.CustomUser;
import com.eafit.herzon.teis.models.Jewel;
import java.io.ByteArrayOutputStream;
import java.util.List;


/**
 * Servicio para generar documentos de recibo en formato PDF.
*/
public interface IrecipeGenerator {

  /**
    * Servicio para generar documentos de recibo en formato PDF.
  */
  public ByteArrayOutputStream generateDocument(List<CartItem> cart, CustomUser user);

  /**
    * Servicio para generar documentos de recibo en formato PDF.
  */
  public String getDocumentType();
}
