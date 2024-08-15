package encryptdecrypt;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

public class Main {

    public static void main(String[] args) {
        String result = null;
        String in = null;
        String out = null;
        String mode = "enc";
        String data = "";
        String key = "0";
        String algorithm = "shift";

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-in" -> {
                    in = args[i + 1];

                    try {
                        data = new String(Files.readAllBytes(Paths.get(in)));
                    } catch (IOException e) {
                        System.out.println("ERROR " + e.getMessage());
                    }
                }
                case "-out" -> {
                    out = args[i + 1];
                }
                case "-mode" -> {
                    mode = args[i + 1];
                }
                case "-key" -> {
                    key = args[i + 1];
                }
                case "-data" -> {
                    data = args[i + 1];
                }
                case "-alg" -> {
                    algorithm = args[i + 1];
                }
            }
        }

        switch (mode) {
            case "enc" -> {
                switch (algorithm) {
                    case "shift" -> result = encryptShift(data, key);
                    case "unicode" -> result = encryptUnicode(data, key);
                }
            }

            case "dec" -> {
                switch (algorithm) {
                    case "shift" -> result = decryptShift(data, key);
                    case "unicode" -> result = decryptUnicode(data, key);
                }
            }
        }

        if (Objects.nonNull(out)) {
            File file = new File(out);
            try {
                boolean yes = file.createNewFile();

                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write(result == null ? "" : result);
                fileWriter.close();

            } catch (IOException e) {
                System.out.println("ERROR " + e.getMessage());
            }
        }

        System.out.println(result);
    }

    static String encryptShift(String original, String key) {
        original = original.replaceAll("\"", "");
        StringBuilder stringBuilder = new StringBuilder();
        int num = Integer.parseInt(key);

        for (char c : original.toCharArray()) {

            if (c >= 'a' && c <= 'z') {
                if (c + num > 'z')
                    stringBuilder.append((char) (c + num - 'z' + 'a' - 1));
                else
                    stringBuilder.append((char) (c + num));
            } else if (c >= 'A' && c <= 'Z') {
                if (c + num > 'Z')
                    stringBuilder.append((char) (c + num - 'Z' + 'A' - 1));
                else
                    stringBuilder.append((char) (c + num));
            }
            else {
                stringBuilder.append(c);
            }
        }

        return stringBuilder.toString();
    }

    static String decryptShift(String original, String key) {
        original = original.replaceAll("\"", "");
        StringBuilder stringBuilder = new StringBuilder();
        int num = Integer.parseInt(key);

        for (char c : original.toCharArray()) {

            if (c >= 'a' && c <= 'z') {
                if (c - num < 'a')
                    stringBuilder.append((char) (c - num + 'z' - 'a' + 1));
                else
                    stringBuilder.append((char) (c - num));
            } else if (c >= 'A' && c <= 'Z') {
                if (c - num < 'A')
                    stringBuilder.append((char) (c - num + 'Z' - 'A' + 1));
                else
                    stringBuilder.append((char) (c - num));
            }
            else {
                stringBuilder.append(c);
            }
        }

        return stringBuilder.toString();
    }

    static String encryptUnicode(String original, String key) {
        original = original.replaceAll("\"", "");
        StringBuilder stringBuilder = new StringBuilder();

        for (char c : original.toCharArray()) {
            stringBuilder.append((char) (c + Integer.parseInt(key)));
        }

        return stringBuilder.toString();
    }
    static String decryptUnicode(String original, String key) {
        original = original.replaceAll("\"", "");
        StringBuilder stringBuilder = new StringBuilder();

        for (char c : original.toCharArray()) {
            stringBuilder.append((char) (c - Integer.parseInt(key)));
        }

        return stringBuilder.toString();
    }
}
