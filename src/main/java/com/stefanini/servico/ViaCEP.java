package com.stefanini.servico;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.stefanini.dto.EnderecoViaCepDTO;
import com.stefanini.model.Endereco;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Optional;

public class ViaCEP {
    public ViaCEP(String cep){
        this.buscarCep(cep);
    }

    public static Optional<EnderecoViaCepDTO> buscarCep(String cep) {
        String json;
        EnderecoViaCepDTO endereco;
        try {
            URL url = new URL("http://viacep.com.br/ws/"+ cep +"/json");
            URLConnection urlConnection = url.openConnection();
            InputStream is = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

            StringBuilder jsonSb = new StringBuilder();

            br.lines().forEach(l -> jsonSb.append(l.trim()));

            json = jsonSb.toString();

            Gson g = new Gson();
            endereco = g.fromJson(json, EnderecoViaCepDTO.class);


        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return Optional.of(endereco);
    }
}
