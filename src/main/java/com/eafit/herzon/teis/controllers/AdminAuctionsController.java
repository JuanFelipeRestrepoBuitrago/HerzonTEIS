package com.eafit.herzon.teis.controllers;

import com.eafit.herzon.teis.dto.AuctionDto;
import com.eafit.herzon.teis.exceptions.InvalidAuctionException;
import com.eafit.herzon.teis.exceptions.JewelNotFoundException;
import com.eafit.herzon.teis.models.Auction;
import com.eafit.herzon.teis.services.AuctionService;
import com.eafit.herzon.teis.services.JewelService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller for the admin auctions dashboard.
 */
@Controller
@RequestMapping("/admin/auctions")
public class AdminAuctionsController {

  private final AuctionService auctionService;
  private final JewelService jewelService;

  /**
   * Constructs a new AdminAuctionsController with the specified AuctionService.
   *
   * @param auctionService the service to manage auction operations
   */
  public AdminAuctionsController(
      AuctionService auctionService,
      JewelService jewelService) {
    this.auctionService = auctionService;
    this.jewelService = jewelService;
  }

  /**
   * Displays the jewel management page with pagination.
   *
   * @param page  the page number (default 0)
   * @param size  the number of items per page (default 9)
   * @param model the model to add attributes to the view
   * @return the admin jewels management page
   */
  @GetMapping({ "", "/" })
  public String manageJewels(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "9") int size,
      Model model) {
    Page<Auction> auctionPage = auctionService.getAllAuctions(page, size);
    model.addAttribute("auctions", auctionPage.getContent());
    model.addAttribute("currentPage", page);
    model.addAttribute("totalPages", auctionPage.getTotalPages());
    model.addAttribute("title", "Subastas - Admin Herzon");
    return "admin/auctions/list";
  }

  /**
   * Displays the form to create a new auction.
   *
   * @param model the model to add attributes to the view
   * @return the form view for creating a auction
   */
  @GetMapping("/create")
  public String showCreateForm(Model model) {
    model.addAttribute("jewels", jewelService.getAllJewels());
    model.addAttribute("auctionForm", new AuctionDto());
    model.addAttribute("title", "Crear Subasta - Admin Herzon");
    return "admin/auctions/form";
  }

  /**
   * Handles the save of a new auction or the update of an existing one.
   *
   * @param auctionFormDto     the auction object submitted from the form
   * @param bindingResult      the binding result to check for validation errors
   * @param model              the model to add attributes to the view
   * @param redirectAttributes the attributes to add to the redirect
   * @return redirects to the auction management page
   */
  @PostMapping({ "/save", "/save/" })
  public String saveAuction(
      @Valid @ModelAttribute("auctionForm") AuctionDto auctionFormDto,
      BindingResult bindingResult, Model model,
      RedirectAttributes redirectAttributes) {
    if (bindingResult.hasErrors()) {
      model.addAttribute("error", true);
      // Add the binding result errors to the messages just with messages, not field
      // names
      model.addAttribute("messages", bindingResult.getAllErrors().stream()
          .map(error -> error.getDefaultMessage())
          .toList());
      model.addAttribute("jewels", jewelService.getAllJewels());
      model.addAttribute("title", "Crear Subasta - Admin Herzon");
      return "admin/auctions/form";
    }

    try {
      auctionService.save(auctionFormDto);
      redirectAttributes.addFlashAttribute("messages",
          Collections.singletonList("Subasta guardada correctamente."));
      redirectAttributes.addFlashAttribute("error", false);
    } catch (JewelNotFoundException e) {
      redirectAttributes.addFlashAttribute("messages",
          Collections.singletonList(e.getMessage()));
      redirectAttributes.addFlashAttribute("error", true);
      
      if (auctionFormDto.getAuctionId() != null) {
        return "redirect:/admin/auctions/edit/" + auctionFormDto.getAuctionId();
      }

      return "redirect:/admin/auctions/create";
    } catch (EntityNotFoundException e) {
      redirectAttributes.addFlashAttribute("messages",
          Collections.singletonList(e.getMessage()));
      redirectAttributes.addFlashAttribute("error", true);

      if (auctionFormDto.getAuctionId() != null) {
        return "redirect:/admin/auctions/edit/" + auctionFormDto.getAuctionId();
      }

      return "redirect:/admin/auctions/create";
    } catch (InvalidAuctionException e) {
      redirectAttributes.addFlashAttribute("messages",
          Collections.singletonList(e.getMessage()));
      redirectAttributes.addFlashAttribute("error", true);

      if (auctionFormDto.getAuctionId() != null) {
        return "redirect:/admin/auctions/edit/" + auctionFormDto.getAuctionId();
      }

      return "redirect:/admin/auctions/create";
    } catch (Exception e) {
      redirectAttributes.addFlashAttribute("messages",
          Collections.singletonList("Error al guardar la subasta."));
      redirectAttributes.addFlashAttribute("error", true);
      
      if (auctionFormDto.getAuctionId() != null) {
        return "redirect:/admin/auctions/edit/" + auctionFormDto.getAuctionId();
      }

      return "redirect:/admin/auctions/create";
    }

    return "redirect:/admin/auctions";
  }

  /**
   * Displays the form to edit an existing auction.
   *
   * @param id    the id of the auction to edit
   * @param model the model to add attributes to the view
   * @return the form view for editing a auction
   */
  @GetMapping({ "/edit/{id}", "/edit/{id}/" })
  public String showEditForm(@PathVariable Long id, Model model) {
    try {
      Auction auction = auctionService.getAuctionById(id);

      // Convert Auction entity to AuctionDto (assuming you have a mapping method)
      AuctionDto auctionDto = new AuctionDto();
      auctionDto.setAuctionId(auction.getId());
      auctionDto.setJewelId(auction.getJewel().getId());
      auctionDto.setStartPrice(auction.getStartPrice());
      auctionDto.setCurrentPrice(auction.getCurrentPrice());
      auctionDto.setStartDateString(
          auction.getStartDate().format(
              DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")));
      auctionDto.setEndDateString(
          auction.getEndDate().format(
              DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")));
      
      model.addAttribute("jewels", jewelService.getAllJewels());
      model.addAttribute("auctionForm", auctionDto);
      model.addAttribute("title", "Editar Subasta - Admin Herzon");
    } catch (EntityNotFoundException e) {
      model.addAttribute("messages", Collections.singletonList(e.getMessage()));
      model.addAttribute("error", true);
      return "redirect:/admin/auctions";
    }

    return "admin/auctions/form";
  }

}
