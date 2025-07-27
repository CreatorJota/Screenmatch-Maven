package br.com.aluna.screenmatch.dto;

import br.com.aluna.screenmatch.model.Categoria;

public record SerieDTO(
        Long id,
        String titulo,
        Integer totalTemporadas,
        Double avaliacao,
        Categoria genero,
        String atores,
        String poster,
        String sinopse
) {
}
