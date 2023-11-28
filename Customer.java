import java.util.ArrayList;
import java.util.Scanner;

public class Customer extends User {
    // balance , bought item ,

    // private double balance;
    private ArrayList<Item> boughtItem;
    private Scanner scanner;
    private String selection;
    // private ItemCollections itemColl;
    private Invoice invoice;

    public Customer() {
        boughtItem = new ArrayList<Item>();
        scanner = new Scanner(System.in);
        // itemColl= new ItemCollections();
    }

    public Customer(int uId, String uName, String uEmail, String uPassword, boolean isActive, double balance) {
        super(uId, uName, uEmail, uPassword, isActive, balance);
        // this.balance= balance;
        boughtItem = new ArrayList<Item>();
        scanner = new Scanner(System.in);
        // itemColl= new ItemCollections();
    }

    public void showCustomerBoard() {
        do {
            System.out.println("Customer Interface");
            System.out.println("=================");
            System.out.println("1. Display Orders");
            System.out.println("2. Create Order");
            System.out.println("3. Delete Order");
            System.out.println("4. Display Balance");
            System.out.println("5. Display Invoices");
            System.out.println("6. Rate Seller");
            System.out.println("7. Exit");

            System.out.print("Enter your selection: ");
            selection = scanner.nextLine();
            switch (selection.trim()) {
                case "1":
                    // --------------
                    displayOrders();

                    break;
                case "2":
                    // ------------------
                    System.out.println();
                    buyProducts();
                    break;
                case "3":
                    // ------------------
                    deleteOrder();
                    break;
                case "4":
                    // ------------------
                    displayBalance();
                    break;

                case "5":
                    invoice.generateInvoice();

                    System.out.println("case 5");
                    break;
                case "6":
                    // ------------------
                    rateSeller();
                    break;
                case "7":
                    System.out.println("Exiting Customer Interface...");
                    System.out.println("Exit successful. Good Bye.");
                    return;

                default:
                    System.out.println("Invalid selection. Please try again.");
            }
            System.out.println("press Enter...");
            scanner.nextLine();
        } while (!selection.equals("7"));
    }

    // public double getBalance() {
    // return balance;
    // }

    // public void setBalance(double balance) {
    // this.balance = balance;
    // }

    public void displayOrders() {
        if (!getBoughtItem().isEmpty()) {
            System.out.println("Your Orders:");
            int index = 1;
            for (Item item : getBoughtItem()) {
                System.out.println("#" + index + " Item Name: " + item.getItemName() + " Item price: "
                        + item.getPrice() +
                        " Item quantity: " + item.getQuantity());
                System.out.println("===================================================================");
                index++;

            }
        } else {
            System.out.println("Your Cart is Empty.");
        }
    }

    public void buyProducts() {
        // -- update the balance and item qut and Seller money ++.
        ItemCollections.displayApproveItems();
        System.out.println("choose Item to buy:");
        int s;
        String g = scanner.nextLine().trim();
        // try and catch block to handle the String input .
        try {
            s = Integer.parseInt(g);
        } catch (Exception e) {
            System.out.println("You have to enter an integer number.");
            return;
        }
        System.out.println("Enter the Quantity of your Item:");
        int quantity;
        String q = scanner.nextLine().trim();
        // try and catch block to handle the String input .
        try {
            quantity = Integer.parseInt(q);
        } catch (Exception e) {
            System.out.println("You have to enter an integer number.");
            return;
        }

        if (ItemCollections.getApproveItems().size() >= s && s > 0) {

            System.out.println("your Item is: " + ItemCollections.getApproveItems(s - 1).getItemName());
            if (quantity > 0 && quantity <= ItemCollections.getApproveItems(s - 1).getQuantity()) {
                double cost = quantity * ItemCollections.getApproveItems(s - 1).getPrice();
                if (cost <= this.getuBalance()) {
                    Item requiredItem = new Item();
                    requiredItem.setItemNo(ItemCollections.getApproveItems(s - 1).getItemNo());
                    requiredItem.setItemName(ItemCollections.getApproveItems(s - 1).getItemName());
                    requiredItem.setPrice(ItemCollections.getApproveItems(s - 1).getPrice());
                    requiredItem.setQuantity(quantity);
                    addBoughtItem(requiredItem);
                    setuBalance(getuBalance() - cost);
                    ItemCollections.getApproveItems(s - 1)
                            .setQuantity(ItemCollections.getApproveItems(s - 1).getQuantity() - quantity);
                    System.out.println("The payment is successfully done!");
                    System.out.println("Now your Balance is: " + this.getuBalance());
                } else {
                    System.out.println("Insufficent balance!");
                    displayBalance();
                }
            } else {
                System.out.println("Sorry, it is out of stock!");
            }

        } else {
            System.out.println("this product does not exist");
        }

    }

