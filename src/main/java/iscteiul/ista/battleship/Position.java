package iscteiul.ista.battleship;

import java.util.Objects;

/**
 * Representa uma posição no tabuleiro da Batalha Naval.
 * Guarda a linha, a coluna, se está ocupada e se já recebeu um tiro.
 */

public class Position implements IPosition {
    private int row;
    private int column;
    private boolean isOccupied;
    private boolean isHit;

    /**
     * Cria uma posição que começa livre e sem ter recebido tiros.
     */
    public Position(int row, int column) {
        this.row = row;
        this.column = column;
        this.isOccupied = false;
        this.isHit = false;
    }

    /**
     * Devolve a linha da posição.
     *
     * @return linha da posição
     */
    @Override
    public int getRow() {
        return row;
    }

    /**
     * Devolve a coluna da posição.
     *
     * @return coluna da posição
     */
    @Override
    public int getColumn() {
        return column;
    }

    /**
     * Calcula um código hash usando a linha, a coluna e os estados
     * de ocupação e de tiro.
     *
     * @return código hash da posição
     */

    @Override
    public int hashCode() {
        return Objects.hash(column, isHit, isOccupied, row);
    }

    /**
     * Verifica se o objeto recebido representa uma posição
     * com a mesma linha e coluna.
     * Os estados de ocupação e de tiro não são comparados.
     *
     * @return true se o objeto implementar IPosition e tiver
     *         a mesma linha e coluna; false caso contrário
     */

    @Override
    public boolean equals(Object otherPosition) {
        if (this == otherPosition)
            return true;
        if (otherPosition instanceof IPosition) {
            IPosition other = (IPosition) otherPosition;
            return (this.getRow() == other.getRow() && this.getColumn() == other.getColumn());
        } else {
            return false;
        }
    }

    /**
     * Verifica se a posição recebida está a uma distância máxima
     * de uma linha e uma coluna desta posição.
     * Inclui as diagonais e a própria posição.
     * @return true se as diferenças de linha e coluna forem
     *         ambas menores ou iguais a 1; false caso contrário
     * @throws NullPointerException se a posição recebida for null
     */

    @Override
    public boolean isAdjacentTo(IPosition other) {
        return (Math.abs(this.getRow() - other.getRow()) <= 1 && Math.abs(this.getColumn() - other.getColumn()) <= 1);
    }

    /**
     * Marca a posição como ocupada.
     */

    @Override
    public void occupy() {
        isOccupied = true;
    }

    /**
     * Marca a posição como tendo recebido um tiro,
     * mesmo que não esteja ocupada.
     */

    @Override
    public void shoot() {
        isHit = true;
    }

    /**
     * Indica se a posição está ocupada.
     *
     * @return true se estiver ocupada; false caso contrário
     */

    @Override
    public boolean isOccupied() {
        return isOccupied;
    }

    /**
     * Indica se a posição já recebeu um tiro.
     *
     * @return true se já recebeu um tiro; false caso contrário
     */

    @Override
    public boolean isHit() {
        return isHit;
    }

    /**
     * Devolve um texto com a linha e a coluna da posição.
     *
     * @return texto no formato "Linha = X Coluna = Y"
     */

    @Override
    public String toString() {
        return ("Linha = " + row + " Coluna = " + column);
    }

}
