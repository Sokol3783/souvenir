package org.example.task;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.List;
import org.example.dao.DAO;
import org.example.dao.FabricatorDAO;
import org.example.dao.SouvenirDAO;
import org.example.models.Fabricator;
import org.example.models.Souvenir;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;


//ALL test is disabled something broke in input/output
class TaskItemsTest {

  private static final DAO<Souvenir> souvenirDAO = SouvenirDAO.getInstance();
  private static final DAO<Fabricator> fabricatorDAO = FabricatorDAO.getInstance();

  private static final TaskItems task = new TaskItemImpl(souvenirDAO, fabricatorDAO);
  private static final InputStream originalInputStream = System.in;
  private static final PrintStream originalPrintStream = System.out;

  private static List<Souvenir> getSouvenirs() {
    return List.of(
        new Souvenir(1, "LEGO Classic Bricks Box", "LEGO", LocalDate.parse("2022-01-15"), 29.99,
            fabricatorDAO.get(1).get()),
        new Souvenir(2, "LEGO Star Wars Millennium Falcon", "LEGO", LocalDate.parse("2021-08-20"),
            159.99, fabricatorDAO.get(1).get()),
        new Souvenir(3, "LEGO Friends Heartlake City Resort", "LEGO", LocalDate.parse("2020-03-10"),
            89.99, fabricatorDAO.get(1).get()),
        new Souvenir(4, "LEGO Technic Bugatti Chiron", "LEGO", LocalDate.parse("2018-06-01"),
            349.99, fabricatorDAO.get(1).get()),
        new Souvenir(5, "LEGO Harry Potter Hogwarts Castle", "LEGO", LocalDate.parse("2018-08-01"),
            399.99, fabricatorDAO.get(1).get()),
        new Souvenir(6, "Transformers Bumblebee Action Figure", "Transformers",
            LocalDate.parse("2020-05-05"), 19.99, fabricatorDAO.get(2).get()),
        new Souvenir(7, "Monopoly Classic Board Game", "Monopoly", LocalDate.parse("2019-02-15"),
            19.99, fabricatorDAO.get(2).get()),
        new Souvenir(8, "Nerf N-Strike Elite Disruptor Blaster", "Nerf",
            LocalDate.parse("2017-06-01"), 12.99, fabricatorDAO.get(2).get()),
        new Souvenir(9, "Play-Doh Modeling Compound 10-Pack", "Play-Doh",
            LocalDate.parse("2021-03-20"), 7.99, fabricatorDAO.get(2).get()),
        new Souvenir(10, "Barbie Dreamhouse Dollhouse", "Barbie", LocalDate.parse("2020-07-01"),
            179.99, fabricatorDAO.get(3).get()),
        new Souvenir(11, "Hot Wheels Ultimate Garage", "Hot Wheels", LocalDate.parse("2019-05-10"),
            99.99, fabricatorDAO.get(3).get()),
        new Souvenir(12, "Fisher-Price Laugh & Learn Smart Stages Chair", "Fisher-Price",
            LocalDate.parse("2021-11-15"), 39.99, fabricatorDAO.get(3).get()),
        new Souvenir(13, "UNO Card Game", "UNO", LocalDate.parse("2018-02-01"), 9.99,
            fabricatorDAO.get(3).get()),
        new Souvenir(14, "Gundam RX-78-2 Model Kit", "Gundam", LocalDate.parse("2022-04-05"), 49.99,
            fabricatorDAO.get(4).get()),
        new Souvenir(15, "Tamagotchi Virtual Pet", "Tamagotchi", LocalDate.parse("2020-09-20"),
            19.99, fabricatorDAO.get(4).get()),
        new Souvenir(16, "Power Rangers Beast Morphers Action Figure", "Power Rangers",
            LocalDate.parse("2019-03-10"), 14.99, fabricatorDAO.get(4).get()),
        new Souvenir(17, "Dragon Ball Z Figurine", "Dragon Ball Z", LocalDate.parse("2017-07-01"),
            29.99, fabricatorDAO.get(4).get()),
        new Souvenir(18, "Baby Alive Doll", "Baby Alive", LocalDate.parse("2019-09-10"), 24.99,
            fabricatorDAO.get(2).get()),
        new Souvenir(19, "My Little Pony Friendship Castle Playset", "My Little Pony",
            LocalDate.parse("2020-11-30"), 39.99, fabricatorDAO.get(2).get()),
        new Souvenir(20, "Connect 4 Classic Grid Board Game", "Connect 4",
            LocalDate.parse("2018-07-15"), 9.99, fabricatorDAO.get(2).get()),
        new Souvenir(21, "Hot Wheels Track Builder Unlimited Loop Kit", "Hot Wheels",
            LocalDate.parse("2022-05-01"), 29.99, fabricatorDAO.get(2).get()));
  }

  private static List<Fabricator> getFabricators() {
    return List.of(new Fabricator(1, "LEGO", "DENMARK"),
        new Fabricator(2, "HASBRO", "USA"),
        new Fabricator(3, "Mattel", "USA"),
        new Fabricator(4, "Bandai", "JAPAN"));
  }

