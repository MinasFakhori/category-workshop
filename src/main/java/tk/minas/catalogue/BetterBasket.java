package tk.minas.catalogue;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Write a description of class BetterBasket here.
 *
 * @author  Your Name
 * @version 1.0
 */
public class BetterBasket extends Basket implements Serializable {
    private static final long serialVersionUID = 1L;


    @Override
    public boolean add(Product pr) {
            for (int i = 0; i < size(); i++) {
                if (pr.getProductNum().equals(get(i).getProductNum())) {
                    pr.setQuantity(get(i).getQuantity() + 1);
                    remove(get(i));
                }
            }

            for(int j=0; j < this.size(); j++){
                int pnInt = Integer.parseInt(pr.getProductNum());
                int bAtJ =Integer.parseInt(get(j).getProductNum());
                if (pnInt < bAtJ){
                    add(j , pr);
                    return true;
                }
            }


        return super.add(pr);
    }

}
