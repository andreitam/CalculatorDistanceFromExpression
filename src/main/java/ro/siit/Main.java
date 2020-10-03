package ro.siit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Hello world!
 *
 */
public class Main
{
    public static void main( String[] args )
    {
        Calculator calc=new Calculator("10 cm + 1 m - 10 mm");
        String result = calc.getDistance();
        System.out.println(result);
    }
}
