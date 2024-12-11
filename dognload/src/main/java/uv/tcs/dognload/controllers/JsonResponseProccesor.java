package uv.tcs.dognload.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonResponseProccesor {
    public static void processReponse(String responseBody) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(responseBody);

            String status = root.get("status").asText();
            String title = root.get("title").asText();
            String downloadLink = root.get("link").asText();

            if (status.equals("ok")) {
                System.out.println("Titulo: " + title);
                System.out.println("Enlace de descarga: " + downloadLink);
            } else {
                System.out.println("Error en la respuesta");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
