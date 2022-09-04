class Main {
    public static boolean check_key(String key) {
        String[] parts = key.split("-");
        if (parts.length != 4)
            return false;
        if (!parts[0].equals("DAWG"))
            return false;
        for (int i = 1; i < parts.length; i++)
            if (parts[i].length() != 4)
                return false;
        String number = parts[1] + parts[2] + parts[3];
        for (int i = 1; i < number.length(); i++)
            if (number.charAt(i) == number.charAt(i - 1))
                return false;
        long num;
        try {
            num = Long.parseLong(number);
        } catch (NumberFormatException e) {
            return false;
        }
        if (num % 1337 != 0)
            return false;
        return true;
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Activation key not given");
            return;
        }
        if (check_key(args[0]))
            System.out.println("Good key");
        else
            System.out.println("Bad key");
    }
}
