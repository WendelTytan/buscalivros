package br.com.buscalivros.service;

import br.com.buscalivros.entrada.Entrada;
import br.com.buscalivros.utils.ScreenClear;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiService {
    public String getResponseBody (String url) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
        HttpResponse<String> response = null;

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            ScreenClear.clear();
            System.out.println("Não foi possível estabelecer a conexão.");
            Entrada.setEntradaUsuario("");
        }

        String responseBody = response != null ? response.body() : null;
        return responseBody;
    }
}
