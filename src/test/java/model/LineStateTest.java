package model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class LineStateTest {
    @DisplayName("true로 LineState를 생성하면 START를 반환한다")
    @ParameterizedTest
    @CsvSource(value = {"true,START", "false,NONE"})
    void decideLineState(boolean decision, LineState expected) {
        LineState actual = LineState.decideLineState(decision);
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("이전 State가 START이면 END를 반환하고 이외에는 boolean 값에 따라 결과를 반환한다")
    @ParameterizedTest
    @CsvSource(value = {"START,true,END", "START,false,END", "NONE,true,START", "END,false,NONE"})
    void decideLineStateWithBeforeState(LineState beforeState, boolean given, LineState expected) {
        LineState actual = LineState.decideLineStateWithBeforeState(beforeState, given);
        assertThat(actual).isEqualTo(expected);
    }
}
