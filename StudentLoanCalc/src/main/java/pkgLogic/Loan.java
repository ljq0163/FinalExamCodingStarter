package pkgLogic;

import java.time.LocalDate;
import java.util.ArrayList;

import org.apache.poi.ss.formula.functions.FinanceLib;

public class Loan {
	private double LoanAmount;
	private double LoanBalanceEnd;
	private double InterestRate;
	private int LoanPaymentCnt;
	private boolean bCompoundingOption;
	private LocalDate StartDate;
	private double AdditionalPayment;
	private double Escrow;

	private ArrayList<Payment> loanPayments = new ArrayList<Payment>();

	public Loan(double loanAmount, double interestRate, int loanPaymentCnt, LocalDate startDate,
			double additionalPayment, double escrow) {
		super();
		LoanAmount = loanAmount;
		InterestRate = interestRate;
		LoanPaymentCnt = loanPaymentCnt * 12;
		StartDate = startDate;
		AdditionalPayment = additionalPayment;
		bCompoundingOption = false;
		LoanBalanceEnd = 0;
		this.Escrow = escrow;

		double RemainingBalance = LoanAmount;
		int PaymentCnt = 1;
		int num_iter = 0;
		while (RemainingBalance >= this.GetPMT() && num_iter<=1000) {
			num_iter += 1;
			//System.out.println(RemainingBalance);
			//System.out.println("PMT");
			//System.out.println(this.GetPMT());
			Payment payment = new Payment(RemainingBalance, PaymentCnt++, startDate, this, false);
			RemainingBalance = payment.getEndingBalance();
			startDate = startDate.plusMonths(1);
			loanPayments.add(payment);
		}

		Payment payment = new Payment(RemainingBalance, PaymentCnt++, startDate, this, true);
		loanPayments.add(payment);
	}

	public double GetPMT() {
		double PMT = 0;
		//TODO: Execute PMT function to determine payment with given rate, nbr of payments, PV, FV, compounding)
		double P = this.getLoanAmount();
		double r = this.getInterestRate()/12;
		int n = this.getLoanPaymentCnt();
		PMT = P*r/(1-Math.pow(1+r, -n));
		return PMT;
	}

	public double getTotalPayments() {
		//TODO: Return the total payments for the loan
		double tot = 0;
		for (int i =0; i<this.getLoanPayments().size(); i++) {
			tot += this.getLoanPayments().get(i).getPayment();
		}
		return tot;
	}

	public double getTotalInterest() {
		//TODO: Return the total interest for the loan
		double interest = 0;
		interest = this.getTotalPayments() - this.getLoanAmount();
		return interest;
	}

	public double getLoanAmount() {
		return LoanAmount;
	}

	public void setLoanAmount(double loanAmount) {
		LoanAmount = loanAmount;
	}

	public double getLoanBalanceEnd() {
		return LoanBalanceEnd;
	}

	public void setLoanBalanceEnd(double loanBalanceEnd) {
		LoanBalanceEnd = loanBalanceEnd;
	}

	public double getInterestRate() {
		return InterestRate;
	}

	public void setInterestRate(double interestRate) {
		InterestRate = interestRate;
	}

	public int getLoanPaymentCnt() {
		return LoanPaymentCnt;
	}

	public void setLoanPaymentCnt(int loanPaymentCnt) {
		LoanPaymentCnt = loanPaymentCnt;
	}

	public boolean isbCompoundingOption() {
		return bCompoundingOption;
	}

	public void setbCompoundingOption(boolean bCompoundingOption) {
		this.bCompoundingOption = bCompoundingOption;
	}

	public LocalDate getStartDate() {
		return StartDate;
	}

	public void setStartDate(LocalDate startDate) {
		StartDate = startDate;
	}

	public double getAdditionalPayment() {
		return AdditionalPayment;
	}

	public void setAdditionalPayment(double additionalPayment) {
		AdditionalPayment = additionalPayment;
	}

	public ArrayList<Payment> getLoanPayments() {
		return loanPayments;
	}

	public void setLoanPayments(ArrayList<Payment> loanPayments) {
		this.loanPayments = loanPayments;
	}

	public double getEscrow() {
		return Escrow;
	}

}
