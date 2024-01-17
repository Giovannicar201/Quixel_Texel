package QuixelTexel.GMP;

import QuixelTexel.IS.Exception.GST.InvalidFillPercentageException;
import QuixelTexel.IS.Exception.GST.InvalidNumberOfPriorityException;
import QuixelTexel.IS.Exception.GST.InvalidPriorityPercentageSumException;
import QuixelTexel.IS.Service.GMP.GMP.MappaServiceImpl;
import QuixelTexel.IS.Service.GST.ScatteringServiceImpl;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class ScatteringServiceTest {

    @Mock
    private MappaServiceImpl mappaServiceImpl;
    @InjectMocks
    private ScatteringServiceImpl scatteringServiceImpl;


    @Before
    public void setup() {

        MockitoAnnotations.initMocks(this);

    }

    @Test
    void TC1_GMP(){

        assertThrows(InvalidFillPercentageException.class,
                () -> scatteringServiceImpl.scatter("mappa", "1", "1", "11", "11", null, List.of("a","b","c","d","e","f","g","h","i"), List.of("10","20","0","0","0","50","15","5","0"),"test@test.com"));

    }

    @Test
    void TC2_GMP(){

        assertThrows(InvalidFillPercentageException.class,
                () -> scatteringServiceImpl.scatter("mappa", "1", "1", "11", "11", "-2", List.of("a","b","c","d","e","f","g","h","i"), List.of("10","20","0","0","0","50","15","5","0"),"test@test.com"));

    }

    @Test
    void TC3_GMP(){

        assertThrows(InvalidFillPercentageException.class,
                () -> scatteringServiceImpl.scatter("mappa", "1", "1", "11", "11", "110", List.of("a","b","c","d","e","f","g","h","i"), List.of("10","20","30","0","0","20","10","10","0"),"test@test.com"));

    }

    @Test
    void TC4_GMP(){

        assertThrows(InvalidNumberOfPriorityException.class,
                () -> scatteringServiceImpl.scatter("mappa", "0", "0", "1", "1", "75", List.of("a","b","c","d","e","f","g","h","i"), List.of("10","20","30","0","0","20","10","10","0"),"test@test.com"));

    }

    @Test
    void TC5_GMP(){

        assertThrows(InvalidPriorityPercentageSumException.class,
                () -> scatteringServiceImpl.scatter("mappa", "1", "1", "11", "11", "75", List.of("a","b","c","d","e","f","g","h","i"),List.of("75", "50", "0", "0", "0", "0", "0", "0", "0"),"test@test.com"));

    }

    @Test
    void TC6_GMP(){

        assertDoesNotThrow(
                () -> scatteringServiceImpl.scatter("mappa", "1", "1", "11", "11", "75", List.of("a","b","c","d","e","f","g","h","i"),List.of("50","25","20","5", "0", "0", "0", "0", "0"),"test@test.com"));

    }

}
