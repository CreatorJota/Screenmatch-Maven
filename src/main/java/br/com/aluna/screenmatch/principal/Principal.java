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
    private List<Serie> series = new ArrayList<>();
    private Optional<Serie> serieBusca;

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
                4 - Buscar serie por titulo
                5 - Buscar serie por ator
                6 - Top 5 series
                7 - Buscar series por categoria
                8 - Filtra Series
                9 - Buscar por trecho
                10 - Top episodios por serie
                11 - Buscar Episodios por data
                
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
            case 4:
                buscarSeriePorTitulo();
                break;
            case 5:
                buscarSeriePorAtor();
            case 6 :
                buscarTop5Series();
            case 7 :
                buscarSeriesPorCategoria();
            case 8 :
                filtrarSeriePorTemporadaEAvaliacao();
            case 9:
                buscarEpisodioPorTrecho();
            case 10:
                topEpisodiosPorSerie();
            case 11:
                buscarEpisodiosPorData();
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
            listarSeriesBuscadas();
            System.out.println("Diga uma serie pelo nome:");
            var nomeSerie = leitura.nextLine();

            Optional<Serie> serie = repositorio.findByTituloContainingIgnoreCase(nomeSerie);
//                    series.stream().filter(s -> s.getTitulo().toLowerCase().contains(nomeSerie.toLowerCase()))
//                    .findFirst();
            if (serie.isPresent()) {

                var serieEncontrada = serie.get();
                List<DadosTemporadas> temporadas = new ArrayList<>();

                for (int i = 1; i <= serieEncontrada.getTotalTemporadas(); i++) {
                    var json = consumoApi.obterDados(ENDERECO
                            + serieEncontrada.getTitulo().replace(" ", "+")
                            + "&season=" + i + APIKEY);
                    DadosTemporadas dadosTemporada = conversor.obterDados(json, DadosTemporadas.class);
                    temporadas.add(dadosTemporada);
                }
                temporadas.forEach(System.out::println);

                List<Episodio> episodios = temporadas.stream()
                        .flatMap(d -> d.episodios().stream()
                                .map(e -> new Episodio(d.numeroTemporada(), e)))
                        .collect(Collectors.toList());
                serieEncontrada.setEpisodios(episodios);
                repositorio.save(serieEncontrada);
            } else {
                System.out.println("Serie nao encontrada!");
            }
        }
        private void listarSeriesBuscadas(){
        series = repositorio.findAll();
            series.stream().sorted(Comparator.comparing(Serie::getGenero))
                    .forEach(System.out::println);
        }

    private void buscarSeriePorTitulo() {
        System.out.println("Escolha uma serie pelo nome: ");
        var nomeSerie = leitura.nextLine();
        serieBusca = repositorio.findByTituloContainingIgnoreCase(nomeSerie);

        if (serieBusca.isPresent()) {
            System.out.println("Dados da serie: " + serieBusca.get());
        } else {
            System.out.println("Serie nao encontrada!");
        }
    }

    private void buscarSeriePorAtor() {
        System.out.println("Diga o nome do ator para a busca: ");
        var nomeAtor = leitura.nextLine();
        System.out.println("Avaliacao a partir de que valor ?");
        var avaliacao = leitura.nextDouble();
        List<Serie> seriesEncontrada = repositorio.findByAtoresContainingIgnoreCaseAndAvaliacaoGreaterThanEqual(nomeAtor, avaliacao);
        System.out.println("Serie em que " +nomeAtor + " trabalhou");
        seriesEncontrada.forEach(s -> System.out.println(s.getTitulo() + " Avaliacao: " + s.getAvaliacao()));
    }

    private void buscarTop5Series() {
        List<Serie> topseries = repositorio.findTop5ByOrderByAvaliacaoDesc();
        topseries.forEach(s -> System.out.println(s.getTitulo() + " Avaliacao: " + s.getAvaliacao()));
    }

    private void buscarSeriesPorCategoria() {
        System.out.println("Deseja buscar por qual categoria ?");
        var genero = leitura.nextLine();
        Categoria categoria = Categoria.fromPortugues(genero);
        List<Serie> seriesPorCategoria = repositorio.findByGenero(categoria);
        System.out.println("Series da categoria " + genero);
        seriesPorCategoria.forEach(System.out::println);
    }

    private void filtrarSeriePorTemporadaEAvaliacao() {
        System.out.println("Filtrar serie ate quantas temporadas ?");
        var totalTemporadas = leitura.nextInt();
        leitura.nextLine();
        System.out.println("Com avaliacao a partir de qual valor ?");
        var avaliacao = leitura.nextDouble();
        leitura.nextLine();
        List<Serie> filtroSeries = repositorio.findByTotalTemporadasLessThanEqualAndAvaliacaoGreaterThanEqual(totalTemporadas, avaliacao);
        System.out.println("***Series Filtradas***");
        filtroSeries.forEach(s -> System.out.println(s.getTitulo() + "- avaliacao: " + s.getAvaliacao()));
    }

    private void buscarEpisodioPorTrecho(){
        System.out.println("Diga o nome do episodio para a busca: ");
        var trechoEpisodio = leitura.nextLine();
        List<Episodio> episodiosEncontrados = repositorio.episodiosPorTrecho(trechoEpisodio);
        episodiosEncontrados.forEach(e -> System.out.printf("Serie: %s Temporada %s - Episodio %s - %s\n",
                e.getSerie().getTitulo(), e.getTemporada(),
                e.getNumeroEpisodio(), e.getTitulo()));
    }

    private void topEpisodiosPorSerie() {
        buscarSeriePorTitulo();
        if (serieBusca.isPresent()) {
            Serie serie = serieBusca.get();
            List<Episodio> topEpisodios = repositorio.topEpisodiosPorSerie(serie);
            topEpisodios.forEach(e -> System.out.printf("Serie: %s Temporada %s - Episodio %s - %s - Avaliação: %s\n",
                    e.getSerie().getTitulo(), e.getTemporada(),
                    e.getNumeroEpisodio(), e.getTitulo(), e.getAvaliacao()));
        }
    }

    private void buscarEpisodiosPorData() {
        buscarSeriePorTitulo();
        if (serieBusca.isPresent()){
            System.out.println("Digita o ano do lançamento");
            var anoLancamento = leitura.nextLine();
            List<Episodio> episodiosPorAno = repositorio.episodiosPorAno(serieBusca.get(), anoLancamento);
            episodiosPorAno.forEach(System.out::println);
        }
    }
}

