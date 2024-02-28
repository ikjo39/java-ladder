package laddergame.model;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ExecutionResultsTest {
    @DisplayName("실행 결과 수가 참가자 수와 동일하지 않는 경우 예외를 발생한다.")
    @Test
    void validateSameCountWithParticipants() {
        List<ExecutionResult> executionResults = Stream.of("1", "2", "3", "4")
                .map(ExecutionResult::new)
                .toList();
        Participants participants = Stream.of("daon", "ash", "dao", "ted", "12")
                .map(Participant::new)
                .collect(collectingAndThen(toList(), Participants::new));

        assertThatThrownBy(() -> new ExecutionResults(executionResults, participants))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("인덱스에 해당하는 실행 결과를 찾는다.")
    @Nested
    class findByIndex {
        @DisplayName("각 인덱스에 해당하는 참가자를 반환한다.")
        @Test
        void find() {
            Participants participants = Stream.of("daon", "ash", "dao", "ted")
                    .map(Participant::new)
                    .collect(collectingAndThen(toList(), Participants::new));

            ExecutionResults executionResults = Stream.of("꽝", "꽝", "당첨", "청소")
                    .map(ExecutionResult::new)
                    .collect(collectingAndThen(toList(), results -> new ExecutionResults(results, participants)));

            assertAll(
                    () -> assertThat(executionResults.findByIndex(0).getName()).isEqualTo("꽝"),
                    () -> assertThat(executionResults.findByIndex(1).getName()).isEqualTo("꽝"),
                    () -> assertThat(executionResults.findByIndex(2).getName()).isEqualTo("당첨"),
                    () -> assertThat(executionResults.findByIndex(3).getName()).isEqualTo("청소")
            );
        }

        @DisplayName("유효하지 않는 인덱스로 조회하면 예외를 발생한다.")
        @ParameterizedTest
        @ValueSource(ints = {-10, -2, -1, 5, 6, 7, 8})
        void findInvalidIndex(int given) {
            Participants participants = Stream.of("daon", "ash", "dao", "ted")
                    .map(Participant::new)
                    .collect(collectingAndThen(toList(), Participants::new));

            ExecutionResults executionResults = Stream.of("꽝", "꽝", "당첨", "청소")
                    .map(ExecutionResult::new)
                    .collect(collectingAndThen(toList(), results -> new ExecutionResults(results, participants)));
            assertThatThrownBy(() -> executionResults.findByIndex(given))
                    .isInstanceOf(IllegalStateException.class);
        }
    }
}
