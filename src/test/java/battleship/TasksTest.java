package battleship;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * TasksTest
 *
 * @author Your Name
 * Date: 07/01/2026
 * Time: 00:53
 */
class TasksTest {

	private static final String FIXED_FLEET = """
			lefrota
			barca 0 0 n
			barca 0 2 n
			barca 0 4 n
			barca 0 6 n
			barca 0 8 n
			barca 2 0 n
			barca 2 2 n
			barca 2 4 n
			barca 2 6 n
			barca 2 8 n
			barca 4 0 n
			""";

	@TempDir
	Path menuDirectory;

	@BeforeEach
	void setUp() {
	}

	@AfterEach
	void tearDown() {
	}

	@Test
	void taskA() {
	}

	@Test
	void taskB() {
	}

	@Test
	void taskC() {
	}

	@Test
	void taskD() {
	}

	@Test
	void buildFleet() {
	}

	@Test
	void readShip() {
	}

	@Test
	void readPosition() {
	}

	@Test
	void firingRound() {
	}

	@Test
	@DisplayName("Rajada sem frota volta ao menu")
	void rajadaWithoutFleetReturnsToMenu() throws Exception {
		String output = runMenu("rajada\ndesisto\n");

		assertTrue(output.contains("> > Bons ventos!"), output);
		assertFalse(output.contains("Jogada nº"), output);
	}

	@Test
	@DisplayName("Rajada com navios a flutuar mostra o resultado e mantém o menu ativo")
	void rajadaWithSurvivorsReturnsToMenu() throws Exception {
		String output = runMenu(FIXED_FLEET + "rajada A1 A2 A3\ndesisto\n");

		assertOutputOrder(output, "Jogada nº1 -> 3 tiros válidos: 2 Barcas ao fundo + 1 tiro na água",
				"Estado da Frota: 9 a flutuar, 2 afundados!", " A | * o * - # . # . # . |", "> Bons ventos!");
		assertFalse(output.contains("Maldito sejas, Java Sparrow"), output);
	}

	@Test
	@DisplayName("A última rajada mostra a frota afundada e termina o processo")
	void rajadaSinkingTheFleetExits() throws Exception {
		String output = runMenu(FIXED_FLEET + """
				rajada A1 A3 A5
				rajada A7 A9 C1
				rajada C3 C5 C7
				rajada C9 E1 J10
				estado
				desisto
				""");

		assertOutputOrder(output, "Jogada nº4 -> 3 tiros válidos: 2 Barcas ao fundo + 1 tiro na água",
				"Estado da Frota: 0 a flutuar, 11 afundados!", " A | * - * - * - * - * - |",
				"| Maldito sejas, Java Sparrow, eu voltarei, glub glub glub ... |");
		assertFalse(output.contains("Bons ventos!"), output);
	}

	private String runMenu(String commands) throws Exception {
		Path input = Files.writeString(menuDirectory.resolve("input.txt"), commands);
		Path output = menuDirectory.resolve("output.txt");
		String classpath = String.join(File.pathSeparator, classLocation(Main.class),
				classLocation(LogManager.class), classLocation(LoggerContext.class),
				classLocation(ObjectMapper.class), classLocation(JsonFactory.class),
				classLocation(JsonProperty.class));
		String java = ProcessHandle.current().info().command()
				.orElseGet(() -> Path.of(System.getProperty("java.home"), "bin", "java").toString());

		Process process = new ProcessBuilder(java, "-ea", "-Dfile.encoding=UTF-8", "-cp", classpath,
				Main.class.getName())
				.directory(menuDirectory.toFile())
				.redirectInput(input.toFile())
				.redirectErrorStream(true)
				.redirectOutput(output.toFile())
				.start();
		try {
			assertTrue(process.waitFor(15, TimeUnit.SECONDS), "O menu não terminou no tempo esperado.");
			String transcript = Files.readString(output);
			assertEquals(0, process.exitValue(), transcript);
			return transcript;
		} finally {
			if (process.isAlive()) {
				process.destroyForcibly();
			}
		}
	}

	private static String classLocation(Class<?> type) throws URISyntaxException {
		return Path.of(type.getProtectionDomain().getCodeSource().getLocation().toURI()).toString();
	}

	private static void assertOutputOrder(String output, String... fragments) {
		int offset = 0;
		for (String fragment : fragments) {
			int index = output.indexOf(fragment, offset);
			assertTrue(index >= 0, () -> "Trecho em falta ou fora de ordem: " + fragment + "\n" + output);
			offset = index + fragment.length();
		}
	}
}
