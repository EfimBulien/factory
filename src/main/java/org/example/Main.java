package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static String[][] products = {
            {"Продукт 1", "100", "50"},
            {"Продукт 2", "150", "70"},
            {"Продукт 3", "200", "100"},
            {"Продукт 4", "180", "90"},
            {"Продукт 5", "210", "120"}
    };

    private static final Map<String, String> employees = new HashMap<>(); // Штат сотрудников

    public static void initializeEmployees() {
        employees.put("Иванов", "Продукт 1");
        employees.put("Петров", "Продукт 2");
        employees.put("Сидоров", "Продукт 3");
    }

    public static void main(String[] args) {
        initializeEmployees(); // Создание стандартных сотрудников с привязанными товарами
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\nВыберите действие:");
            System.out.println("1. Найти общий вес продукции на заводе");
            System.out.println("2. Найти среднюю цену за продукт");
            System.out.println("3. Вывести имя оптимального продукта (лучшее соотношение цена и вес)");
            System.out.println("4. Записать отчетность по продукции в файл");
            System.out.println("5. Нанять нового работника");
            System.out.println("6. Уволить работника");
            System.out.println("7. Показать список всех работников с привязанными продуктами к ним");
            System.out.println("8. Изменить информацию о сотруднике");
            System.out.println("9. Создать продукт");
            System.out.println("10. Показать список всех продуктов");
            System.out.println("11. Обновить информацию о продукте");
            System.out.println("12. Удалить продукт");
            System.out.println("0. Выйти из программы");

            int choice = scanner.nextInt();
            scanner.nextLine(); // очистка буфера после nextInt()

            switch (choice) {
                case 1:
                    System.out.println("Общий вес продукции на заводе: " + calculateTotalWeight());
                    break;
                case 2:
                    System.out.println("Средняя цена за продукт: " + calculateAveragePrice());
                    break;
                case 3:
                    System.out.println("Оптимальный продукт: " + findOptimalProduct());
                    break;
                case 4:
                    writeReportToFile("report.txt");
                    break;
                case 5:
                    hireEmployee(scanner);
                    break;
                case 6:
                    fireEmployee(scanner);
                    break;
                case 7:
                    showEmployeeList();
                    break;
                case 8:
                    updateEmployeeInfo(scanner);
                    break;
                case 9:
                    createProduct(scanner);
                    break;
                case 10:
                    showProductList();
                    break;
                case 11:
                    updateProductInfo(scanner);
                    break;
                case 12:
                    deleteProduct(scanner);
                    break;
                case 0:
                    System.out.println("Программа завершена.");
                    running = false;
                    break;
                default:
                    System.out.println("Неверный выбор. Попробуйте еще раз.");
            }
        }
        scanner.close();
    }

    public static int calculateTotalWeight() {
        int totalWeight = 0;
        for (String[] product : products) {
            totalWeight += Integer.parseInt(product[1]);
        }
        return totalWeight;
    }

    public static double calculateAveragePrice() {
        double totalSum = 0;
        for (String[] product : products) {
            totalSum += Double.parseDouble(product[2]);
        }
        return totalSum / products.length;
    }

    public static String findOptimalProduct() {
        double minPricePerWeight = Double.MAX_VALUE;
        String optimalProductName = "";
        for (String[] product : products) {
            double pricePerWeight = Double.parseDouble(product[2]) / Integer.parseInt(product[1]);
            if (pricePerWeight < minPricePerWeight) {
                minPricePerWeight = pricePerWeight;
                optimalProductName = product[0];
            }
        }
        return optimalProductName;
    }

    public static void writeReportToFile(String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write("Отчетность по продукции на заводе:\n");
            writer.write("-------------------------------\n");
            writer.write("Средняя цена за продукт: " + calculateAveragePrice() + "\n");
            writer.write("Общий вес продукции на заводе: " + calculateTotalWeight() + "\n");
            writer.write("Оптимальный продукт: " + findOptimalProduct() + "\n");
            writer.write("-------------------------------\n");

            writer.write("Сотрудники и их ответственность за продукты:\n");
            for (Map.Entry<String, String> entry : employees.entrySet()) {
                writer.write(entry.getKey() + " - " + entry.getValue() + "\n");
            }
            writer.write("-------------------------------\n");
            System.out.println("Отчет успешно записан в файл " + fileName);
        } catch (IOException e) {
            System.out.println("Ошибка при записи отчета в файл.");
            e.printStackTrace();
        }
    }

    public static void hireEmployee(Scanner scanner) {
        System.out.println("Введите имя нового работника:");
        String name = scanner.nextLine();
        System.out.println("Введите продукт, за который будет ответственен новый работник:");
        String product = scanner.nextLine();
        employees.put(name, product);
        System.out.println("Новый работник " + name + " нанят и ответственен за продукт " + product);
    }

    public static void fireEmployee(Scanner scanner) {
        System.out.println("Введите имя работника, которого нужно уволить:");
        String name = scanner.nextLine();
        if (employees.containsKey(name)) {
            employees.remove(name);
            System.out.println("Работник " + name + " уволен.");
        } else {
            System.out.println("Работник с таким именем не найден.");
        }
    }

    public static void showEmployeeList() {
        System.out.println("Список всех работников и их привязанных продуктов:");
        for (Map.Entry<String, String> entry : employees.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    public static void updateEmployeeInfo(Scanner scanner) {
        System.out.println("Введите имя сотрудника, информацию о котором хотите изменить:");
        String name = scanner.nextLine();
        if (employees.containsKey(name)) {
            System.out.println("Введите новый продукт, за который будет ответственен сотрудник:");
            String product = scanner.nextLine();
            employees.put(name, product);
            System.out.println("Информация о сотруднике " + name + " успешно обновлена.");
        } else {
            System.out.println("Сотрудник с таким именем не найден.");
        }
    }

    public static void createProduct(Scanner scanner) {
        System.out.println("Введите название нового продукта:");
        String name = scanner.nextLine();
        System.out.println("Введите вес нового продукта:");
        String weight = scanner.nextLine();
        System.out.println("Введите цену нового продукта:");
        String price = scanner.nextLine();

        String[][] newProducts = new String[products.length + 1][3];
        System.arraycopy(products, 0, newProducts, 0, products.length);
        newProducts[products.length] = new String[]{name, weight, price};
        products = newProducts;
        System.out.println("Новый продукт успешно создан.");
    }

    public static void showProductList() {
        System.out.println("Список всех продуктов:");
        for (String[] product : products) {
            System.out.println("Название: " + product[0] + ", Вес: " + product[1] + ", Цена: " + product[2]);
        }
    }

    public static void updateProductInfo(Scanner scanner) {
        System.out.println("Введите название продукта, информацию о котором хотите изменить:");
        String name = scanner.nextLine();
        boolean productFound = false;
        for (String[] product : products) {
            if (product[0].equals(name)) {
                System.out.println("Введите новое название для продукта:");
                String newName = scanner.nextLine();
                System.out.println("Введите новый вес для продукта:");
                String newWeight = scanner.nextLine();
                System.out.println("Введите новую цену для продукта:");
                String newPrice = scanner.nextLine();
                product[0] = newName;
                product[1] = newWeight;
                product[2] = newPrice;
                productFound = true;
                System.out.println("Информация о продукте успешно обновлена.");
                break;
            }
        }
        if (!productFound) {
            System.out.println("Продукт с таким названием не найден.");
        }
    }

    public static void deleteProduct(Scanner scanner) {
        System.out.println("Введите название продукта, который хотите удалить:");
        String name = scanner.nextLine();
        boolean productFound = false;
        String[][] newProducts = new String[products.length - 1][3];
        int index = 0;
        for (String[] product : products) {
            if (!product[0].equals(name)) {
                newProducts[index++] = product;
            } else {
                productFound = true;
            }
        }
        if (productFound) {
            products = newProducts;
            System.out.println("Продукт успешно удален.");
        } else {
            System.out.println("Продукт с таким названием не найден.");
        }
    }
}
