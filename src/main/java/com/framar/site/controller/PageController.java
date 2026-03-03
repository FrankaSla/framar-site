package com.framar.site.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.framar.site.service.ProjectService;

@Controller
public class PageController {

    private final ProjectService projectService;

    public PageController(ProjectService projectService) {
        this.projectService = projectService;
    }
    
    private final MailService mailservice;
    
    public PageController(MailService mailService) {
    	this.mailservice = mailservice;
    }

    // helper: složi canonical URL (radi i na localhostu)
    private String fullUrl(HttpServletRequest req, String path) {
        String scheme = req.getScheme(); // http
        String host = req.getServerName(); // localhost
        int port = req.getServerPort(); // 8080
        String base = scheme + "://" + host + (port == 80 || port == 443 ? "" : ":" + port);
        return base + path;
    }

    @GetMapping("/")
    public String index(Model model, HttpServletRequest req) {
        model.addAttribute("pageTitle", "FRAMAR - niskogradnja i podvodni radovi");
        model.addAttribute("pageDesc", "FRAMAR - profesionalni radovi niskogradnje i podvodne sanacije. Pouzdana izvedba i kvalitetna izvedba na terenu.");
        model.addAttribute("ogImage", "/img/logo.png");
        model.addAttribute("ogUrl", fullUrl(req, "/"));
        return "index";
    }

    @GetMapping("/o-nama")
    public String oNama(Model model, HttpServletRequest req) {
        model.addAttribute("pageTitle", "O nama - FRAMAR");
        model.addAttribute("pageDesc", "FRAMAR - tim za niskogradnju i podvodne radove. Iskustvo, pouzdanost i kvalitetna izvedba.");
        model.addAttribute("ogImage", "/img/logo.png");
        model.addAttribute("ogUrl", fullUrl(req, "/o-nama"));
        return "o-nama";
    }

    @GetMapping("/usluge")
    public String usluge(Model model, HttpServletRequest req) {
        model.addAttribute("pageTitle", "Usluge - FRAMAR");
        model.addAttribute("pageDesc", "Niskogradnja, podvodni radovi i sanacije. Organizacija, logistika i izvedba na terenu.");
        model.addAttribute("ogImage", "/img/logo.png");
        model.addAttribute("ogUrl", fullUrl(req, "/usluge"));
        return "usluge";
    }

    @GetMapping("/projekti")
    public String projekti(Model model, HttpServletRequest req) {
        model.addAttribute("projects", projectService.getProjects());
        model.addAttribute("pageTitle", "Projekti - FRAMAR");
        model.addAttribute("pageDesc", "Galerija izvedenih projekata: niskogradnja i podvodni radovi.");
        model.addAttribute("ogImage", "/img/logo.png");
        model.addAttribute("ogUrl", fullUrl(req, "/projekti"));
        return "projekti";
    }

    @GetMapping("/projekti/{slug}")
    public String projektDetalj(@PathVariable String slug, Model model, HttpServletRequest req) {
        return projectService.getBySlug(slug)
            .map(p -> {
                model.addAttribute("project", p);

                model.addAttribute("pageTitle", p.getTitle() + " - FRAMAR");
                model.addAttribute("pageDesc", "Galerija i detalji projekta: " + p.getTitle() + " - niskogradnja i podvodni radovi.");
                model.addAttribute("ogImage", (p.getCoverImage() != null ? p.getCoverImage() : "/img/logo.png"));
                model.addAttribute("ogUrl", fullUrl(req, "/projekti/" + slug));

                return "projekt-detalj";
            })
            .orElse("redirect:/projekti");
    }

    @GetMapping("/kontakt")
    public String kontakt(Model model, HttpServletRequest req) {
        model.addAttribute("pageTitle", "Kontakt - FRAMAR");
        model.addAttribute("pageDesc", "Kontaktirajte FRAMAR za upit i ponudu. Niskogradnja i podvodni radovi.");
        model.addAttribute("ogImage", "/img/logo.png");
        model.addAttribute("ogUrl", fullUrl(req, "/kontakt"));
        return "kontakt";
    }
    
    @PostMapping("/kontakt")
    public String kontaktSend(
            @RequestParam String ime,
            @RequestParam String email,
            @RequestParam String poruka,
            @RequestParam(required = false) String website, // honeypot
            RedirectAttributes ra
    ) {
        // anti-bot: ako je skriveno polje popunjeno, ignoriraj
        if (website != null && !website.isBlank()) {
            return "redirect:/kontakt";
        }

        try {
            mailService.sendContact(ime, email, poruka);
            ra.addFlashAttribute("successMessage", "Poruka je poslana. Javit ćemo se uskoro.");
        } catch (Exception e) {
            ra.addFlashAttribute("errorMessage", "Došlo je do greške pri slanju. Pokušajte ponovno.");
        }

        return "redirect:/kontakt";
    }

}