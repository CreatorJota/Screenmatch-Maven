package br.com.aluna.screenmatch.controller;

import br.com.aluna.screenmatch.dto.SerieDTO;
import br.com.aluna.screenmatch.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class SerieCrontroller {
    @Autowired
    private SerieRepository repositorio;
    @GetMapping("/series")
    public List<SerieDTO> obterSeries() {
        return repositorio.findAll().stream().map(s -> new SerieDTO(s.getId(), s.getTitulo(), s.getTotalTemporadas(),
                s.getAvaliacao(), s.getGenero(), s.getAtores(), s.getPoster(), s.getSinopse())).collect(Collectors.toList());
    }
}
