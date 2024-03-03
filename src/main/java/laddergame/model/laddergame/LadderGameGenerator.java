package laddergame.model.laddergame;

import java.util.List;
import java.util.stream.Collectors;

public class LadderGameGenerator {
    private final List<List<Boolean>> doublyBooleans;

    public LadderGameGenerator(List<List<Boolean>> doublyBooleans) {
        validateBooleans(doublyBooleans);
        this.doublyBooleans = doublyBooleans;
    }

    private void validateBooleans(List<List<Boolean>> doublyBooleans) {
        if (doublyBooleans == null || doublyBooleans.isEmpty() || checkBooleansSameSize(doublyBooleans)) {
            throw new IllegalArgumentException("[ERROR] 유효하지 않는 값으로 사다리 게임 생성기를 만들 수 없습니다.");
        }
    }

    private boolean checkBooleansSameSize(List<List<Boolean>> doublyBooleans) {
        return doublyBooleans.stream()
                .anyMatch(booleans -> booleans.size() != doublyBooleans.get(0).size());
    }

    public LadderGame getLadderGame() {
        return doublyBooleans.stream()
                .map(LineGenerator::new)
                .map(LineGenerator::generate)
                .collect(Collectors.collectingAndThen(Collectors.toList(), LadderGame::new));
    }
}