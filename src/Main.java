import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String[] fullAnswer;

        clearConsole();
        while (true) {
            try {
                fullAnswer = inputFullData();
                parseAnswer(fullAnswer);
                clearConsole();
                showСorrectData(fullAnswer);
                saveData(fullAnswer);
                return;
            } catch (IllegalArgumentException e) {
                clearConsole();
                System.out.println(e.getMessage());
            } catch (IOException e) {
                clearConsole();
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }
    }

    public static void saveData(String[] data) throws IOException{
        String filename = data[0] + ".txt";
        String line = String.format("%s %s %s %s %s %s", data[0], data[1], data[2], data[3], data[4], data[5]);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            writer.write(line);
            writer.newLine();
            System.out.println("\nДанные успешно сохранены в файл " + filename);
        } catch (IOException e) {
            throw new IOException("Не удалось сохранить данные в файл!");
        }
    }

    public static String[] inputFullData() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите данные: фамилия, имя, отчество, дата рождения (дд.ММ.гггг), номер телефона, пол (m/f)\n");
        String[] partsOfAnswer = scanner.nextLine().trim().split("[\\s,\\+\\-=*/;]+");
        if (partsOfAnswer.length != 6) {
            throw new IllegalArgumentException("Некорректное количество введенных данных! Ожидалось 6");
        } else {
            return partsOfAnswer;
        }
    }

    public static void parseAnswer(String[] data) throws IllegalArgumentException{
        String surname = data[0];
        String name = data[1];
        String middleName = data[2];
        LocalDate dateOfBirth;
        long phoneNumber;
        char gender;

        try {
            dateOfBirth = parseDate(data[3]);
        } catch (Exception e) {
            throw new IllegalArgumentException("Некорректный формат даты! Ожидалось дд.ММ.ггг");
        }

        try {
            phoneNumber = Long.parseLong(data[4]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Некорректный формат номера телефона! Ожидалось число");
        }

        gender = Character.toLowerCase(data[5].charAt(0));
        if (gender != 'm' && gender != 'f') {
            throw new IllegalArgumentException("Некорректный пол! Ожидалось 'm' или 'f'");
        }


    }

    public static void showСorrectData(String[] data) {
        System.out.println("Получены данные:\n");
        System.out.println("Фамилия: " + data[0]);
        System.out.println("Имя: " + data[1]);
        System.out.println("Отчество: " + data[2]);
        System.out.println("Дата рождения: " + data[3]);
        System.out.println("Номер телефона: " + data[4]);
        System.out.println("Пол: " + (data[5].equalsIgnoreCase("m") ? "Мужской" : "Женский"));
    }

    public static void clearConsole() {
        System.out.println("\033[H\033[2J");
        System.out.flush();
        for (int i = 0; i < 100; i++) {
            System.out.println();
        }
    }

    private static LocalDate parseDate(String dateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        try {
            return LocalDate.parse(dateStr, formatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Некорректный формат даты! Ожидалось дд.ММ.гггг", e);
        }
    }
}