    public void deleteOrder() {
        // ++ update the balance and item qut and Seller money --.
        displayOrders();
        System.out.println("choose Order to delete:");
        int order;
        String o = scanner.nextLine().trim();
        // try and catch block to handle the String input .
        try {
            order = Integer.parseInt(o);
        } catch (Exception e) {
            System.out.println("You have to enter an integer number.");
            return;
        }
        System.out.println("Enter the Quantity of your Item:");
        int quantity;
        String q = scanner.nextLine().trim();
        // try and catch block to handle the String input .
        try {
            quantity = Integer.parseInt(q);
        } catch (Exception e) {
            System.out.println("You have to enter an integer number.");
            return;
        }

        if (getBoughtItem().size() >= order && order > 0) {

            if (getBoughtItem(order - 1).getQuantity() >= quantity) {

                if (getBoughtItem(order - 1).getQuantity() == quantity) {
                    System.out.println(getBoughtItem(order - 1).getItemName() + " is now removed");
                    // update balance after delete order
                    this.setuBalance(getuBalance() + (quantity * getBoughtItem(order - 1).getPrice()));
                    removeBoughtItem(order - 1);
                } else {
                    getBoughtItem(order - 1).setQuantity(getBoughtItem(order - 1).getQuantity() - quantity);
                    // update balance after delete order
                    this.setuBalance(getuBalance() + (quantity * getBoughtItem(order - 1).getPrice()));
                    
                    
                    System.out.println(getBoughtItem(order - 1).getItemName() + " is update now.");
                }
            } else {
                System.out.println("Wrong Quantity.");
            }
        } else {
            System.out.println("this product does not exist");
        }

    }

    // public void displayProducts() {
    // int index = 1;
    // for (Item item : boughtItem) {
    // if (item.getQuantity() == 0) {
    // System.out.println("#" + index + " " + item.getItemName() + " out of stock");
    // } else {
    // System.out.println("#" + index + " " + item.getItemName());
    // }
    // index++;
    // }
    // }

    public void displayBalance() {
        System.out.println("Now your Balance is: " + this.getuBalance());
        System.out.println("Do you want to add to your Balance (y/n):");
        String answer = scanner.nextLine();
        if (answer.equals("y") || answer.equals("Y")) {
            System.out.println("Enter your update:");
            double balance;
            String b = scanner.nextLine().trim();
            // try and catch block to handle the String input .
            try {
                balance = Double.parseDouble(b);
            } catch (Exception e) {
                System.out.println("You have to enter an integer number.");
                return;
            }
            this.setuBalance(balance + getuBalance());
            System.out.println("Now your Balance is: " + this.getuBalance());
        }

    }

    public void rateSeller(){
        displayOrders();
        System.out.println("choose Item to Rate it's Seller:");
        int order;
        String o = scanner.nextLine().trim();
        // try and catch block to handle the String input .
        try {
            order = Integer.parseInt(o);
        } catch (Exception e) {
            System.out.println("You have to enter an integer number.");
            return;
        }

        System.out.println("Rate your Seller from 1 to 5");
        double rate;
        String r = scanner.nextLine().trim();
        // try and catch block to handle the String input .
        try {
            rate = Double.parseDouble(r);
        } catch (Exception e) {
            System.out.println("You have to enter an integer number.");
            return;
        }

        if (getBoughtItem().size() >= order && order > 0) {

            getBoughtItem(order-1).getSeller().setRate(rate);
            System.out.println("You Rate your Seller "+ getBoughtItem(order-1).getSeller().getuName()+ " : "+ getBoughtItem(order-1).getSeller().getRate());
        } else {
            System.out.println("this product does not exist");
        }
        }

    public void addBoughtItem(Item boughtItem) {
        this.boughtItem.add(boughtItem);
    }

    public ArrayList<Item> getBoughtItem() {
        return boughtItem;
    }

    public void removeBoughtItem(int itemNo) {
        getBoughtItem().remove(getBoughtItem().get(itemNo));
    }

    public Item getBoughtItem(int n) {
        return boughtItem.get(n);
    }

}