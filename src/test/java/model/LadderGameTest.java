package model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class LadderGameTest {
    @DisplayName("사다리 높이가 0 이하면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(ints = {-5, -1, 0})
    void validateLadderHeight(int given) {
        Participants participants = new Participants(List.of(
                new Participant("daon"),
                new Participant("ash"),
                new Participant("ted")
        ));
        RandomGenerator randomGenerator = new RandomGenerator();

        assertThatThrownBy(() -> new LadderGame(given, participants, randomGenerator))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 사다리 높이는")
                .hasMessageContaining("이상의 정수이어야 한다.");
    }

    @DisplayName("사다리 높이와, 참여자 수, 생성기를 이용하여 사다리를 반환한다.")
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 10, 100})
    void createLadder(int givenHeight) {
        Participants participants = new Participants(List.of(
                new Participant("daon"),
                new Participant("ash"),
                new Participant("ted")
        ));
        RandomGenerator randomGenerator = new RandomGenerator();

        LadderGame ladderGame = new LadderGame(givenHeight, participants, randomGenerator);
        Ladder result = ladderGame.createLadder();

        assertThat(result.getLines()).hasSize(givenHeight);
    }
}
