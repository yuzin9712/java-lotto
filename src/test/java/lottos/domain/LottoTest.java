package lottos.domain;

import lottos.converter.IntListConverter;
import lottos.domain.exceptions.LottoDuplicationNumberException;
import lottos.domain.exceptions.LottoNumberRangeIncorrectException;
import lottos.domain.exceptions.LottoSizeIncorrectException;
import lottos.domain.numbers.Number;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LottoTest {


    @ParameterizedTest
    @CsvSource(value = {"1,2,3,4,5", "1,2,3,4,5,6,7"}, delimiter = ':')
    void 로또넘버가_여섯개가_아닌_로또면_에러(@ConvertWith(IntListConverter.class) final List<Integer> numbers) {
        assertThrows(LottoSizeIncorrectException.class, () -> new Lotto(numbers));
    }

    @ParameterizedTest
    @CsvSource(value = {"1,2,3,4,5,70", "1,2,3,4,50,6"}, delimiter = ':')
    void 로또의_숫자_범위가_아닐경우_에러(@ConvertWith(IntListConverter.class) final List<Integer> numbers) {
        assertThrows(LottoNumberRangeIncorrectException.class, () -> new Lotto(numbers));
    }

    @Test
    void 로또_개수_검증() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);

        Lotto lotto = new Lotto(numbers);

        assertEquals(lotto.getNumbers().elements().size(), numbers.size());
    }

    @Test
    void 로또_범위_검증() {

        // given
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
        Collections.sort(numbers);

        final int min = numbers.get(0);
        final int max = numbers.get(numbers.size() - 1);

        // when
        Lotto lotto = new Lotto(numbers);

        // then
        for (Number number : lotto.getNumbers().elements()) {
            assertTrue(number.value() >= min && number.value() <= max);
        }
    }

    @Test
    void 중복_숫자_검증() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 5);
        assertThrows(LottoDuplicationNumberException.class, () -> new Lotto(numbers));
    }
}