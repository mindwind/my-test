package me.mindwind.my.test.misc;

/**
 * @author mindwind
 * @version 1.0, May 20, 2014
 */
public class LoanCalculator {

	public double calculateEarnings(double amount, int month, double yearRate, double monthPay, double earnings) {
		assert amount   > 0;
		assert yearRate > 0;
		assert monthPay > 0;
		
		double monthEarnings = amount * (yearRate / 12);
		earnings += monthEarnings;
		if (month == 0 || amount < monthPay) return earnings;
		amount = amount + monthEarnings - monthPay;
		return calculateEarnings(amount, --month, yearRate, monthPay, earnings);
	}
	
	public double calculateInterest(double amount, double rate) {
		return amount * rate;
	}
	
	public static void main(String[] args) {
		LoanCalculator lc = new LoanCalculator();
		
		// case 1: amount=120000, month=12, yearRate=5%, rate=8.5%
		int    month    = 12;
		double amount   = 120000;
		double yearRate = 0.05;
		double rate     = 0.085;
		double monthPay = amount / month; 
		double interest = lc.calculateInterest(amount, rate);
		double earnings = 0;
		earnings = lc.calculateEarnings(amount - interest, month, yearRate, monthPay, earnings);
		System.out.println("case1: interest=" + interest + ", earnings=" + earnings + ", deviation=" + (interest - earnings));
		
		// case 2: amount=120000, month=36, yearRate=5%, rate=16.5%
		month    = 36;
		amount   = 120000;
		rate     = 0.165;
		monthPay = amount / month; 
		interest = lc.calculateInterest(amount, rate);
		earnings = 0;
		earnings = lc.calculateEarnings(amount - interest, month, yearRate, monthPay, earnings);
		System.out.println("case2: interest=" + interest + ", earnings=" + earnings + ", deviation=" + (interest - earnings));
		
		// case 3: amount=150000, month=36, yearRate=5%, rate=16.5%
		month    = 36;
		amount   = 150000;
		rate     = 0.165;
		monthPay = amount / month; 
		interest = lc.calculateInterest(amount, rate);
		earnings = 0;
		earnings = lc.calculateEarnings(amount - interest, month, yearRate, monthPay, earnings);
		System.out.println("case3: interest=" + interest + ", earnings=" + earnings + ", deviation=" + (interest - earnings));
		
		// case 4: amount=180000, month=36, yearRate=5%, rate=16.5%
		month    = 36;
		amount   = 180000;
		rate     = 0.165;
		monthPay = amount / month; 
		interest = lc.calculateInterest(amount, rate);
		earnings = 0;
		earnings = lc.calculateEarnings(amount - interest, month, yearRate, monthPay, earnings);
		System.out.println("case4: interest=" + interest + ", earnings=" + earnings + ", deviation=" + (interest - earnings));	
	}
	
}
