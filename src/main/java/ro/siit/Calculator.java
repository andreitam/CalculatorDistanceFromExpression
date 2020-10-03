package ro.siit;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Write a calculator that is capable of computing a metric distance
 * value from an expression that contains different scales and systems.
 * Output should be specified by the user.
 * Only Addition and subtraction is allowed.
 * Supported formats: mm, cm, dm, m, km.
 * Examples: Output is in lowest unit. Expression: 10 cm + 1 m - 10 mm. Result: 1090 mm
 *
 * @author  Andrei Tamasanu
 * @version 1.0
 * @since   2020-10-02
 */
public class Calculator {
    private String expression;
    /**
     * Expression will be passed in the constructor and validated
     *
     * @param expression- to be calculated
     */
    public Calculator(String expression) {
        this.expression = processExpression(expression);
    }
    /**
     * Method to trim and validate the input expression.
     * Only digits, word characters and + and minus signs are permitted
     *
     * @param expression -  String to validate
     */
    private String processExpression(String expression) {
        //trim and convert to lower case
        String proccessExpression = expression.replace(" ","").toLowerCase();
        //check expression is not null
        if (proccessExpression.equals("")) {
            throw new IllegalArgumentException("Wrong input string.String is empty.");
        }
        //check only permitted characters are input
        Pattern my_pattern = Pattern.compile("[^a-z0-9-+]");
        Matcher my_match = my_pattern.matcher(proccessExpression);
        boolean check = my_match.find();
        if (check) {
            throw new IllegalArgumentException("Wrong input string.Only digits, letters, + and - signs are permitted");
        }
        return proccessExpression;
    }
    /**
     * Public method to return the calculated distance in milimeters.
     *
     * @return String result + mm
     */
    public String getDistance() {
        List<String> signs = getSigns(this.expression);
        List<Integer> distances = getDistancesConverted(this.expression);
        return convertIntegerToUnit(calculateDistances(signs, distances));
    }
    /**
     * Method to create the list of signs
     *
     * @param expression- from where sigs are extracted
     * @return List<String> with signs
     */
    private List<String> getSigns(String expression) {
        String[] operators = expression.split("\\w+");
        List<String> signs = new ArrayList<String>();
        for (int i=1; i<expression.length(); ++i) {
                if (expression.charAt(i) == '+' || expression.charAt(i) == '-') {
                    signs.add(expression.substring(i,i+1));
                }
        }
        return signs;
    }
    /**
     * Method to create the list of distances.
     * Takes  a String expression and splits by non-characters(\\W) into String[] operands.
     * operands[] are converted and added to ArrayList<Integer>
     *
     * @param expression- from where distances are extracted and converted
     * @return List<Integer> with distances converted to mm
     */
    private List<Integer> getDistancesConverted(String expression) {
        String[] operands = expression.split("\\W");
        List<Integer> distances = new ArrayList<Integer>();
        for (int i=0; i<operands.length; ++i) {
            distances.add(convertStringToMM(operands[i]));
        }
        return distances;
    }
    /**
     * Method to convert the String type distances in Integer type milimeters distances.
     *
     * @param operand- from where distances are extracted and converted
     * @return List<Integer> with distances converted to mm
     */
    private Integer convertStringToMM(String operand) {
        if (operand.replaceAll("\\d","").equals("m")){
            return Integer.parseInt(operand.replace("m",""))*1000;
        }
        else if (operand.replaceAll("\\d","").equals("dm")) {
            return Integer.parseInt(operand.replace("dm",""))*100;
        }
        else if (operand.replaceAll("\\d","").equals("cm")) {
            return Integer.parseInt(operand.replace("cm",""))*10;
        }
        else if (operand.replaceAll("\\d","").equals("mm")) {
            return Integer.parseInt(operand.replace("mm",""))*1;
        }
        //if we have no units assume its milimeters
        else if (operand.replaceAll("\\d","").equals("")) {
            return Integer.parseInt(operand)*1;
        }
        else throw new IllegalArgumentException("Wrong input string.Unit not found. Only m dm cm mm are permitted");

    }
    /**
     * Method to calculate the total distance from a List<String> with signs and
     * a List<Integer> with distances(as operands) in the order they were inserted.
     *
     * @param signs -  List<String> with signs
     * @param distances - List<Integer> with distances(as operands)
     * @return total distance
     */
    private Integer calculateDistances(List<String> signs, List<Integer> distances) {
        Integer distanceTotal = distances.get(0);
        for (int i=1; i<distances.size(); ++i) {
            if (signs.get(i-1).equals("+")) {
                distanceTotal = distanceTotal + distances.get(i);
            }
            if (signs.get(i-1).equals("-")) {
                distanceTotal = distanceTotal - distances.get(i);
            }
        }
        return distanceTotal;
    }
    /**
     * Method to convert Integer type distance into String with "mm" unit
     *
     * @param calculatedDistance -  input distance to be converted
     * @return String result in milimeters
     */
    private String convertIntegerToUnit(Integer calculatedDistance) {
        return String.valueOf(calculatedDistance)+" mm";
    }

}
