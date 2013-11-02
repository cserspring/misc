/*
 * @file AnalyzePageResult.java
 * @author Shuai Wang (shuai@cmu.edu)
 * @date 10/01/2013
 * */
package edu.cmu.wang;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.util.LinkedList;

/* Use class Item to represent the information of the product */
class Item {
	private String title;
	private String price;
	private String shippingPrice;
	private String vendor;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getShippingPrice() {
		return shippingPrice;
	}

	public void setShippingPrice(String shippingPrice) {
		this.shippingPrice = shippingPrice;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public String toString() {
		return title + "\n" + price + "\n" + shippingPrice + "\n" + vendor
				+ "\n";
	}
}

public class AnalyzePageResult {
	/* Get the information of every item in a page */
	private List<Item> wgetItemContent(String url) {
		String text;
		List<Item> items = new LinkedList<Item>();
		Item item = new Item();
		try {
			URL gotoUrl = new URL(url);
			InputStreamReader isr = new InputStreamReader(gotoUrl.openStream());
			BufferedReader in = new BufferedReader(isr);

			while ((text = in.readLine()) != null) {
				if (text.contains("There were no matches for your")) {
					break;
				} else if (text
						.contains("input type=\"hidden\" name=\"itemPrice\"")) {
					String arr[] = text.split("\"");
					item.setPrice(arr[5]);
					items.add(item);
					item = new Item();
				} else if (text.contains("class=\"newMerchantName\"")) {
					item.setVendor(text.split("newMerchantName")[1].split(">")[1]
							.split("<")[0]);
				} else if (text.contains("quickLookGridItemFullName hide")) {
					item.setTitle(text.split(">")[1].split("<")[0]);

				} else if (text.contains("div class=\"taxShippingArea\"")) {
					String shippingArea = text.trim();
					while ((text = in.readLine()) != null) {
						shippingArea += text.trim();
						if (text.equals("</div>"))
							break;
					}
					String arr[] = shippingArea.split("</span>")[0].split(">");
					String info = arr[arr.length - 1].trim();
					if (info.contains(";")) {
						item.setShippingPrice("+" + info.split(";")[1]);
					} else {
						item.setShippingPrice(info);
					}
				}
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return items;
	}

	/* Get the url of every page */
	private String getURL(String keywords, int page) {
		String keyword[] = keywords.split(" ");
		String str1 = keyword[0];
		for (int i = 1; i < keyword.length; ++i) {
			str1 += "-" + keyword[i];
		}
		String str2 = str1.replace('-', '+');
		// Example:
		// http://www.shopping.com/computer-milk-cup-c/products~PG-5?KW=computer+milk+cup+c
		String url = "http://www.shopping.com/" + str1 + "/products~PG-" + page
				+ "?KW=" + str2;
		return url;
	}

	/* Get the number of products in a single page */
	private int getItemsCountInSinglePage(String url) {
		String text;
		int count = 0;
		try {
			URL gotoUrl = new URL(url);
			InputStreamReader isr = new InputStreamReader(gotoUrl.openStream());
			BufferedReader in = new BufferedReader(isr);

			while ((text = in.readLine()) != null) {
				if (text.contains("There were no matches for your")) {
					in.close();
					return 0;
				} else if (text.contains("quickLookItem-")) {
					++count;
				}
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return count;
	}

	/* Get the whole number of products in a search */
	public int getResultsCount(String keywords) {
		int count = 0;
		int page = 1;
		int pageCount;
		String url;

		while (true) {
			url = getURL(keywords, page);
			pageCount = getItemsCountInSinglePage(url);
			if (pageCount == 0) {
				break;
			} else {
				count += pageCount;
				++page;
			}
		}
		return count;
	}

	/* Print out the information of products */
	public void printPageResults(String keywords, int page) {
		String url = getURL(keywords, page);
		List<Item> items = wgetItemContent(url);
		if (items.size() == 0) {
			System.out.println("No results...");
		} else {
			for (int i = 0; i < items.size(); ++i) {
				System.out.println(items.get(i));
			}
		}
	}

	public static void main(String[] args) {
		final String usage = "Usage: java -jar Assignment.jar [keywords] [page]\n"
				+ "  Query 1: (requires a single argument)\n"
				+ "  java -jar Assignment.jar <keyword> (e.g. java -jar Assignment.jar \"baby strollers\")\n"
				+ "  Query 2: (requires two arguments)\n"
				+ "  java -jar Assignment.jar <keyword> <page number> (e.g. java -jar Assignment.jar \"baby strollers\" 2)\n";
		AnalyzePageResult analyze = new AnalyzePageResult();
		if (args.length == 1) {
			System.out.println("Number of results: "
					+ analyze.getResultsCount(args[0]));
		} else if (args.length == 2) {
			analyze.printPageResults(args[0], Integer.parseInt(args[1]));
		} else {
			System.out.println(usage);
		}
	}

}
