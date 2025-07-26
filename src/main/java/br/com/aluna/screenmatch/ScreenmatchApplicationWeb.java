package br.com.aluna.screenmatch;

import br.com.aluna.screenmatch.principal.Principal;
import br.com.aluna.screenmatch.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenmatchApplicationWeb {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplicationWeb.class, args);
	}

}
