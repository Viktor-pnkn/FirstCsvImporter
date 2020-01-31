package program;

import data.Data;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

public class Program {
    public static void main(String[] args) throws IOException, ParseException {
        long startTime = System.currentTimeMillis();
        Data data = Data.loadData2(new File("test_case.csv"));
        //Data.write2(data);

        long timeSpent = System.currentTimeMillis() - startTime;
        System.out.println(timeSpent);

        /*// A
        Map<String, Set<String>> usersFormsLastHour = Data.usersFormsLastHour(data);
        for (Map.Entry<String, Set<String>> entry : usersFormsLastHour.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
        Data.writeUsersForms(usersFormsLastHour);*/

        /*// B
        Map<String, String> map = Data.currentUsersForms(data);
        for (Map.Entry<String,String> entry : map.entrySet()) {
            if (!entry.getValue().equals("send")) {
                System.out.println(entry.getKey() + " " + entry.getValue());
            }
        }*/

        /*// C
        ArrayList<ArrayList<String>> topForms = Data.getTopFiveForms(data);
        for (int i = 0; i < topForms.size(); i++) {
            System.out.println(topForms.get(i));
        }*/
    }
}
