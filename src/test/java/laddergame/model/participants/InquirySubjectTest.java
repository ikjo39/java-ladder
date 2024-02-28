package laddergame.model.participants;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class InquirySubjectTest {
    @DisplayName("조회 대상자가 all 또는 참여자들 이름이 아니면 예외를 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"holly", "kelly", "woni", "pobi"})
    void validateNotInParticipants(String given) {
        //given
        Participant subject = new Participant(given);
        Participants participants = Stream.of("daon", "mason", "ted", "jk")
                .map(Participant::new)
                .collect(collectingAndThen(toList(), Participants::new));
        //when //then
        assertThatThrownBy(() -> new InquirySubject(subject, participants))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("조회 대상자가 참여자들에 위치한 인덱스를 반환한다.")
    @Nested
    class getSubjectIndex {
        @DisplayName("조회 대상자가 참여자들에 위치한 인덱스를 반환한다.")
        @ParameterizedTest
        @CsvSource(value = {"daon,0", "mason,1", "ted,2", "jk,3"})
        void getIndex(String given, int expected) {
            //given
            Participant subject = new Participant(given);
            Participants participants = Stream.of("daon", "mason", "ted", "jk")
                    .map(Participant::new)
                    .collect(collectingAndThen(toList(), Participants::new));
            //when
            InquirySubject inquirySubject = new InquirySubject(subject, participants);
            List<IndexInfo> result = inquirySubject.getIndexInfos();
            //then
            assertThat(result).hasSize(1);
            assertThat(result.get(0).getIndex()).isEqualTo(expected);
        }

        @DisplayName("조회 대상자가 all이면 모든 참여자들이 위치한 인덱스를 반환한다.")
        @Test
        void getAllIndex() {
            //given
            Participant subject = new Participant("all");
            Participants participants = Stream.of("daon", "mason", "ted", "jk")
                    .map(Participant::new)
                    .collect(collectingAndThen(toList(), Participants::new));
            //when
            InquirySubject inquirySubject = new InquirySubject(subject, participants);
            List<IndexInfo> result = inquirySubject.getIndexInfos();
            //then
            assertThat(result).hasSize(participants.getSize());
        }
    }
}

