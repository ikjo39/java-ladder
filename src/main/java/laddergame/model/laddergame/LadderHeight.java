package laddergame.model.laddergame;

public class LadderHeight {
    private static final int MINIMUM_LADDER_HEIGHT = 1;

    private final int height;

    public LadderHeight(int height) {
        validateLadderHeight(height);
        this.height = height;
    }

    private void validateLadderHeight(int height) {
        if (height < MINIMUM_LADDER_HEIGHT) {
            String message = "[ERROR] 사다리 높이는 1보다 작을 수 없습니다. 입력값: " + height;
            throw new IllegalArgumentException(message);
        }
    }

    public int getHeight() {
        return height;
    }
}
