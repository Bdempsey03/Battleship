package Logic;

public class Game {
    GameBoard humanGameBoard;
    GameBoard cpuGameBoard;
    Player cpu;
    Player human;

    public Game(){
        humanGameBoard = new GameBoard(10, 10);
        cpuGameBoard = new GameBoard(10, 10);
        cpu = new CPU(this);
        human = new Human(this);
    }


    public GameBoard getHumanBoard(){
        return this.humanGameBoard;
    }


    public GameBoard getCpuBoard() {
        return cpuGameBoard;
    }
}
