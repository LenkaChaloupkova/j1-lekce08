package cz.czechitas.lekce8.svatky;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Month;
import java.time.MonthDay;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Třída s informacemi o tom,kdo má kdy svátek.
 */
public class Svatky {
  private static final DateTimeFormatter MONTH_PARSER = DateTimeFormatter.ofPattern("d.M.");

  public Stream<Svatek> seznamSvatku() {
    try {
      Path path = Paths.get(Svatky.class.getResource("svatky.txt").toURI());
      return Files.lines(path).map(Svatky::parseLine);
    } catch (URISyntaxException | IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Vrátí seznam všech svátků v daném měsíci.
   *
   * @param mesic Měsíc, pro který se mají svátky vypsat.
   * @return Stream svátků.
   */
  public Stream<Svatek> svatkyVMesici(Month mesic) {
    return seznamSvatku()
            .filter(svatek -> svatek.getDen().getMonth() == mesic);
  }

  /**
   * Vrátí den, kdy má dotyčné jméno svátek.
   *
   * @param jmeno
   * @return
   */
  public Stream<MonthDay> datumSvatku(String jmeno) {
    return seznamSvatku()
            .filter(svatek -> svatek.getJmeno().equals(jmeno))
            .map(Svatek::getDen);
  }

  /**
   * Vrátí všechna jména mužů.
   *
   * @return Stream jmen.
   */
  public Stream<String> muzi() {
    //TODO implementovat pomosí lambda výrazu
    // je nejaka podminka -

    return seznamSvatku()
            .filter(s -> s.getGender() == Gender.MUZ)
            .map(s -> s.getJmeno());
            // .map(Svatek::getJmeno);
  }

  /**
   * Vrátí všechna jména žen.
   *
   * @return Stream jmen.
   */
  public Stream<String> zeny() {
    //TODO implementovat pomocí method reference
    return seznamSvatku()
            //.filter(s -> s.getGender() == Gender.ZENA) // > toto je lambda výraz
            .filter(s -> s.getGender() == Gender.ZENA)
            .map (Svatek::getJmeno); // reference metoda
  }

  /**
   * Vrátí jména, která mají v daný den svátek.
   *
   * @return Stream jmen.
   */
  public Stream<String> den(MonthDay den) {
    //TODO
    return seznamSvatku()
            .filter(s -> s.getDen() == den)
    .map(Svatek::getJmeno);
  }

  /**
   * Vrátí ženská jména, která maj ísvátek v daném měsíci.
   *
   * @param mesic Vybraný měsíc.
   * @return Stream jmen.
   */
  public Stream<String> zenskaJmenaVMesici(Month mesic) {
    //TODO
    return seznamSvatku()
            .filter(svatek -> svatek.getDen().getMonth() == mesic)
            .filter(svatek -> svatek.getGender() == zenskaJmenaVMesici()) // neni dodelane
            .map(Svatek::getJmeno);
  }

  /**
   * Vrátí počet mužů, kteří mají svátek 1. den v měsíci.
   *
   * @return Počet mužských jmen.
   */
  public int pocetMuzuSvatekPrvniho() {
    //TODO
    return 0;
  }

  /**
   * Vypíše do konzole seznam jmen, která mají svátek v listopadu.
   *
   */
  public void vypsatJmenaListopad() {
    //TODO
  }

  /**
   * Vypíše počet unikátních jmen v kalendáři.
   *
   */
  public int pocetUnikatnichJmen() {
    //TODO
    return 0;
  }

  /**
   * Vrátí seznam jmen, která mají svátek v červnu – přeskočí prvních 10 jmen.
   *
   * @see Stream#skip(long)
   */
  public Stream<String> cervenJmenaOdDesatehoJmena() {
    //TODO
    return null;
  }

  /**
   * Vrátí seznam jmen, která mají svátek od 24. 12. včetně do konce roku.
   *
   * @see Stream#dropWhile(java.util.function.Predicate)
   */
  public Stream<String> jmenaOdVanoc() {
    //TODO
    return null;
  }

  private static Svatek parseLine(String line) {
    String[] parts = line.split("\\s");
    assert parts.length == 3;
    return new Svatek(
            MonthDay.parse(parts[0], MONTH_PARSER),
            parts[1],
            Gender.valueOf(parts[2].toUpperCase(Locale.ROOT))
    );
  }
}
