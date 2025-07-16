package br.com.aluna.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosEpisodios(@JsonAlias("Title") String titulo,
                             @JsonAlias("Episode") Integer numeroEpisodio,
                             @JsonAlias("imdbRating") String avaliacao,
                             @JsonAlias("Runtime") String duracao,
                             @JsonAlias("Released") String dataLancamento) {
}
