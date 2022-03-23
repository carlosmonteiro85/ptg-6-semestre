package br.com.anhanguera.caranavirus;

import java.util.Locale;

import org.springframework.boot.test.context.SpringBootTest;

import com.github.javafaker.Faker;

@SpringBootTest
public class CaranaVirusApplicationTests {

	public static Faker faker = Faker.instance(new Locale("pt", "BR"));

}
