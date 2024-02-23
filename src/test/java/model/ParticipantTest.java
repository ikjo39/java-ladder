package model;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class ParticipantTest {
    @DisplayName("참여할 사람의 이름은 5자를 초과할 수 없다.")
    @ParameterizedTest
    @ValueSource(strings = {"123456", "어쩌다마주친그대", "그대나의챔피언너와나의챔피언"})
    void validateNameLength(String given) {
        assertThatThrownBy(() -> new Participant(given))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 참여자 이름의 길이는 ")
                .hasMessageContaining("자를 초과할 수 없다.");
    }

    @DisplayName("참여할 사람의 이름은 null이거나 공백이면 안된다.")
    @ParameterizedTest
    @NullSource
    @EmptySource
    void validateNameNotNullAndNotBlank(String given) {
        assertThatThrownBy(() -> new Participant(given))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 참여자 이름은 null이거나 공백일 수 없다.");
    }
}
