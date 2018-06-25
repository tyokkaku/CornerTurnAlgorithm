public class Board {

    private int[][] board_;
    private int boardSideLength;

    // 進行方向
    private static final int Right = 0;
    private static final int Bottom = 1;
    private static final int Left = 2;
    private static final int Top = 3;

    // 二次元配列の初期値
    private static final int InitializeNum = 0;

    // 現在の進行方向
    private int currentTravelDirection;

    // 現在地点の座標
    // [0] Y軸
    // [1] X軸
    private int[] currentPoint = new int[2];

    /**
     * コンストラクタ。
     *
     * @param boardSideLength 二次元配列の一辺の長さ
     */
    private Board(int boardSideLength) {
        this.boardSideLength = boardSideLength;
    }

    /**
     * 現在地点の座標を得る
     *
     * @return 現在地点の座標
     */
    public int[] getPointState() {
        return currentPoint;
    }

    /**
     * 現在地点に数値を置く
     */
    private void putNumber(int putNum) {
        board_[currentPoint[0]][currentPoint[1]] = putNum;
    }

    /**
     * 次の座標に置けるか判定する
     *
     * @param nextPoint 現在地点の
     * @return 置けるならtrue
     */
    private boolean canPut(int[] nextPoint) {
        boolean result = false;

        if (nextPoint[0] >= boardSideLength || nextPoint[1] >= boardSideLength || nextPoint[0] < 0 || nextPoint[1] < 0) {
            // 辺からオーバーしたらFalse
            result = false;
        } else if (board_[nextPoint[0]][nextPoint[1]] > 0) {
            // 既に数字が置かれていたらFalse
            result = false;
        } else {
            result = true;
        }
        return result;
    }

    /**
     * 次の座標を得る
     *
     * @param travelDirection 進行方向
     */
    private int[] getNextPoint(int travelDirection) {

        int[] nextPoint = new int[2];
        int nextPointX;
        int nextPointY;

        switch (travelDirection) {
            case Right:
                nextPointX = currentPoint[1] + 1;
                nextPoint[1] = nextPointX;
                nextPointY = currentPoint[0];
                nextPoint[0] = nextPointY;
                break;
            case Bottom:
                nextPointX = currentPoint[1];
                nextPoint[1] = nextPointX;
                nextPointY = currentPoint[0] + 1;
                nextPoint[0] = nextPointY;
                break;
            case Left:
                nextPointX = currentPoint[1] - 1;
                nextPoint[1] = nextPointX;
                nextPointY = currentPoint[0];
                nextPoint[0] = nextPointY;
                break;
            case Top:
                nextPointX = currentPoint[1];
                nextPoint[1] = nextPointX;
                nextPointY = currentPoint[0] - 1;
                nextPoint[0] = nextPointY;
                break;
        }
        return nextPoint;
    }

    /**
     * 次の座標に移動する
     */
    private void moveNextPoint(int[] nextPoint) {
        currentPoint = nextPoint;
    }

    /**
     * 進行方向を切り替える
     *
     * @param travelDirection 現在の進行方向
     */
    private int changeDirection(int travelDirection) {
        return currentTravelDirection = (travelDirection + 1) % 4;
    }

    /**
     * ボードを初期化する
     *
     * @param board 二次元配列
     */
    private void prepareBoard(Board board) {

        // ボードの生成
        board_ = new int[board.boardSideLength][board.boardSideLength];

        // 初期値を設定する
        for (int i = 0; i < boardSideLength; i++) {
            for (int h = 0; h < boardSideLength; h++) {
                board_[i][h] = InitializeNum;
            }
        }

        // 進行方向の初期値を設定する
        currentTravelDirection = Right;

        // 現在地点の初期化
        currentPoint[0] = 0; // Y軸
        currentPoint[1] = 0; // X軸
    }

    /**
     * ボードを描画する
     */
    public void renderBoard() {
        System.out.println();
        for (int h = 0; h < boardSideLength; h++) {
            for (int i = 0; i < boardSideLength; i++) {
                if (board_[h][i] < 10) {
                    System.out.print("  " + board_[h][i] + " ");
                } else if (board_[h][i] < 100) {
                    System.out.print(" " + board_[h][i] + " ");
                } else {
                    System.out.print(board_[h][i] + " ");
                }
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Board board = new Board(10);

        // ボードの準備
        board.prepareBoard(board);

        for (int putNum = 1; putNum <= board.boardSideLength * board.boardSideLength; putNum++) {

            board.putNumber(putNum);

            // 次の座標に置けるなら、次の座標に移動する
            if (board.canPut(board.getNextPoint(board.currentTravelDirection))) {
                board.moveNextPoint(board.getNextPoint(board.currentTravelDirection));
            } else {
                // 次の座標に置けないなら、方向を変更して、次の座標に移動する
                board.changeDirection(board.currentTravelDirection);
                board.moveNextPoint(board.getNextPoint(board.currentTravelDirection));
            }
        }
        board.renderBoard();
    }
}
