package br.com.anhanguera.caranavirus.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import br.com.anhanguera.caranavirus.entity.Adress;




@Service
public class CEPService {

	private static CEPService instance;

	/**
	 * inatanciando um objeto
	 * */
	public static CEPService getInstance() {
		if (instance == null) {
			instance = new CEPService();
		}
		return instance;
	}
	
	/**
	 * Obtendo o endereço pelo cep
	 * */
	public Adress getEndereco(String cep) {
		String url = "http://viacep.com.br/ws/" + cep + "/json/";
		JSONObject objRetornadoJson = new JSONObject(getHttpGET(url));

		if (!objRetornadoJson.has("erro")) {
			return new Adress(objRetornadoJson.getString("cep"), objRetornadoJson.getString("logradouro"), objRetornadoJson.getString("complemento"),
					objRetornadoJson.getString("bairro"), objRetornadoJson.getString("localidade"), objRetornadoJson.getString("uf"));

		}
		return null;
	}

	private String getHttpGET(String urlToRead) {
		StringBuilder result = new StringBuilder();
		try {
			URL url = new URL(urlToRead);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			String line;
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}

		} catch (Exception ex) {
			throw new IllegalStateException(ex.getMessage(), ex);
		}

		return result.toString();

	}
}