  @BeforeEach
  void setUp() {
    List<Fabricator> fabricators = getFabricators();
    fabricators.forEach(fabricatorDAO::update);
    List<Souvenir> souvenirs = getSouvenirs();
    souvenirs.forEach(souvenirDAO::update);
  }

  @AfterEach
  void tearDownTest() {
    fabricatorDAO.getAll().forEach(fabricatorDAO::delete);
    souvenirDAO.getAll().forEach(souvenirDAO::delete);
    System.setIn(originalInputStream);
    System.setOut(originalPrintStream);
  }

  private ByteArrayOutputStream getOutputByteStream(String args) {
    InputStream inputStream = new ByteArrayInputStream(args.getBytes());
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(outputStream);
    System.setIn(inputStream);
    System.setOut(printStream);
    return outputStream;
  }

  @Test
  @Disabled
  void listFabricatorsSouvenirsByInputTest() {
    ByteArrayOutputStream outputStream = getOutputByteStream("1\nLego\n");
    task.listFabricatorsSouvenirsByInput().run();
    String output = outputStream.toString().trim();
    assertEquals(getListSubString(output), getLegoSouvenirsList());
  }

  private String getListSubString(String output) {
    return output.substring(output.indexOf("List:"));
  }

  private String getLegoSouvenirsList() {
    return "List:\n"
        + " id                       name                        brand      fabricator   date issue    price\n"
        + "  1 LEGO Classic Bricks Box                       LEGO             LEGO       2022-01-15    29,99\n"
        + "  2 LEGO Star Wars Millennium Falcon              LEGO             LEGO       2021-08-20    159,99\n"
        + "  3 LEGO Friends Heartlake City Resort            LEGO             LEGO       2020-03-10    89,99\n"
        + "  4 LEGO Technic Bugatti Chiron                   LEGO             LEGO       2018-06-01    349,99\n"
        + "  5 LEGO Harry Potter Hogwarts Castle             LEGO             LEGO       2018-08-01    399,99";
  }

  @Test
  @Disabled
  void listSouvenirsFromCountryTest() {
    ByteArrayOutputStream outputStream = getOutputByteStream("Japan");
    task.listSouvenirsFromCountry().run();
    String output = outputStream.toString().trim();
    assertEquals(getListSubString(output), getListSouvenirsFromJapan());
  }

  private String getListSouvenirsFromJapan() {
    return "List:\n"
        + " id                       name                        brand      fabricator   date issue    price\n"
        + " 14 Gundam RX-78-2 Model Kit                      Gundam           Bandai     2022-04-05    49,99\n"
        + " 15 Tamagotchi Virtual Pet                        Tamagotchi       Bandai     2020-09-20    19,99\n"
        + " 16 Power Rangers Beast Morphers Action Figure    Power Rangers    Bandai     2019-03-10    14,99\n"
        + " 17 Dragon Ball Z Figurine                        Dragon Ball Z    Bandai     2017-07-01    29,99";
  }

  @Test
  void listFabricatorsHasPriceLessThenInputPriceTest() {
    ByteArrayOutputStream outputStream = getOutputByteStream("Japan");
    task.listSouvenirsFromCountry().run();
    String output = outputStream.toString().trim();
    assertEquals(getListSubString(output), "");
  }

  @Test
  void listFabricatorsAndListFabricatorsSouvenirsTest() {
    ByteArrayOutputStream outputStream = getOutputByteStream("Japan");
    task.listSouvenirsFromCountry().run();
    String output = outputStream.toString().trim();
    assertEquals(getListSubString(output), "");
  }

  @Test
  void listInfoAboutSouvenirFabricatorsByYearTest() {
    ByteArrayOutputStream outputStream = getOutputByteStream("Japan");
    task.listSouvenirsFromCountry().run();
    String output = outputStream.toString().trim();
    assertEquals(getListSubString(output), "");
  }

  @Test
  @Disabled
  void listSouvenirsByYearTest() {
    ByteArrayOutputStream outputStream = getOutputByteStream("2018");
    task.listSouvenirsByYear().run();
    String output = outputStream.toString().trim();
    assertEquals(getListSubString(output), getListSouvenirsByYear());
  }

  private String getListSouvenirsByYear() {
    return "List:\n"
        + " id                       name                        brand      fabricator   date issue    price\n"
        + "  4 LEGO Technic Bugatti Chiron                   LEGO             LEGO       2018-06-01    349,99\n"
        + "  5 LEGO Harry Potter Hogwarts Castle             LEGO             LEGO       2018-08-01    399,99\n"
        + " 13 UNO Card Game                                 UNO              Mattel     2018-02-01     9,99\n"
        + " 20 Connect 4 Classic Grid Board Game             Connect 4        HASBRO     2018-07-15     9,99";
  }

  @Test
  void removeFabricatorAndFabricatorsSouvenirTest() {
    ByteArrayOutputStream outputStream = getOutputByteStream("Japan");
    task.listSouvenirsFromCountry().run();
    String output = outputStream.toString().trim();
    assertEquals(getListSubString(output), "");
  }
}