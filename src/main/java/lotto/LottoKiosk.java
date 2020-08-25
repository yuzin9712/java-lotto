package lotto;

import static java.util.stream.Collectors.toList;

import java.util.Collection;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import lotto.factory.LottoFactory;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LottoKiosk {

	public static final int LOTTO_PRICE = 1_000;

	public static Lottos issue(LottoPurchaseArgument argument) {
		List<Lotto> manualGeneratedLottos = argument.getManualLottoNumbersString()
													.stream()
													.map(LottoFactory::createManualLotto)
													.collect(toList());
		List<Lotto> autoGeneratedLottos = IntStream.range(0, argument.getAutoGeneratedLottosCount())
												   .mapToObj(i -> LottoFactory.create())
												   .collect(toList());

		return Lottos.of(Stream.of(manualGeneratedLottos, autoGeneratedLottos)
							   .flatMap(Collection::stream)
							   .collect(toList()));
	}
}