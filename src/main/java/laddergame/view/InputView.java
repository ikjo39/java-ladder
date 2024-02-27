package laddergame.view;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.Scanner;
import java.util.function.Supplier;
import laddergame.model.ExecutionResult;
import laddergame.model.ExecutionResults;
import laddergame.model.LadderHeight;
import laddergame.model.Participant;
import laddergame.model.Participants;

public class InputView {
    private final Scanner scanner = new Scanner(System.in);

    public Participants readParticipantNames() {
        return repeatUntilSuccess(() -> {
            System.out.println("참여할 사람 이름을 입력하세요. (이름은 쉼표(,)로 구분하세요)");
            String input = scanner.nextLine();
            validateMultipleInputs(input);
            return Arrays.stream(input.split(","))
                    .map(Participant::new)
                    .collect(collectingAndThen(toList(), Participants::new));
        });
    }

    public ExecutionResults readExecutionResults(Participants participants) {
        return repeatUntilSuccess(() -> {
            System.out.println();
            System.out.println("실행 결과를 입력하세요. (결과는 쉼표(,)로 구분하세요)");
            String input = scanner.nextLine();
            validateMultipleInputs(input);
            return Arrays.stream(input.split(","))
                    .map(ExecutionResult::new)
                    .collect(collectingAndThen(toList(), results -> new ExecutionResults(results, participants)));
        });
    }

    public LadderHeight readLadderHeight() {
        return repeatUntilSuccess(() -> {
            System.out.println();
            System.out.println("최대 사다리 높이는 몇 개인가요?");
            String input = scanner.nextLine();
            int height = parseInt(input);
            return new LadderHeight(height);
        });
    }

    private void validateMultipleInputs(String input) {
        if (input == null || input.isBlank() || input.endsWith(",")) {
            throw new IllegalArgumentException("[ERROR] 입력값은 공백이거나 구분자(,)로 끝날 수 없다.");
        }
    }

    private int parseInt(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 입력값은 숫자형식이어야 한다.");
        }
    }

    private <T> T repeatUntilSuccess(Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return repeatUntilSuccess(supplier);
        }
    }
}
