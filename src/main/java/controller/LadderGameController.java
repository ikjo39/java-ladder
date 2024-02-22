package controller;

import model.Ladder;
import model.LadderGame;
import model.LadderHeight;
import model.Participants;
import model.RandomGenerator;
import view.InputView;
import view.OutputView;

public class LadderGameController {

    private final InputView inputView;
    private final OutputView outputView;

    public LadderGameController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        Participants participants = inputView.readParticipantNames();
        int height = inputView.readLadderHeight();

        Ladder ladder = createLadder(height, participants);

        printResult(participants, ladder);
    }

    private Ladder createLadder(int height, Participants participants) {
        LadderHeight ladderHeight = new LadderHeight(height);
        RandomGenerator generator = new RandomGenerator();
        LadderGame ladderGame = new LadderGame(ladderHeight, participants, generator);
        return ladderGame.createLadder();
    }

    private void printResult(Participants participants, Ladder ladder) {
        outputView.printResultHeader();
        outputView.printParticipantsNames(participants);
        outputView.printLadder(ladder);
    }
}
