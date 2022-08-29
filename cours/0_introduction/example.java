class Example {
	public static void main(String[] args) {
		int amount = Integer.parseInt(args[0]);
		// Check the amount plus 1 because we want to print some stuff after
		if (amount + 1 > 11) {
			System.out.println("You're trying to print too much");
		} else {
			for (int i = 0; i < amount; i++) {
				System.out.println(i);
			}
			System.out.println("And we're done");
		}
	}
}
