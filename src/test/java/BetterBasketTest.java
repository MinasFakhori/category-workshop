import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tk.minas.catalogue.BetterBasket;
import tk.minas.catalogue.Product;
import tk.minas.dbAccess.StockRW;
;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BetterBasketTest {
    Product pn1 = null;
    Product pn2 = null;

    Product pn3 = null;

    Product pn4 = null;

    BetterBasket bb = new BetterBasket();


    @BeforeEach
    public void before(){
        try {
            StockRW stock = new StockRW();

            pn1 =  stock.getDetails("0001");
            pn2 = stock.getDetails("0001");
            pn3 = stock.getDetails("0002");
            pn4 = stock.getDetails("0003");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    public void add() {
        bb.add(pn1 );
        bb.add(pn2);
        assertEquals(bb.size(), 1);

    }


    @Test
    public void sort(){
        bb.add(pn3);
        bb.add(pn1);
        bb.add(pn4);

        assertEquals(bb.get(0).getProductNum() , "0001");
        assertEquals(bb.get(1).getProductNum() , "0002");
        assertEquals(bb.get(2).getProductNum() , "0003");

    }

}