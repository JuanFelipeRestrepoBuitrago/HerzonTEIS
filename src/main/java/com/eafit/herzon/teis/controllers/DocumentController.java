package com.eafit.herzon.teis.controllers;

import com.eafit.herzon.teis.models.CustomUser;
import com.eafit.herzon.teis.models.Jewel;
import com.eafit.herzon.teis.repositories.JewelRepository;
import com.eafit.herzon.teis.repositories.UserRepository;
import com.eafit.herzon.teis.services.AuthGeneratorService;
import com.eafit.herzon.teis.services.RecipeGeneratorService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

  private final RecipeGeneratorService recipeGeneratorService;
  private final AuthGeneratorService authGeneratorService;
  private final JewelRepository jewelRepository;
  private final UserRepository userRepository;

  /**
     * Constructor que inicializa los servicios y repositorios necesarios.
     *
     * @param recipeGeneratorService servicio para generar recibos
     * @param authGeneratorService   servicio para generar certificados
     * @param jewelRepository        repositorio de joyas
     * @param userRepository         repositorio de usuarios
  */
  public DocumentController(
            RecipeGeneratorService recipeGeneratorService,
            AuthGeneratorService authGeneratorService,
            JewelRepository jewelRepository,
            UserRepository userRepository) {
    this.recipeGeneratorService = recipeGeneratorService;
    this.authGeneratorService = authGeneratorService;
    this.jewelRepository = jewelRepository;
    this.userRepository = userRepository;
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
    CustomUser user = (CustomUser) authentication.getPrincipal();
    List<Long> jewelIdList = Arrays.stream(jewelIds.split(","))
            .map(Long::parseLong)
            .toList();
    ByteArrayOutputStream zipOutputStream = new ByteArrayOutputStream();
    ZipOutputStream zip = new ZipOutputStream(zipOutputStream);

    for (Long joyaId : jewelIdList) {
      Jewel jewel = jewelRepository.findById(joyaId).orElseThrow();

      // Usa el método existente que genera el certificado
      byte[] pdfBytes = authGeneratorService.generateDocument(jewel, user).toByteArray();

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
     * Genera un recibo en formato PDF para una joya específica.
     *
     * @param joyaId identificador de la joya
     * @param userId identificador del usuario que solicita el recibo
     * @return PDF en formato de arreglo de bytes
  */
  @GetMapping("/recipe/{joyaId}")
  public ResponseEntity<byte[]> generarRecibo(
            @PathVariable Long joyaId,
            @RequestParam Long userId) {

    Jewel joya = jewelRepository.findById(joyaId).orElseThrow();
    CustomUser user = userRepository.findById(userId).orElseThrow();

    byte[] pdf = recipeGeneratorService.generarDocumento(joya, user).toByteArray();

    return ResponseEntity.ok()
    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE)
    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=recibo.pdf")
    .body(pdf);
  }
}
