package tobyspring.hellospring;

import java.util.Arrays;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SortTest {

    Sort sort;

    @BeforeEach
    void setup() {

        sort = new Sort();
    }

    @Test
    void sort() {

        List<String> lists = sort.sortByLength(Arrays.asList("aa", "b"));

        Assertions.assertThat(lists)
            .isEqualTo(List.of("b", "aa"));
    }

    @Test
    void sort3Items() {

        List<String> lists = sort.sortByLength(Arrays.asList("aa", "ccc", "b"));

        Assertions.assertThat(lists)
            .isEqualTo(List.of("b", "aa", "ccc"));
    }

    @Test
    void sortAlreadySorted() {

        List<String> lists = sort.sortByLength(Arrays.asList("b", "aa", "ccc"));

        Assertions.assertThat(lists)
            .isEqualTo(List.of("b", "aa", "ccc"));
    }
}