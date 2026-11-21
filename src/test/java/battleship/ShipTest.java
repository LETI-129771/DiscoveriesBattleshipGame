package battleship;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;
import java.util.List;

/**
 * Test class for Ship.
 * Author: ${user.name}
 * Date: ${current_date}
 * Time: ${current_time}
 * Cyclomatic Complexity for each method:
 * - Constructor: 1
 * - getCategory: 1
 * - getSize: 1
 * - getBearing: 1
 * - getPositions: 1
 * - stillFloating: 2
 * - shoot: 2
 * - occupies: 2
 * - tooCloseTo (IShip): 2
 * - tooCloseTo (IPosition): 2
 * - getTopMostPos: 2
 * - getBottomMostPos: 2
 * - getLeftMostPos: 2
 * - getRightMostPos: 2
 */
public class ShipTest {

    private Ship ship, shipBorder;

    @BeforeEach
    void setUp() {
        // Since Ship is abstract, instantiate it with a concrete subclass (e.g., Barge)
        ship = new Barge(Compass.NORTH, new Position(5, 5));
        shipBorder = new Barge(Compass.NORTH, new Position(0, 0));
    }

    @AfterEach
    void tearDown() {
        ship = null;
    }

    /**
     * Test for the constructor.
     * Cyclomatic Complexity: 1
     */
    @Test
    void testConstructor() {
        assertNotNull(ship, "Error: Ship instance should not be null.");
        assertEquals("Barca", ship.getCategory(), "Error: Ship category is incorrect.");
        assertEquals(Compass.NORTH, ship.getBearing(), "Error: Ship bearing is incorrect.");
        assertEquals(1, ship.getSize(), "Error: Ship size is incorrect.");
        assertFalse(ship.getPositions().isEmpty(), "Error: Ship positions should not be empty.");
    }

    /**
     * Test for the getCategory method.
     * Cyclomatic Complexity: 1
     */
    @Test
    void testGetCategory() {
        assertEquals("Barca", ship.getCategory(), "Error: Ship category should be 'Barca'.");
    }

    /**
     * Test for the getSize method.
     * Cyclomatic Complexity: 1
     */
    @Test
    void testGetSize() {
        assertEquals(1, ship.getSize(), "Error: Ship size should be 1.");
    }

    /**
     * Test for the getBearing method.
     * Cyclomatic Complexity: 1
     */
    @Test
    void testGetBearing() {
        assertEquals(Compass.NORTH, ship.getBearing(), "Error: Ship bearing should be NORTH.");
    }

    /**
     * Test for the getPositions method.
     * Cyclomatic Complexity: 1
     */
    @Test
    void testGetPositions() {
        List<IPosition> positions = ship.getPositions();
        assertNotNull(positions, "Error: Ship positions should not be null.");
        assertEquals(1, positions.size(), "Error: Ship should have exactly one position.");
        assertEquals(5, positions.get(0).getRow(), "Error: Position's row should be 5.");
        assertEquals(5, positions.get(0).getColumn(), "Error: Position's column should be 5.");
    }

    /**
     * Test for the stillFloating method (all positions intact).
     * Cyclomatic Complexity: 2
     */
    @Test
    void testStillFloating1() {
        assertTrue(ship.stillFloating(), "Error: Ship should still be floating.");
    }

    /**
     * Test for the stillFloating method (all positions hit).
     */
    @Test
    void testStillFloating2() {
        ship.getPositions().get(0).shoot();
        assertFalse(ship.stillFloating(), "Error: Ship should no longer be floating after being hit.");
    }

    /**
     * Test for the shoot method (valid position).
     * Cyclomatic Complexity: 2
     */
    @Test
    void testShoot1() {
        Position target = new Position(5, 5);
        ship.shoot(target);
        assertTrue(ship.getPositions().get(0).isHit(), "Error: Position should be marked as hit.");
    }

    /**
     * Test for the shoot method (invalid position).
     */
    @Test
    void testShoot2() {
        Position target = new Position(0, 0);
        ship.shoot(target); // No exception expected
        assertFalse(ship.getPositions().get(0).isHit(), "Error: Position should not be marked as hit for an invalid target.");
    }

    /**
     * Test for the occupies method (position occupied).
     * Cyclomatic Complexity: 2
     */
    @Test
    void testOccupies1() {
        Position pos = new Position(5, 5);
        assertTrue(ship.occupies(pos), "Error: Ship should occupy position (5, 5).");
    }

    /**
     * Test for the occupies method (position not occupied).
     */
    @Test
    void testOccupies2() {
        Position pos = new Position(1, 1);
        assertFalse(ship.occupies(pos), "Error: Ship should not occupy position (1, 1).");
    }

