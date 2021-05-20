package lotto.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 로또 결과를 처리한다.
 */
public class GameResult {
    /**
     * 로또 결과.
     */
    private Map<Prize, Integer> result;
    /**
     * 구매한 로또.
     */
    private List<Lotto> purchasedLottos;

    public GameResult() {
        initialize();
    }

    public GameResult(List<Lotto> purchasedLottos) {
        initialize();
        this.purchasedLottos = new ArrayList<>(purchasedLottos);
    }

    private void initialize() {
        result = new HashMap<>();
        for (Prize prize : Prize.values()) {
            result.put(prize, 0);
        }
    }

    /**
     * 당첨 결과를 게임 결과에 추가한다.
     *
     * @param prize 당첨 결과
     */
    public void addWinResult(Prize prize) {
        result.put(prize, result.get(prize) + 1);
    }

    /**
     * 우승 결과를 가져온다.
     */
    public Integer getWinResult(Prize prize) {
        return result.get(prize);
    }

    /**
     * 수익률을 리턴한다.
     *
     * @param purchaseAmount 구매 금액
     * @return 수익률
     */
    public double getProfit(Money purchaseAmount) {
        return (double) getTotalPrizeAmount() / (double) purchaseAmount.getAmount();
    }

    private long getTotalPrizeAmount() {
        return result.keySet().stream()
                .mapToInt(this::getTotal)
                .sum();
    }

    /**
     * 로또 결과를 리턴한다.
     */
    public GameResult getResult(final Lotto winningLotto) {
        final GameResult gameResult = new GameResult(this.purchasedLottos);
        for (final Lotto lotto : purchasedLottos) {
            gameResult.addWinResult(this.getPrizeMatch(lotto, winningLotto));
        }
        return gameResult;
    }

    private int getTotal(final Prize prize) {
        return result.get(prize) * prize.getAmount();
    }

    /**
     * 주어진 로또와 우승 로또를 비교하여 상금을 리턴한다.
     *
     * @param lotto        주어진 로또
     * @param winningLotto 당첨 로또
     * @return 맞은 갯수에 해당하는 상금
     */
    public Prize getPrizeMatch(final Lotto lotto, Lotto winningLotto) {
        int matchCount = winningLotto.getLottoMatchCount(lotto);
        return Prize.of(matchCount);
    }
}