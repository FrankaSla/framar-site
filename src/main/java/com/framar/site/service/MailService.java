package com.framar.site.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class MailService {

  @Value("${brevo.api.key}")
  private String apiKey;

  @Value("${brevo.sender}")
  private String sender;

  @Value("${framar.contact.to}")
  private String to;

  private final RestTemplate rest = new RestTemplate();

  public void sendContact(String ime, String email, String poruka) {
    String url = "https://api.brevo.com/v3/smtp/email";

    Map<String, Object> body = new HashMap<>();
    body.put("sender", Map.of("email", sender, "name", "FRAMAR web"));
    body.put("to", List.of(Map.of("email", to)));
    body.put("replyTo", Map.of("email", email, "name", ime));
    body.put("subject", "FRAMAR web upit - " + ime);
    body.put("textContent", "Ime: " + ime + "\nEmail: " + email + "\n\nPoruka:\n" + poruka);

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("api-key", apiKey);

    HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

    ResponseEntity<String> resp = rest.exchange(url, HttpMethod.POST, entity, String.class);

    if (!resp.getStatusCode().is2xxSuccessful()) {
      throw new RuntimeException("Brevo API error: " + resp.getStatusCode() + " " + resp.getBody());
    }
  }
}