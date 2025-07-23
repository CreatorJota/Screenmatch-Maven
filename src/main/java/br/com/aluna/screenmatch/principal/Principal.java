package br.com.aluna.screenmatch.principal;

import br.com.aluna.screenmatch.model.*;
import br.com.aluna.screenmatch.repository.SerieRepository;
import br.com.aluna.screenmatch.service.ConsumoApi;
import br.com.aluna.screenmatch.service.ConverteDados;
import jakarta.persistence.Access;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private Scanner leitura = new Scanner(System.in);
    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String APIKEY = "&apikey=f620158d";
    private ConsumoApi consumoApi = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private List<DadosSerie> seriesBuscadas = new ArrayList<>();
    private SerieRepository repositorio;

    public Principal(SerieRepository repositorio){
        this.repositorio = repositorio;
    }

    public void menu() {
        var opcao = -1;
        while (opcao != 0) {
        var menu = """
                1 - Buscar séries
                2 - Buscar episódios
                3 - Listar series buscadas
                
                0 - Sair
                """;

        System.out.println(menu);
        opcao = leitura.nextInt();
        leitura.nextLine();

        switch (opcao) {
            case 1:
                buscarSerieWeb();
                break;
            case 2:
                buscarEpisodioPorSerie();
                break;
            case 3:
                listarSeriesBuscadas();
                break;
            case 0:
                System.out.println("Saindo...");
                break;
            default:
                System.out.println("Opção inválida");
        }

//        System.out.println("Digite o nome da serie: ");
//        var nomeSerie = leitura.nextLine();
//        var json = consumoApi.obterDados(ENDERECO +
//                nomeSerie.replace(" ", "+") + APIKEY);
//        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
//        System.out.println(dados);
//
//        List<DadosTemporadas> temporadas = new ArrayList<>();
//
//        for (int i = 1; i <= dados.totalTemporadas(); i++) {
//            json = consumoApi.obterDados(ENDERECO +
//                    nomeSerie.replace(" ", "+") + "&season=" + i + APIKEY);
//            DadosTemporadas dadosTemporadas = conversor.obterDados(json, DadosTemporadas.class);
//            temporadas.add(dadosTemporadas);
//        }
//        temporadas.forEach(System.out::println);
//
////        for (int i = 0; i < dados.totalTemporadas(); i++) {
////            List<DadosEpisodios> episodiosTemporada = temporadas.get(i).episodios();
////            for (int j = 0; j < episodiosTemporada.size(); j++) {
////                System.out.println(episodiosTemporada.get(j).Titulo());
////            }
////        }
////        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));
//
//
//        List<DadosEpisodios> dadosEpisodios = temporadas.stream()
//                .flatMap(t -> t.episodios().stream())
//                        .collect(Collectors.toList());
//
////ENTENDENDO O USO DO PEEK
////        System.out.println("\nTop 10 episódios");
////        dadosEpisodios.stream()
////                .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
////                .peek(e -> System.out.println("Primeiro filtro(N/A) " + e))
////                .sorted(Comparator.comparing(DadosEpisodios::avaliacao).reversed())
////                .peek(e -> System.out.println("Ordenacao " + e))
////                .limit(10)
////                .peek(e -> System.out.println("Limite " + e))
////                .map(e -> e.titulo().toUpperCase(Locale.ROOT))
////                .peek(e -> System.out.println("Mapeamento " + e))
////                .forEach(System.out::println);
//
//        List<Episodio> episodios = temporadas.stream().flatMap(t -> t.episodios().stream()
//                .map(d -> new Episodio(t.numeroTemporada(), d))).collect(Collectors.toList());
//        episodios.forEach(System.out::println);
//
//
//        System.out.println("Digita o nome do episodio");
//        var trechoTitulo = leitura.nextLine();
//
//        Optional<Episodio> episodioLocalizado = episodios.stream()
//                .filter(e -> e.getTitulo().toUpperCase(Locale.ROOT).contains(trechoTitulo.toUpperCase()))
//                .findFirst();
//
//        if (episodioLocalizado.isPresent()) {
//            System.out.println("Episodio encontrado!");
//            System.out.println("Temporada: " + episodioLocalizado.get().getTemporada());
//        } else {
//            System.out.println("Episodio nao encontrado!");
//        }
//
//        System.out.println("A partir de que ano você deseja ver os episodios ?");
//        var ano = leitura.nextLine();
//        leitura.nextLine();
//
//        LocalDate dataBusca = LocalDate.of(Integer.parseInt(ano), 1, 1);
//
//        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//
//        episodios.stream()
//                .filter(e -> e.getDataLancamento() != null && e.getDataLancamento().isAfter(dataBusca))
//                .forEach(e -> System.out.println(
//                        "Temporada: " + e.getTemporada() +
//                                " Episodio: " + e.getTitulo() +
//                                " Data De Lancamento: " + e.getDataLancamento().format(formatador)
//                ));
//        System.out.println("\nAvaliacao por temporada!");
//        Map<Integer, Double> avaliacoesPorTemporada = episodios.stream()
//                .filter(e -> e.getAvaliacao() > 0.0)
//                .collect(Collectors.groupingBy(Episodio::getTemporada,
//                        Collectors.averagingDouble(Episodio::getAvaliacao)));
//        System.out.println(avaliacoesPorTemporada);
//
//        System.out.println("\nInformacoes da Avaliacoes");
//        DoubleSummaryStatistics est = episodios.stream()
//                .filter(e -> e.getAvaliacao() > 0.0)
//                .collect(Collectors.summarizingDouble(Episodio::getAvaliacao));
//        System.out.println("Media: " + est.getAverage());
//        System.out.println("Melhor Episodio: " + est.getMax());
//        System.out.println("Pior Episodio: " + est.getMin());
//        System.out.println("Quantidade: " + est.getCount());
    }
    }
        private void buscarSerieWeb() {
            DadosSerie dados = getDadosSerie();
            Serie serie = new Serie(dados);
            //seriesBuscadas.add(dados);
            repositorio.save(serie);
            System.out.println(dados);
        }

        private DadosSerie getDadosSerie() {
            System.out.println("Digite o nome da série para busca");
            var nomeSerie = leitura.nextLine();
            var json = consumoApi.obterDados(ENDERECO
                    + nomeSerie.replace(" ", "+") + APIKEY);
            DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
            return dados;
        }

        private void buscarEpisodioPorSerie(){
            DadosSerie dadosSerie = getDadosSerie();
            List<DadosTemporadas> temporadas = new ArrayList<>();

            for (int i = 1; i <= dadosSerie.totalTemporadas(); i++) {
                var json = consumoApi.obterDados(ENDERECO
                        + dadosSerie.titulo().replace(" ", "+")
                        + "&season=" + i + APIKEY);
                DadosTemporadas dadosTemporada = conversor.obterDados(json, DadosTemporadas.class);
                temporadas.add(dadosTemporada);
            }
            temporadas.forEach(System.out::println);
        }
        private void listarSeriesBuscadas(){
        List<Serie> series = repositorio.findAll();
            series.stream().sorted(Comparator.comparing(Serie::getGenero))
                    .forEach(System.out::println);
        }
}