    /**
     * Test for the tooCloseTo method with another IShip (ships too close).
     * Cyclomatic Complexity: 2
     */
    @Test
    void testTooCloseToShip1() {
        Ship nearbyShip = new Barge(Compass.NORTH, new Position(5, 6));
        assertTrue(ship.tooCloseTo(nearbyShip), "Error: Ships should be too close.");
    }

    /**
     * Test for the tooCloseTo method with another IShip (ships not close).
     */
    @Test
    void testTooCloseToShip2() {
        Ship farShip = new Barge(Compass.NORTH, new Position(10, 10));
        assertFalse(ship.tooCloseTo(farShip), "Error: Ships should not be too close.");
    }

    /**
     * Test for the tooCloseTo method with an IPosition (positions adjacent).
     * Cyclomatic Complexity: 2
     */
    @Test
    void testTooCloseToPosition1() {
        Position pos = new Position(5, 6); // Adjacent position
        assertTrue(ship.tooCloseTo(pos), "Error: Ship should be too close to the given position.");
    }

    /**
     * Test for the tooCloseTo method with an IPosition (positions not adjacent).
     */
    @Test
    void testTooCloseToPosition2() {
        Position pos = new Position(7, 7); // Non-adjacent position
        assertFalse(ship.tooCloseTo(pos), "Error: Ship should not be too close to the given position.");
    }

    /**
     * Test for the getTopMostPos method.
     * Cyclomatic Complexity: 2
     */
    @Test
    void testGetTopMostPos() {
        assertEquals(5, ship.getTopMostPos(), "Error: The topmost position should be 5.");
    }

    /**
     * Test for the getBottomMostPos method.
     * Cyclomatic Complexity: 2
     */
    @Test
    void testGetBottomMostPos() {
        assertEquals(5, ship.getBottomMostPos(), "Error: The bottommost position should be 5.");
    }

    /**
     * Test for the getLeftMostPos method.
     * Cyclomatic Complexity: 2
     */
    @Test
    void testGetLeftMostPos() {
        assertEquals(5, ship.getLeftMostPos(), "Error: The leftmost position should be 5.");
    }

    /**
     * Test for the getRightMostPos method.
     * Cyclomatic Complexity: 2
     */
    @Test
    void testGetRightMostPos() {
        assertEquals(5, ship.getRightMostPos(), "Error: The rightmost position should be 5.");
    }

    /**
     * Test for the getRightMostPos method.
     * Cyclomatic Complexity: 2
     */
    @Test
    void testGetAdjacentPositions() {
        assertEquals(8, ship.getAdjacentPositions().size(), "Error: The number of adjacent positions of a barge in the middle of the tray should be 8.");
        assertEquals(3, shipBorder.getAdjacentPositions().size(), "Error: The number of adjacent positions of a barge in the middle of the tray should be 8.");
    }

    @Test
    @DisplayName("Test ship creation and position")
    void testShipInitialization() {
        // Arrange & Act
        Ship testShip = new Barge(Compass.NORTH, new Position(3, 4));

        // Assert
        assertNotNull(testShip, "O navio não deve ser nulo após a instanciação.");
        assertNotNull(testShip.getPosition(), "A posição inicial não deve ser nula.");
    }

    @ParameterizedTest(name = "{0} cria {1} com {2} posições")
    @CsvSource({
            "barca, Barca, 1",
            "caravela, Caravela, 2",
            "nau, Nau, 3",
            "fragata, Fragata, 4",
            "galeao, Galeao, 5"
    })
    @DisplayName("A fábrica cria os cinco tipos de navio")
    void buildShipCreatesKnownTypes(String kind, String category, int size) {
        Position start = new Position(4, 4);

        Ship built = Ship.buildShip(kind, Compass.NORTH, start);

        assertEquals(category, built.getCategory());
        assertEquals(size, built.getSize());
        assertEquals(size, built.getPositions().size());
        assertEquals(Compass.NORTH, built.getBearing());
        assertEquals(start, built.getPosition());
    }

    @Test
    @DisplayName("A fábrica devolve null para um tipo desconhecido")
    void buildShipReturnsNullForUnknownType() {
        assertNull(Ship.buildShip("desconhecido", Compass.NORTH, new Position(4, 4)));
    }

    @Test
    @DisplayName("A fábrica rejeita argumentos nulos com asserções Java ativas")
    void buildShipRejectsNullArguments() {
        Position start = new Position(4, 4);

        assertThrows(AssertionError.class, () -> Ship.buildShip(null, Compass.NORTH, start));
        assertThrows(AssertionError.class, () -> Ship.buildShip("barca", null, start));
        assertThrows(AssertionError.class, () -> Ship.buildShip("barca", Compass.NORTH, null));
    }

