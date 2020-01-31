package data;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Data {
    private ArrayList<Form> form;

    public Data() {
        form = new ArrayList<Form>();
    }

    /**
     * loadData через символьный поток (FileReader + BufferedReader
     */
    public static Data loadData(File file) throws IOException, ParseException {
        BufferedReader bf = new BufferedReader(new FileReader(file));
        Data data = new Data();
        bf.readLine();
        while (bf.ready()) {
            String s = bf.readLine();
            String[] str = s.split(";");
            if (str.length == 12) {
                data.form.add(new Form(str[0], Long.parseLong(str[1]), str[2], str[3], str[4], str[5], str[6], str[7], str[8], new SimpleDateFormat("yyyy-dd-MM-HH").parse(str[11])));
            }
        }
        bf.close();
        return data;
    }

    /**
     * loadData через байтовый поток (FileInputStream + конструктор строки)
     */
    public static Data loadData2(File file) throws IOException, ParseException {
        FileInputStream fis = new FileInputStream(file);
        Data data = new Data();
        byte[] arr = new byte[fis.available()];
        fis.read(arr);
        String s = new String(arr);
        String[] strings = s.split("\n");
        for (int i = 1; i < strings.length; i++) {
            String[] str = strings[i].split(";");
            if (str.length == 12) {
                data.form.add(new Form(str[0], Long.parseLong(str[1]), str[2], str[3], str[4], str[5], str[6], str[7], str[8], new SimpleDateFormat("yyyy-dd-MM-HH").parse(str[11])));
            }
        }
        System.out.println("Parasha");
        return data;
    }

    public static void write(Data data) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter("neo.txt"));
        for (int i = 0; i < data.form.size(); i++) {
            bw.write(data.form.get(i).toString());
        }
        bw.close();
    }

    public static void write2(Data data) throws IOException {
        FileOutputStream fos = new FileOutputStream("out.txt");
        for (int i = 0; i < data.form.size(); i++) {
            fos.write(data.form.get(i).toString().getBytes());
        }
    }

    /**
     * Метод возвращает список пользователей и используемых ими форм за последний час
     */
    public static Map<String, Set<String>> usersFormsLastHour(Data data) {
        Map<String, Set<String>> map = new TreeMap<String, Set<String>>();
        for (int i = 0; i < data.form.size(); i++) {
            if (!map.containsKey(data.form.get(i).getSsoid())) {
                if (!data.form.get(i).getFormid().equals("") && !data.form.get(i).getSsoid().equals("")) {
                    Date date = new Date();
                    if (date.getTime() - data.form.get(i).getDate().getTime() <= 3600000) {
                        Set<String> l = new HashSet<String>();
                        l.add(data.form.get(i).getFormid());
                        map.put(data.form.get(i).getSsoid(), l);
                    }
                }
            } else {
                if (!data.form.get(i).getFormid().equals("") && !data.form.get(i).getSsoid().equals("")) {
                    Date date = new Date();
                    if (date.getTime() - data.form.get(i).getDate().getTime() <= 3600000) {
                        map.get(data.form.get(i).getSsoid()).add(data.form.get(i).getFormid());
                    }
                }
            }
        }
        return map;
    }

    public static void writeUsersForms(Map<String, Set<String>> map) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter("Users-Forms.txt"));
        for (Map.Entry<String, Set<String>> entry : map.entrySet()) {
            bw.write(entry.getKey() + " : " + entry.getValue() + '\n');
        }
        bw.close();
    }

    /**
     * Метод возвращает список пользователей (ключ Map), которые начали активность на форме и не дошли до конца.
     * Например, для услуг grp dszn_* начальное состояние start, конечное состояние send.
     * Также возвращает состояние, на котором пользователь остановился (значение Map)
     */
    public static Map<String,String> currentUsersForms(Data data) {
        Map<String, String> map = new HashMap<String, String>();
        for (int i = 0; i < data.form.size(); i++) {
            if (!map.containsKey(data.form.get(i).getSsoid())) {
                map.put(data.form.get(i).getSsoid(), data.form.get(i).getSubtype());
            } else {
                if (!map.get(data.form.get(i).getSsoid()).equals("send")) {
                    map.put(data.form.get(i).getSsoid(), data.form.get(i).getSubtype());
                }
            }
        }
        return map;
    }

    public static ArrayList<ArrayList<String>> getTopFiveForms(Data data) {
        Map<String, Integer> map = Data.getAllForms(data); // ключ - форма, значение - количество
        Map<Integer, ArrayList<String>> map1 = new TreeMap<Integer, ArrayList<String>>();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (!map1.containsKey(entry.getValue())) {
                ArrayList<String> first = new ArrayList<String>();
                first.add(entry.getKey());
                map1.put(entry.getValue(), first);
            } else {
                map1.get(entry.getValue()).add(entry.getKey());
            }
        }
        ArrayList<ArrayList<String>> topFive = new ArrayList<ArrayList<String>>();
        int i = 0; // счетчик для того, чтобы добавить последние 5 форм из map1
        for (Map.Entry<Integer, ArrayList<String>> entry : map1.entrySet()) {
            if (++i >= map1.size() - 4) {
                topFive.add(entry.getValue());
            }
        }
        return topFive;
    }

    private static Map<String, Integer> getAllForms(Data data) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        for (int i = 0; i < data.form.size(); i++) {
            if (!data.form.get(i).getFormid().equals("")) {
                if (!map.containsKey(data.form.get(i).getFormid())) {
                    map.put(data.form.get(i).getFormid(), 1);
                } else {
                    map.put(data.form.get(i).getFormid(), map.get(data.form.get(i).getFormid()) + 1);
                }
            }
        }
        return map;
    }

    @Override
    public String toString() {
        return "Form{" +
                "form=" + form +
                '}';
    }
}
