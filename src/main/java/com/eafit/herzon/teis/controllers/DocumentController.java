package com.eafit.herzon.teis.controllers;

import com.eafit.herzon.teis.models.CustomUser;
import com.eafit.herzon.teis.models.Jewel;
import com.eafit.herzon.teis.models.Order;
import com.eafit.herzon.teis.repositories.JewelRepository;
import com.eafit.herzon.teis.repositories.OrderRepository;
import com.eafit.herzon.teis.repositories.UserRepository;
import com.eafit.herzon.teis.services.IdocumentGenerator;
import com.eafit.herzon.teis.services.IrecipeGenerator;
import jakarta.persistence.EntityNotFoundException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador que expone los endpoints para generar documentos PDF como
 * certificados de autenticidad y recibos de joyas.
*/
@RestController
@RequestMapping("/api/documents")
public class DocumentController {

  private final IdocumentGenerator documentGenerator;
  private final IrecipeGenerator recipeGenerator;
  private final JewelRepository jewelRepository;
  private final UserRepository userRepository;
  private final OrderRepository orderRepository;

  /**
     * Constructor que inicializa los servicios y repositorios necesarios.
     *
     * @param documentGenerator interface para generar recibos
     * @param recipeGenerator   interface para generar certificados
     * @param jewelRepository        repositorio de joyas
     * @param userRepository         repositorio de usuarios
  */
  public DocumentController(
            IdocumentGenerator documentGenerator,
            IrecipeGenerator recipeGenerator,
            JewelRepository jewelRepository,
            UserRepository userRepository,
            OrderRepository orderRepository) {
    this.documentGenerator = documentGenerator;
    this.recipeGenerator = recipeGenerator;
    this.jewelRepository = jewelRepository;
    this.userRepository = userRepository;
    this.orderRepository = orderRepository;
  }

  /**
     * Genera un certificado de autenticidad en formato PDF para una joya específica.
     * joyaId identificador de la joyas
     *
     * @return PDF en formato de arreglo de bytes
  */
  @PostMapping("/certify")
  public ResponseEntity<byte[]> generarCertificadosZip(
          @RequestParam String jewelIds) throws IOException {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();

    // Buscar el CustomUser en tu repositorio
    CustomUser user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
    List<Long> jewelIdList = Arrays.stream(jewelIds.split(","))
            .map(Long::parseLong)
            .toList();
    ByteArrayOutputStream zipOutputStream = new ByteArrayOutputStream();
    ZipOutputStream zip = new ZipOutputStream(zipOutputStream);

    for (Long joyaId : jewelIdList) {
      Jewel jewel = jewelRepository.findById(joyaId).orElseThrow();

      // Usa el método existente que genera el certificado
      byte[] pdfBytes = documentGenerator.generateDocument(jewel, user).toByteArray();

      // Agrega el archivo al ZIP con un nombre único
      ZipEntry entry = new ZipEntry("certificado_" + joyaId + ".pdf");
      zip.putNextEntry(entry);
      zip.write(pdfBytes);
      zip.closeEntry();
    }

    zip.close();

    return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_TYPE, "application/zip")
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=certificados.zip")
            .body(zipOutputStream.toByteArray());
  }


  /**
   * Genera un certificado de autenticidad en formato PDF para una joya específica.
   * joyaId identificador de la joyas
   *
   * @return PDF en formato de arreglo de bytes
  */
  @PostMapping("/recipe")
  public ResponseEntity<byte[]> generateReceipt(
          @RequestParam Long orderId) {

    Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new EntityNotFoundException("Pedido no encontrado"));

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();

    CustomUser user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

    // Pasa directamente los items del carrito
    return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE)
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=recibo.pdf")
            .body(recipeGenerator
            .generateDocument(order.getCartItems(), user).toByteArray());
  }
}