    @Test
    @DisplayName("Os vizinhos de uma caravela no canto excluem casas ocupadas e duplicados")
    void adjacentPositionsOfCaravelAtCorner() {
        Ship caravel = new Caravel(Compass.EAST, new Position(0, 0));
        List<IPosition> neighbors = caravel.getAdjacentPositions();

        assertEquals(4, neighbors.size());
        assertTrue(neighbors.containsAll(List.of(
                new Position(0, 2), new Position(1, 0),
                new Position(1, 1), new Position(1, 2))));
    }

    @Test
    @DisplayName("Os extremos de um galeão são calculados a partir de todas as posições")
    void extremaOfGalleon() {
        Ship galleon = new Galleon(Compass.EAST, new Position(4, 4));
        // Put the bottom cell first so the minimum row must be updated.
        Collections.reverse(galleon.getPositions());

        assertAll(
                () -> assertEquals(4, galleon.getTopMostPos()),
                () -> assertEquals(6, galleon.getBottomMostPos()),
                () -> assertEquals(2, galleon.getLeftMostPos()),
                () -> assertEquals(4, galleon.getRightMostPos()));
    }

    @ParameterizedTest(name = "{0} em ({1}, {2}) → {3}")
    @CsvSource({
            "EAST, 9, 8, true",
            "EAST, 4, -1, false",
            "EAST, 4, 9, false",
            "NORTH, -1, 4, false",
            "NORTH, 9, 4, false"
    })
    @DisplayName("A caravela pode tocar nos limites mas não ultrapassar nenhum lado do tabuleiro")
    void caravelBoardBoundaries(Compass bearing, int row, int column, boolean expected) {
        Ship caravel = new Caravel(bearing, new Position(row, column));

        assertEquals(expected, caravel.isInsideBoard());
    }

    @Test
    @DisplayName("A nau deteta ocupação e proximidade a partir de posições além da primeira")
    void carrackChecksAllPositions() {
        Ship carrack = new Carrack(Compass.EAST, new Position(5, 4));
        Ship caravel = new Caravel(Compass.EAST, new Position(6, 2));

        assertTrue(carrack.occupies(new Position(5, 6)));
        assertFalse(carrack.occupies(new Position(5, 7)));
        assertTrue(carrack.tooCloseTo(new Position(6, 7)));
        assertTrue(carrack.tooCloseTo(caravel));
    }

    @Test
    @DisplayName("Um tiro marca apenas a casa atingida e a nau flutua até ao último impacto")
    void carrackFloatsUntilAllPositionsAreHit() {
        Ship carrack = new Carrack(Compass.EAST, new Position(5, 4));
        List<IPosition> cells = carrack.getPositions();

        carrack.shoot(new Position(5, 5));
        assertFalse(cells.get(0).isHit());
        assertTrue(cells.get(1).isHit());
        assertFalse(cells.get(2).isHit());

        carrack.shoot(new Position(5, 4));
        assertTrue(carrack.stillFloating());

        carrack.shoot(new Position(5, 6));
        assertFalse(carrack.stillFloating());
    }

    @Test
    @DisplayName("Afundar uma nau marca todas as suas posições como atingidas")
    void sinkHitsEveryPosition() {
        Ship carrack = new Carrack(Compass.EAST, new Position(5, 4));

        carrack.sink();

        assertTrue(carrack.getPositions().stream().allMatch(IPosition::isHit));
        assertFalse(carrack.stillFloating());
    }

    @Test
    @DisplayName("As consultas de ocupação e proximidade rejeitam null com asserções Java ativas")
    void queriesRejectNullArguments() {
        assertThrows(AssertionError.class, () -> ship.occupies(null));
        assertThrows(AssertionError.class, () -> ship.tooCloseTo((IPosition) null));
        assertThrows(AssertionError.class, () -> ship.tooCloseTo((IShip) null));
    }

    @Test
    @DisplayName("Um tiro rejeita null e coordenadas fora do tabuleiro com asserções Java ativas")
    void shootRejectsInvalidTargets() {
        assertThrows(AssertionError.class, () -> ship.shoot(null));
        assertThrows(AssertionError.class, () -> ship.shoot(new Position(-1, 0)));
    }

    @Test
    @DisplayName("A descrição do navio inclui categoria, orientação e posição inicial")
    void shipDescription() {
        Ship caravel = new Caravel(Compass.EAST, new Position(4, 4));

        assertEquals("[Caravela e E5]", caravel.toString());
    }
}
