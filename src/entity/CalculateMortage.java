package entity;

public class CalculateMortage {
	double loanAmount, interestRate;
	int loanTerm;
	public CalculateMortage(double loanAmount, double interestRate, int loanTerm) {
		this.loanAmount = loanAmount;
		this.interestRate = interestRate;
		this.loanTerm = loanTerm;
		
		
	}

	public double calculateMonthlyPayment(double loanAmount, double interestRate, int loanTerm) {
        double monthlyInterestRate = interestRate / 100 / 12;
        int numberOfPayments = loanTerm * 12;
        double monthlyPayment = loanAmount * monthlyInterestRate /
                (1 - Math.pow(1 + monthlyInterestRate, -numberOfPayments));
        return monthlyPayment;
    }

}
