package calculator;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class StringCalculatorTest {
    @Test
    void NULL_또는_빈값으로_입력시_0_리턴() {
        int result = StringCalculator.splitAndSum(null);
        assertThat(result).isEqualTo(0);

        result = StringCalculator.splitAndSum("");
        assertThat(result).isEqualTo(0);
    }

    @Test
    void 숫자_하나를_문자열로_입력할_경우() {
        int result = StringCalculator.splitAndSum("123");
        assertThat(result).isEqualTo(123);
    }

    @Test
    void 커스텀_구분자로_계산() {
        String formula = "//]\n1]2]3]4";
        assertThat(StringCalculator.splitAndSum(formula)).isEqualTo(10);
    }

    @Test
    void 일반_구분자로_계산() {
        String formula = "1,2,3,4";
        assertThat(StringCalculator.splitAndSum(formula)).isEqualTo(10);
    }

    @Test
    void 특수_기호가_있을경우_RuntimeException() {
        assertThatThrownBy(() -> StringCalculator.splitAndSum("-1,2,3"))
                .isInstanceOf(RuntimeException.class);
    }
}