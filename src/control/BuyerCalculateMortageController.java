package control;

import java.util.List;

import entity.BuyerPropertyListing;
import entity.CalculateMortage;

public class BuyerCalculateMortageController {
private CalculateMortage calculateMortage;
	
	public BuyerCalculateMortageController(CalculateMortage calculateMortage) {
		this.calculateMortage = calculateMortage;
	}
	
	public double calculateMonthlyPayment(double loanAmount, double interestRate, int loanTerm){
        return calculateMortage.calculateMonthlyPayment(loanAmount, interestRate, loanTerm);
    }
}
