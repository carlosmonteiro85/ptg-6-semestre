package br.com.anhanguera.caranavirus.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LeitorCSV {

	public static List<String> geradorEndereco(String enderecoCsv) {

		List<String> ceps = new ArrayList<>();

		String linha = "";
		String csvDivisor = ",";
		boolean linhaTitulo = true;

		try (BufferedReader br = new BufferedReader(new FileReader(enderecoCsv))) {

			while ((linha = br.readLine()) != null) {
				
				if (linhaTitulo) {
					linhaTitulo = false;
				} else {
					String[] arrey = linha.split(csvDivisor);
					ceps.add(arrey[0]);					
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println(
					"Error " + e + " O arquivo CSV indicado não foi encontrado, verifique o caminho informado");
		} catch (IOException e) {
			System.out.println("Error " + e + " Falha na leitura do arquivo, verifique se o arquivo está correto");
		}
		return ceps;
	}

}
