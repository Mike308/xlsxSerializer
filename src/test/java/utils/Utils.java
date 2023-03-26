package utils;

import java.time.LocalDate;

public class Utils {
    public static int range(int min, int max) {
        return max - min + 1;
    }

    public static String drawFirstName() {
        String[] fistNames = {"Oliver","Jake","Noah","James","Jack","Connor","Liam","John","Harry","Callum","Mason","Robert","Jacob","Jacob","Jacob","Michael","Charlie","Kyle","William","William","Thomas","Joe","Ethan","David","George","Reece","Michael","Richard","Oscar","Rhys","Alexander","Joseph","James","Charlie","James","Charles","William","Damian","Daniel","Thomas"};
        int min = 0;
        int max = fistNames.length - 1;
        int index = (int)(Math.random() * range(0, max) + min);
        return fistNames[index];
    }

    public static String drawSystem() {
        String[] systems = {"Ubuntu", "Debian", "Windows", "FEDORA", "MACOS"};
        int min = 0;
        int max = systems.length - 1;
        int index = (int)(Math.random() * range(0, max) + min);
        return systems[index];
    }

    public static String drawLastName() {
        String[] lastNames = {"Smith","Murphy","Smith","Jones","O'Kelly","Johnson","Williams","O'Sullivan","Williams","Brown","Walsh","Brown","Taylor","Smith","Jones","Davies","O'Brien","Miller","Wilson","Byrne","Davis","Evans","O'Ryan","Garcia","Thomas","O'Connor","Rodriguez","Roberts","O'Neill","Wilson"};
        int min = 0;
        int max = lastNames.length - 1;
        int index = (int)(Math.random() * range(min, max) + min);
        return lastNames[index];
    }

    public static String drawStreet() {
        String[] streets = {"Rodeo Drive", "Hollywood Boulevard", "Sunset Boulevard", "Mulholland Drive", "Melrose Avenue"};
        int min = 0;
        int max = streets.length - 1;
        int index = (int)(Math.random() * range(min, max) + min);
        return streets[index];
    }

//    public static Date drawBirthDate() throws ParseException {
//        int randomYear = (int)(Math.random() * range(1983, 1992) + 1983);
//        int randomMonth = (int)(Math.random() * range(1, 12) + 1);
//        int randomDay = (int)(Math.random() * range(1, 20) + 1);
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
//        String day = randomDay < 10 ? "0".concat(Integer.toString(randomDay)) : Integer.toString(randomDay);
//        String month = randomMonth < 10 ? "0".concat(Integer.toString(randomMonth)) : Integer.toString(randomMonth);
//        String year = Integer.toString(randomYear);
//        String date = day + "." + month + "." + year;
//        return dateFormat.parse(date);
//    }

    public static LocalDate drawBirthDate() {
        int randomYear = (int) (Math.random() * range(1983, 1992) + 1983);
        int randomMonth = (int) (Math.random() * range(1, 12) + 1);
        int randomDay = (int) (Math.random() * range(1, 20) + 1);
        return LocalDate.of(randomYear, randomMonth, randomDay);
    }
}
