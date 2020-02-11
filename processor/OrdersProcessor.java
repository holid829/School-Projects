package processor;

import java.io.*;
import java.text.NumberFormat;
import java.util.*;

public class OrdersProcessor {

	//The array within the map stores data to help calculate total values
	protected TreeMap<String, Double[]> data;
	private String output;

	public OrdersProcessor(BufferedReader fIn, String base, int orders, boolean multi) {
		data = new TreeMap<String, Double[]>();
		output = "";
		
			Orders[] threads = new Orders[orders];
			
			//Initializes the treemap with the given data
			try {
				while (fIn.ready()) {
					String current = fIn.readLine();
					int location = current.indexOf(" ");
					data.put(current.substring(0, location),
							new Double[] { Double.parseDouble(current.substring(location + 1)), 0.0 });
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		
		if (multi) {
			for (int i = 0; i < threads.length; i++) {
				threads[i] = new Orders(base + (i + 1) + ".txt");
				output += threads[i].process() + "\n";
				
			}
		}
		
		else {
			for (int i = 0; i < threads.length; i++) {
				threads[i] = new Orders(base + (i + 1) + ".txt");
				output += threads[i].processOneThread() + "\n";
			}
		}
	}

	public String getOutput() {
		
		output += "***** Summary of all orders *****\n";

		double total = 0;
		
		while (!data.isEmpty()) {
			String item = data.firstKey();
			Double[] number = data.remove(item);

			//Adds the total values to the string
			output += "Summary - Item's name: " + item + ", Cost per item: "
					+ NumberFormat.getCurrencyInstance().format(number[0]) + ", Number sold: " + number[1].intValue()
					+ ", Item's Total: " + NumberFormat.getCurrencyInstance().format(number[0] * number[1]) + "\n";

			total += number[0] * number[1];
		}

		output += "Summary Grand Total: " + NumberFormat.getCurrencyInstance().format(total) + "\n";
		
		return output;
	}

	public class Orders implements Runnable {

		private BufferedReader fIn;
		private String output;
		private TreeMap<String, Integer> items;
		private Thread processor;

		public Orders(String fileName) {
			File file = new File(fileName);
			processor = new Thread(this);
			FileReader f;
			try {
				f = new FileReader(file);
				fIn = new BufferedReader(f);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

			items = new TreeMap<String, Integer>();
		}

		//Starts the thread
		public String process() {
			processor.start();
			
			return output;
		}
		
		//Gets the output without any additional threads
		public String processOneThread() {
			getOutput();
			return output;
		}

		public void run() {
			getOutput();
			
			try {
				processor.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	
		}
		
		//Gets the output of the orders
		public void getOutput() {
			String line;

			try {

				line = fIn.readLine();
				int clientID = Integer.parseInt(line.substring(line.indexOf(" ") + 1));
				double total = 0;

				//Loads all of the items in the order into a map
				while (fIn.ready()) {
					line = fIn.readLine();
					line = line.substring(0, line.indexOf(" "));

					if (items.containsKey(line)) {
						items.replace(line, items.get(line) + 1);
					}

					else {
						items.put(line, 1);
					}

				}

				System.out.println("Reading order for client with ID: " + clientID);
				output = "----- Order details for client with Id: " + clientID + " -----\n";

				while (!items.isEmpty()) {
					String item = items.firstKey();
					int number = items.remove(item);
					Double[] cost;

					//Updates the map in the outer class with the new number of items purchased
					synchronized (data) {
						cost = data.get(item);
						cost[1] += number;
						data.put(item, cost);
					}

					total += cost[0] * number;
					output += "Item's name: " + item + ", Cost per item: "
							+ NumberFormat.getCurrencyInstance().format(cost[0]) + ", Quantity: " + number + ", Cost: "
							+ NumberFormat.getCurrencyInstance().format(cost[0] * number) + "\n";
				}

				output += "Order Total: " + NumberFormat.getCurrencyInstance().format(total);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {

	
		Scanner keyboard = new Scanner(System.in);
		boolean multi = false;
		String resultsFile = "";
		String base = "";
		int orders = 0;
		OrdersProcessor process = null;

		System.out.println("Enter item's data file name: ");
		File dataFile = new File(keyboard.nextLine());

		System.out.println("Enter 'y' for multiple threads: ");
		if (keyboard.next().equals("y")) {
			multi = true;
		}

		keyboard.nextLine();

		System.out.println("Enter number of orders to process: ");
		orders = keyboard.nextInt();

		

		keyboard.nextLine();

		System.out.println("Enter order's base filename: ");
		base = keyboard.nextLine();

		System.out.println("Enter result's filename: ");
		resultsFile = keyboard.nextLine();

		long startTime = System.currentTimeMillis();
		
		BufferedReader fIn = null;

		try {
			FileReader f = new FileReader(dataFile);
			fIn = new BufferedReader(f);
			process = new OrdersProcessor(fIn, base, orders, multi);

		} catch (IOException e) {
			e.printStackTrace();
		}

		String output = process.getOutput();

		

		File out = new File(resultsFile);
		try {
			FileWriter outFile = new FileWriter(out);
			BufferedWriter fOut = new BufferedWriter(outFile);
			fOut.append(output);
			fOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		long endTime = System.currentTimeMillis();
		System.out.println("Processing time (msec): " + (endTime - startTime));
		System.out.println("Results can be found in file: " + resultsFile);

	}

}
